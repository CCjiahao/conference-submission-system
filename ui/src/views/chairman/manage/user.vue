<template>
    <h2>用户管理界面</h2>
    <a-table :dataSource="users" :columns="columns" >
        <template #bodyCell="{ column,record }">
            <template v-if="column.key === 'action'" >
                <a @click="showModal();see(record)">编辑</a>
            </template>
        </template>
    </a-table>
    <a-modal centered v-model:visible="visible" title="编辑用户信息" @ok="handleOk">
        <a-form
            :model="formState"
            v-bind="layout"
            name="nest-messages"
            :validate-messages="validateMessages"
            @finish="onFinish"
        >
            <a-form-item :name="['user', 'username']" label="用户名" :rules="[{ required: true }]">
                <a-input disabled="true" v-model:value="formState.user.username" />                            
            </a-form-item>
            <a-form-item :name="['user', 'name']" label="姓名" :rules="[{ required: true }]">
            <a-input v-model:value="formState.user.name" />
            </a-form-item>
            <a-form-item :name="['user', 'email']" label="Email" :rules="[{ type: 'email' }]">
            <a-input v-model:value="formState.user.email" />
            </a-form-item>
            <a-form-item :name="['user', 'school']" label="组织" :rules="[{ required: true }]">
            <a-input-number v-model:value="formState.user.school" />
            </a-form-item>
            <a-form-item :name="['user', 'country']" label="国家/地区">
            <a-input v-model:value="formState.user.country" />
            </a-form-item>
            <a-form-item :name="['user', 'role']" label="角色">
            <a-textarea v-model:value="formState.user.role" />
            </a-form-item>
            <a-form-item :name="['user', 'expertise']" label="归属组别">
            <a-textarea v-model:value="formState.user.expertise" />
            </a-form-item>
            <!-- <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
            <a-button type="primary" html-type="submit">Submit</a-button>
            </a-form-item> -->
        </a-form>
    </a-modal>
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
    title: '国家/地区',
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
    title: '操作',
    dataIndex: 'action',
    key: 'action',
    },
];

const formState = reactive({
    user: {
        username: '',
        name: '',
        email: '',
        school: '',
        country: '',
        role: '',
        expertise: '',
    },
});

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
        },
        see(value: any){
            console.log(value);
            formState.user = value;
        }
    },
    mounted() {
        // 调用请求数据的方法
        this.getUsers()
    },
    setup() {
        const visible = ref<boolean>(false);

        const showModal = () => {
            visible.value = true;
        };

        const handleOk = (e: MouseEvent) => {
            console.log(e);
            visible.value = false;
        };

        const validateMessages = {
            required: '${label} is required!',
            types: {
                email: '${label} is not a valid email!',
                number: '${label} is not a valid number!',
            },
            number: {
                range: '${label} must be between ${min} and ${max}',
            },
        };

        const layout = {
            labelCol: { span: 4 },
            wrapperCol: { span: 16 },
        };

        const onFinish = (values: any) => {
            console.log('Success:', values);
        };

        return {
            columns,
            visible,
            showModal,
            handleOk,
            formState,
            layout,
            validateMessages,
            onFinish,
        };
    },
});

</script>

<style scoped lang="scss">
</style>