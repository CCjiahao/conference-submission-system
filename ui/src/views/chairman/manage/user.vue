<template>
    <div>用户管理界面</div>
    <a-table :dataSource="users" :columns="columns" />
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import axios from 'axios';

const columns = [
    {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    },
    {
    title: '姓名',
    dataIndex: 'name',
    key: 'name',
    },
    {
    title: '电子邮箱',
    dataIndex: 'email',
    key: 'email',
    },
    {
    title: '组织',
    dataIndex: 'school',
    key: 'school',
    },
    {
    title: '国家',
    dataIndex: 'country',
    key: 'country',
    },
    {
    title: '角色',
    dataIndex: 'role',
    key: 'role',
    },
    {
    title: '归属组别',
    dataIndex: 'expertise',
    key: 'expertise',
    },
    {
    title: 'Action',
    key: 'action',
    },
];

export default defineComponent({
    data() {
        return {
            users:[]
        }
    },
    methods: {
        getUsers(){
          axios({
              url:"http://localhost:8081/api/getUsers",
              method:'GET',
          }).then(res=>{
              console.log(res.data);
              this.users= res.data;
          })
      }
    },
    mounted() {
        // 调用请求数据的方法
        this.getUsers()
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