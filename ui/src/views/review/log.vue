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
                <!--每篇文章都有对应的detail页，辩论页，编辑审阅页-->
                <router-link :to="{ path: '/review/detail', query: { id: record.id } }">
                    {{ record.title }}
                </router-link>
            </template>
            <template v-else-if="column.key === 'action'">
                <span>
                    <!--下载请求-->
                    <a :href="'http://localhost:8081/api/download?uuid=' + record.paper" target="_blank">下载文章</a>
                    <a-divider type="vertical" />
                    <template v-if="record.state === '待审核'">
                        <router-link :to="{ path: '/review/edit', query: { id: record.id } }">编辑审阅</router-link>
                    </template>
                    <template v-else-if="record.state === '待辩论'">
                        <router-link :to="{ path: '/review/review', query: { id: record.id } }">查看审阅</router-link>
                    </template>
                    <a-divider type="vertical" />
                    <a>查看辩论</a>
                </span>
            </template>
        </template>
    </a-table>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue';
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';
import { GetReviewedPapersByReviewerApi } from '@/request/api';

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

export default defineComponent({
    components: {
        SmileOutlined,
        DownOutlined,
    },
    data() {
        return {
            papers: []
        }
    },
    methods: {
        getPapers() {
            const token = localStorage.getItem('token');
            if (token) {
                GetReviewedPapersByReviewerApi(token).then((res: any) => {
                    if (res.errno === 0) {
                        this.papers = res.data['papers'];
                    }
                })
            }
        }
    },
    mounted() {
        // 调用请求数据的方法
        this.getPapers()
    }
    ,
    setup() {
        return {
            columns,
        };
    },
});
</script>

<style scoped lang="scss">
</style>