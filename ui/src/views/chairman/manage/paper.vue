<template>
    <div>论文管理界面</div>
    <a-table :dataSource="papers" :columns="columns">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'title'">
                <a @click="showDrawer(record.id)">{{ record.title }}</a>
            </template>
            <template v-if="column.key === 'action'">
                <span>
                    <a type="primary" :href="'http://localhost:8081/api/download?uuid=' + record.paper"
                        target="_blank">下载论文</a>
                    <a-divider type="vertical" />
                    <template v-if="record.state !== '待审核'">
                        <router-link :to="{ path: '/review/review', query: { id: record.id } }">查看审阅</router-link>
                        <a-divider type="vertical" />
                    </template>
                    <a>删除论文</a>
                </span>
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
import { GetPapersWithReviewerApi, GetPapersApi, GetPaperDetailByIdApi } from '@/request/api';

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
        title: '负责人',
        dataIndex: 'reviewer',
        key: 'reviewer',
    },
    {
        title: '状态',
        dataIndex: 'state',
        key: 'state',
    },
    {
        title: 'Action',
        key: 'action',
    },
];

const papers = ref([])
GetPapersWithReviewerApi().then((res: any) => {
    if (res.errno === 0) {
        papers.value = res.data['papers'];
    }
})

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
    console.log(id)
    paperid.value = id;
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