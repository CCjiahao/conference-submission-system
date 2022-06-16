<template>
    <div style="margin-bottom: 20px">
        <b>新建投稿</b>
        <p>请正确填写论文及作者真实信息，并且保证符合学术道德规范。</p>
    </div>
    <div style="padding: 30px 100px;">
        <a-steps :current="current">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        <div class="steps-content">
            <a-form :model="paperState" name="normal_login" class="login-form" @finish="onFinish"
                @finishFailed="onFinishFailed" style="padding: 30px 100px">
                <a-form-item name="title" v-show="steps[current].step == 1"
                    :rules="[{ required: true, message: '请输入论文标题!' }]">
                    <a-input v-model:value="paperState.title" placeholder="论文标题" />
                </a-form-item>

                <a-form-item name="abstract" v-show="steps[current].step == 1"
                    :rules="[{ required: true, message: '请输入论文摘要!' }]">
                    <a-textarea v-model:value="paperState.abstract" placeholder="论文摘要" />
                </a-form-item>

                <a-form-item name="expertise" v-show="steps[current].step == 1"
                    :rules="[{ required: true, message: '请输入论文分类!' }]">
                    <a-input v-model:value="paperState.expertise" v-show="false" placeholder="论文分类" />
                    <a-select placeholder="论文分类" label-in-value style="width: 100%" :options="expertise_options"
                        @change="expertiseChange" />
                </a-form-item>

                <a-form-item v-show="steps[current].step == 1" name="collaborators">
                    <a-input-group compact>
                        <a-input v-model:value="paperState.new_collaborator" style="width: calc(100% - 100px)"
                            placeholder="请输入协作者邮箱" />
                        <a-button type="primary" style="width: 100px;" @click="addCollaborator">添加</a-button>
                    </a-input-group>
                </a-form-item>

                <a-table bordered :data-source="dataSource" :columns="columns"
                    v-show="dataSource.length > 0 && steps[current].step == 1">
                    <template #bodyCell="{ column, record }">
                        <template v-if="column.dataIndex === 'operation'">
                            <a-popconfirm v-if="dataSource.length" title="确定删除?" @confirm="onDelete(record.email)">
                                <a>删除</a>
                            </a-popconfirm>
                        </template>
                    </template>
                </a-table>

                <a-upload-dragger v-show="steps[current].step == 2" v-model:fileList="fileList" accept=".pdf"
                    name="file" action="http://localhost:8081/api/upload" @change="handleChange" :max-count="1"
                    @drop="handleDrop" :before-upload="beforeUpload">
                    <p class="ant-upload-drag-icon">
                        <inbox-outlined></inbox-outlined>
                    </p>
                    <p class="ant-upload-text">点击或拖动文件到此区域来上传文件</p>
                    <p class="ant-upload-hint">只支持上传单个PDF格式文件</p>
                </a-upload-dragger>

                <div v-if="current == 2">
                    <a-result status="success" title="论文提交成功" sub-title="你的论文将会被审核，任何动态我们都将通过邮箱联系你，请静候消息！">
                        <template #extra>
                            <a-button key="console" type="primary"><a href="/submission/my">返回</a></a-button>
                        </template>
                    </a-result>
                </div>

                <div class="steps-action" style="padding: 10px 0px">
                    <a-button v-if="current == 0" html-type="validate" type="primary" @click="next">下一步</a-button>
                    <a-button v-if="current == 1" html-type="submit" type="primary">提交</a-button>
                    <a-button v-if="current == 1" style="margin-left: 8px" @click="prev">上一步</a-button>
                </div>
            </a-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { message, UploadChangeParam, Upload } from 'ant-design-vue';
import { checkEmail } from '@/utils/index'
import { GetUserByEmailApi, SubmitPaperApi, GetExpertisesApi } from '@/request/api';
import type { Ref } from 'vue';
import { steps, columns } from '@/variable/submission_paper'
import { userStore } from '@/store/user';
import type { UploadProps } from 'ant-design-vue';

// 获取用户信息
const store = userStore()
const user = store.getState;

const current = ref<number>(0);
const next = () => {
    if (paperState.title != '' && paperState.abstract != '' && paperState.expertise != '') {
        current.value++;
    }
};
const prev = () => {
    current.value--;
};

interface PaperState {
    title: string;
    abstract: string;
    expertise: string;
    collaborators: string;
    new_collaborator: string;
    paper: string;
}

const paperState = reactive<PaperState>({
    title: '',
    abstract: '',
    expertise: '',
    collaborators: '',
    new_collaborator: '',
    paper: '',
});

const expertise_options: Ref<{ value: string }[]> = ref([]);
GetExpertisesApi().then((res: any) => {
    if (res.errno === 0) {
        const expertises = res.data['expertises'];
        for (var i = 0; i < expertises.length; i++) {
            expertise_options.value.push({ value: expertises[i]['name'] });
        }
    }
})

const expertiseChange = (values: any) => {
    paperState.expertise = values.value
};

interface DataItem {
    email: string;
    name: string;
    school: string;
}

const dataSource: Ref<DataItem[]> = ref([]);

const onDelete = (email: string) => {
    dataSource.value = dataSource.value.filter(item => item.email !== email);
};

const addCollaborator = () => {
    const collaborator = paperState.new_collaborator;
    if (!checkEmail(collaborator)) {
        message.error('邮箱格式不正确');
    }
    else if (user.email == collaborator) {
        message.error('不能添加自己为协作人');
    }
    else {
        for (var i = 0; i < dataSource.value.length; i++) {
            if (dataSource.value[i].email == collaborator) {
                message.error('该邮箱已添加');
                return;
            }
        }
        GetUserByEmailApi(collaborator).then((res: any) => {
            if (res.errno === 0) {
                const user = res.data['user'];
                if(user.role == 'chairman') {
                    message.error("期刊主席不能够作为协作人")
                    return;
                }
                const newData = {
                    email: user.email,
                    name: user.username,
                    school: user.school,
                };
                dataSource.value.push(newData);
                const collaborators = [];
                for (var i = 0; i < dataSource.value.length; i++) {
                    collaborators.push(dataSource.value[i].name)
                }
                paperState.collaborators = collaborators.toString()
                paperState.new_collaborator = '';
            }
        }).catch((err: any) => {
            console.log(err);
        })
    }
}

const fileList = ref([])
const handleChange = (info: UploadChangeParam) => {
    console.log(info)
    const status = info.file.status;
    if (status === 'done') {
        paperState.paper = info.file.response['data']['uuid'];
        message.success(`${info.file.name} file uploaded successfully.`);
    }
};
const handleDrop = (e: DragEvent) => {
    console.log(e);
}
const beforeUpload: UploadProps['beforeUpload'] = file => {
    const isPDF = file.name.endsWith('.pdf');
    if (!isPDF) {
        message.error(`上传的论文必须是PDF格式.`);
    }
    return isPDF || Upload.LIST_IGNORE;
};

const onFinish = (values: any) => {
    if (paperState.paper == '') {
        message.error('请上传论文文件！')
        return;
    }
    const token = localStorage.getItem('token');
    if (token == null) {
        message.error("登陆已过期");
        return;
    }
    SubmitPaperApi(token, paperState.title, paperState.abstract, paperState.expertise, paperState.collaborators, paperState.paper).then((res: any) => {
        if (res.errno === 0) {
            message.info("论文提交成功");
            current.value++;
        }
    }).catch((err: any) => {
        console.log(err);
    })
    console.log('Success:', values);
};

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
};
</script>

<style scoped lang="scss">
</style>