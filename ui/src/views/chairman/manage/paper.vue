<template>
    <div>论文管理界面</div>
    <a-table :dataSource="papers" :columns="columns" />
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import axios from 'axios';

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
            papers:[]
        }
    },
    methods: {
        getPapers(){
          axios({
              url:"http://localhost:8081/api/getPapers",
              method:'GET',
          }).then(res=>{
              console.log(res.data);
              this.papers= res.data;
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