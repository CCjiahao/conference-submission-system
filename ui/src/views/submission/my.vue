<template>
    <div style="margin-bottom: 20px">
        <b>我的投稿记录</b>
        <p>请及时跟进论文进度，避免错过重要信息。</p>
    </div>
    <div style="padding: 30px 100px;">
        <a-table bordered :data-source="dataSource" :columns="columns" style="width: 100%;">
            <template #bodyCell="{ column, text, record }">
                <template v-if="column.dataIndex === 'title'">
                    <a @click="showDrawer(record.id)">{{ text || ' ' }}</a>
                </template>
                <template v-else-if="column.dataIndex === 'operation'">
                    <a :href="'http://localhost:8081/api/download?uuid=' + record.paper" target="_blank">下载</a>
                    <a-divider v-if="record.state != '待审核'" type="vertical" />
                    <router-link v-if="record.state != '待审核'"
                        :to="{ path: '/submission/rebuttal', query: { id: record.id } }">查看审阅材料</router-link>
                    <a-divider v-if="record.state == '中选'" type="vertical" />
                    <router-link v-if="record.state == '中选'"
                        :to="{ path: '/submission/rebuttal', query: { id: record.id } }">
                        提交最终版论文</router-link>
                    <a-divider v-if="record.state == '待审核' || record.state == '未中选'" type="vertical" />
                    <a-popconfirm v-if="record.state == '待审核' || record.state == '未中选'" title="确认删除?"
                        @confirm="onDelete(record.id)">
                        <a>删除</a>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
    </div>

    <a-drawer width="800" placement="right" :closable="false" :visible="visible" @close="onClose">
        <a-descriptions title="论文详情" bordered>
            <a-descriptions-item label="论文标题">{{ paper.title }}</a-descriptions-item>
            <a-descriptions-item label="所属领域">{{ paper.expertise }}</a-descriptions-item>
            <a-descriptions-item label="论文状态">{{ paper.state }}</a-descriptions-item>
            <a-descriptions-item label="论文摘要" :span="3">{{ paper.abstracts }}</a-descriptions-item>
            <a-descriptions-item label="提交时间">{{ paper.commitTime }}</a-descriptions-item>
        </a-descriptions>
        <a-divider />
        <b>作者详情</b>
        <a-list :data-source="paper.authors">
            <template #renderItem="{ item }">
                <a-descriptions :title="item.username" bordered>
                    <a-descriptions-item label="姓名">{{ item.name }}</a-descriptions-item>
                    <a-descriptions-item label="学校/组织">{{ item.school }}</a-descriptions-item>
                    <a-descriptions-item label="国家">{{ item.country }}</a-descriptions-item>
                    <a-descriptions-item label="邮箱">{{ item.email }}</a-descriptions-item>
                </a-descriptions>
                <a-divider />
            </template>
        </a-list>
        <a-button type="primary" :href="'http://localhost:8081/api/download?uuid=' + paper.uuid" target="_blank">
            <template #icon>
                <DownloadOutlined />
            </template>
            下载论文
        </a-button>

    </a-drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { Ref } from 'vue';
import { userStore } from '@/store/user';
import { GetPapersByAuthorApi, DeletePaperByIdApi, GetPaperDetailByIdApi } from '@/request/api';
import { message } from 'ant-design-vue';

interface PaperItem {
    id: number,
    title: string,
    expertise: string,
    paper: string,
    commitTime: string;
    state: string;
}
const columns = [
    {
        title: '论文编号',
        dataIndex: 'id',
    },
    {
        title: '论文标题',
        dataIndex: 'title',
    },
    {
        title: '论文领域',
        dataIndex: 'expertise',
    },
    {
        title: '投稿时间',
        dataIndex: 'commitTime',
    },
    {
        title: '状态',
        dataIndex: 'state',
    },
    {
        title: '操作',
        dataIndex: 'operation',
    },
];
const dataSource: Ref<PaperItem[]> = ref([]);
const paper = ref({
    uuid: '',
    title: '',
    abstracts: '',
    expertise: '',
    commitTime: '',
    state: '',
    authors: [
        {
            username: '',
            name: '',
            email: '',
            school: '',
            country: '',
        }
    ]
})

// 获取用户信息
const store = userStore();
const user = store.getState

if (user.username != null) {
    GetPapersByAuthorApi(user.username).then((res: any) => {
        if (res.errno === 0) {
            dataSource.value = res.data['papers'];
        }
    })
}

const onDelete = (id: number) => {
    const token = localStorage.getItem('token');
    if (token != null) {
        DeletePaperByIdApi(token, id).then((res: any) => {
            if (res.errno === 0) {
                message.info("论文删除成功！");
                dataSource.value = dataSource.value.filter(item => item.id !== id);
            }
        })
    }
};

const visible = ref<boolean>(false);
const showDrawer = (id: number) => {
    console.log(id)
    GetPaperDetailByIdApi(id).then((res: any) => {
        console.log(res)
        if (res.errno === 0) {
            paper.value = res.data['detail'];
            visible.value = true;
        }
    })
};
const onClose = () => {
    visible.value = false;
};
</script>

<style scoped lang="scss">
</style>