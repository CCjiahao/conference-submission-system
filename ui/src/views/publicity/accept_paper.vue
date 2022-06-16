<template>
    <h2>中选论文公示页面</h2>
    <a-table :dataSource="papers" :columns="columns">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'title'">
                <a @click="showDrawer(record.id)">{{ record.title }}</a>
            </template>
        </template>
    </a-table>
    <a-drawer width="800" placement="right" :closable="false" :visible="visible" @close="onClose">
        <a-descriptions title="论文详情" bordered>
            <a-descriptions-item label="论文标题">{{ paper.title }}</a-descriptions-item>
            <a-descriptions-item label="所属领域">{{ paper.expertise }}</a-descriptions-item>
            <a-descriptions-item label="论文状态">{{ paper.state }}</a-descriptions-item>
            <a-descriptions-item label="论文摘要" :span="3">{{ paper.abstracts }}</a-descriptions-item>
            <a-descriptions-item label="提交时间">{{ paper.commitTime }}</a-descriptions-item>
            <a-descriptions-item label="投递流程" > 
                <a-timeline mode="left">
                <template v-if="paper.state==='待审核'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="blue">审阅</a-timeline-item>
                <a-timeline-item color="grey">辩驳</a-timeline-item>
                <a-timeline-item color="grey">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='待辩论'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="blue">辩驳</a-timeline-item>
                <a-timeline-item color="grey">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已辩论'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="blue">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已确认'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="blue">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已接收'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="green">最终结果:已接收</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已拒绝'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="red">最终结果:已拒绝</a-timeline-item>
                </template>
                </a-timeline>
            </a-descriptions-item>
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
import { ref, Ref } from 'vue'
import { GetPapersWithReviewerApi, GetPapersApi, GetPaperDetailByIdApi, DeletePaperByIdAdminApi, AcceptPaperApi, RejectPaperApi } from '@/request/api';
import { Modal, message } from 'ant-design-vue';
import { createVNode, defineComponent } from 'vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';

const columns = [
    {
        title: '论文编号',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: '论文标题',
        dataIndex: 'title',
        key: 'title',
    },
    {
        title: '投稿时间',
        dataIndex: 'commitTime',
        key: 'commitTime',
    },
    {
        title: '归属类别',
        dataIndex: 'expertise',
        key: 'expertise',
    },
    {
        title: '状态',
        dataIndex: 'state',
        key: 'state',
    }
];

const papers: Ref<any[]> = ref([]);

const getpapers = () => {
    papers.value = []
    GetPapersApi().then((res: any) => {
        if (res.errno === 0) {
            for(var i=0;i<res.data['papers'].length;i++){
                const paper = res.data['papers'][i];
                if(paper.state == "已接收"){
                    papers.value.push(paper)
                }
            }
        }
    })
}
getpapers();

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
        },
        {
            username: '',
            name: '',
            email: '',
            school: '',
            country: '',
        }
    ],
    reviewer: ''
})

const paperid = ref(0);
const visible = ref<boolean>(false);
const showDrawer = (id: number) => {
    paperid.value = id;
    GetPaperDetailByIdApi(id).then((res: any) => {
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