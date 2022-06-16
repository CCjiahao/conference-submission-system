<template>
    <h2>审稿大厅</h2>
    <a-table :columns="columns" :data-source="papers">
        <template #headerCell="{ column }">
            <template v-if="column.key === 'id'">
                <span>
                    论文编号
                </span>
            </template>
        </template>

        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'title'">
                <a @click="showDrawer(record.id)">{{ record.title }}</a>
            </template>
            <template v-else-if="column.key === 'action'">
                <span>
                    <!--下载请求-->
                    <a :href="'http://localhost:8081/api/download?uuid=' + record.paper" target="_blank">下载文章</a>
                    <a-divider type="vertical" />
                    <router-link :to="{ path: '/review/edit', query: { id: record.id } }">编辑审阅</router-link>
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

        <a-row>
            <a-col :span="3">
                <a-button type="primary" :href="'http://localhost:8081/api/download?uuid=' + paper.uuid"
                    target="_blank">
                    <template #icon>
                        <DownloadOutlined />
                    </template>
                    下载论文
                </a-button>
            </a-col>

            <a-col :span="3" offset="1">
                <a-button type="primary">
                    <template #icon>
                        <search-outlined />
                    </template>
                    <router-link :to="'/review/edit?id=' + paperid" style="color: #ffffff !important;">审阅</router-link>
                </a-button>
            </a-col>
        </a-row>
    </a-drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { GetPapersByReviewerApi, GetPaperDetailByIdApi } from '@/request/api';

const columns = [
    {
        name: '论文编号',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: '论文标题',
        dataIndex: 'title',
        key: 'title',
    },
    {
        title: '论文分类',
        dataIndex: 'expertise',
        key: 'expertise',
    },
    {
        title: '提交时间',
        dataIndex: 'commitTime',
        key: 'commitTime',
    },
    {
        title: '状态',
        key: 'state',
        dataIndex: 'state',
    },
    {
        title: '操作',
        key: 'action',
    },
];
const papers = ref([]);
const token = localStorage.getItem('token');
if (token) {
    GetPapersByReviewerApi(token).then((res: any) => {
        if (res.errno === 0) {
            papers.value = res.data['papers'];
        }
    })
}

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