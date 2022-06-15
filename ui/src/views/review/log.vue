<template>
    <h2>审稿记录</h2>
    <!-- <a-button type="primary" @click="getPapers()">获取论文</a-button> -->
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
                    <template v-if="record.state === '已辩论'">
                        <a-divider type="vertical" />
                        <router-link :to="{ path: '/review/review', query: { id: record.id } }">查看辩论</router-link>
                    </template>
                    <template v-else-if="record.state === '待辩论'">
                        <a-divider type="vertical" />
                        <router-link :to="{ path: '/review/review', query: { id: record.id } }">查看审阅</router-link>
                    </template>
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
            <a-descriptions-item label="投递流程" > 
                <a-timeline mode="left">
                <template v-if="paper.state==='待审阅'">
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
import { ref } from 'vue';
import { GetReviewedPapersByReviewerApi, GetPaperDetailByIdApi } from '@/request/api';

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
    GetReviewedPapersByReviewerApi(token).then((res: any) => {
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