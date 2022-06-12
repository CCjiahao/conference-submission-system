<template>
    <div>论文管理界面</div>
    <a-table :dataSource="papers" :columns="columns" >
        <template #bodyCell="{ column }">
            <template v-if="column.key === 'action'">
                <span>
                    <a>查看摘要</a>
                    <a-divider type="vertical" />
                    <a>下载论文</a>
                    <a-divider type="vertical" />
                    <a>查看辩论</a>
                    <a-divider type="vertical" />
                    <a>删除论文</a>
                </span>
            </template>
        </template>
    </a-table>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import { GetPapersApi } from '@/request/api';

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
        dataIndex: 'manInCharge',
        key: 'manInCharge',
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

export default defineComponent({
    data() {
        return {
            papers: []
        }
    },
    methods: {
        getPapers() {
            GetPapersApi().then((res: any) => {
                if (res.errno === 0) {
                    this.papers = res.data['papers'];
                }
            })
        }
    },
    mounted() {
        // 调用请求数据的方法
        this.getPapers()
    },
    setup() {
        return {
            columns,
        };
    },
});

</script>

<style scoped lang="scss">
</style>