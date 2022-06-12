<template>
    <h2>用户管理界面</h2>
    <a-table :dataSource="users" :columns="columns" >
        <template #bodyCell="{ column }">
            <template v-if="column.key === 'action'">
                <span>
                    <a @click="showModal">编辑</a>
                    <a-modal centered v-model:visible="visible" title="编辑用户信息" @ok="handleOk">
                        <a-form
                            :model="formState"
                            v-bind="layout"
                            name="nest-messages"
                            :validate-messages="validateMessages"
                            @finish="onFinish"
                        >
                            <a-form-item :name="['user', 'name']" label="Name" :rules="[{ required: true }]">
                            <a-input v-model:value="formState.user.name" />
                            </a-form-item>
                            <a-form-item :name="['user', 'email']" label="Email" :rules="[{ type: 'email' }]">
                            <a-input v-model:value="formState.user.email" />
                            </a-form-item>
                            <a-form-item :name="['user', 'age']" label="Age" :rules="[{ type: 'number', min: 0, max: 99 }]">
                            <a-input-number v-model:value="formState.user.age" />
                            </a-form-item>
                            <a-form-item :name="['user', 'website']" label="Website">
                            <a-input v-model:value="formState.user.website" />
                            </a-form-item>
                            <a-form-item :name="['user', 'introduction']" label="Introduction">
                            <a-textarea v-model:value="formState.user.introduction" />
                            </a-form-item>
                            <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
                            <a-button type="primary" html-type="submit">Submit</a-button>
                            </a-form-item>
                        </a-form>
                    </a-modal>
                </span>
            </template>
        </template>
    </a-table>
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
    title: '操作',
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
            labelCol: { span: 8 },
            wrapperCol: { span: 16 },
        };

        const formState = reactive({
            user: {
                name: '',
                age: undefined,
                email: '',
                website: '',
                introduction: '',
            },
        });

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