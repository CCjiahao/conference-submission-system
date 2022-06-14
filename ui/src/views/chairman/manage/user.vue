<template>
    <h2>用户管理界面</h2>
    <a-table :dataSource="users" :columns="columns">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'action'">
                <a @click="showModal(); see(record)">编辑</a>
            </template>
        </template>
    </a-table>
    <a-modal centered v-model:visible="visible" title="编辑用户信息" @ok="handleOk">
        <a-form :model="formState" v-bind="layout" name="nest-messages" :validate-messages="validateMessages"
            @finish="onFinish">
            <a-form-item :name="['user', 'username']" label="用户名">
                <a-input disabled="true" v-model:value="formState.user.username" />
            </a-form-item>
            <a-form-item :name="['user', 'name']" label="姓名" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.name" />
            </a-form-item>
            <a-form-item :name="['user', 'email']" label="Email" :rules="[{ type: 'email', required: true }]">
                <a-input v-model:value="formState.user.email" />
            </a-form-item>
            <a-form-item :name="['user', 'school']" label="组织" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.school" />
            </a-form-item>
            <a-form-item :name="['user', 'country']" label="国家/地区" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.country" />
            </a-form-item>
            <a-form-item :name="['user', 'role']" label="角色" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.role" />
            </a-form-item>
            <a-form-item :name="['user', 'expertise']" label="归属组别" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.expertise" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script setup lang="ts">
import { ref, reactive, computed, Ref } from 'vue'
import { UpdateUserApi, GetUsers } from '@/request/api'

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

var users: Ref<any[]> = ref([]);
const getUsers = () => {
    users.value = []
    GetUsers().then((res: any) => {
        if (res.errno === 0) {
            for (var i = 0; i < res.data['users'].length; i++) {
                const user = res.data['users'][i];
                if (user.role != 'chairman') {
                    users.value.push(user);
                }
            }
        }
    })
}
getUsers();

const see = (value: any) => {
    console.log(value);
    formState.user.username = value.username;
    formState.user.name = value.name;
    formState.user.email = value.email;
    formState.user.school = value.school;
    formState.user.country = value.country;
    formState.user.role = value.role;
    formState.user.expertise = value.expertise;
}

const visible = ref<boolean>(false);
const showModal = () => {
    visible.value = true;
};

const handleOk = (e: MouseEvent) => {
    console.log(e);
    onFinish(formState);
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
    UpdateUserApi(values.user.username, values.user.name, values.user.school, values.user.country, values.user.expertise, values.user.email).then((res: any) => {
        console.log(res)
        if (res.errno === 0) {
            getUsers()
        }
    }).catch((err: any) => {
        console.log(err);
    })
};

</script>

<style scoped lang="scss">
</style>