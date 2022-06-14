<template>
    <h2>用户管理界面</h2>
    <a-table :dataSource="users" :columns="columns">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'action'">
                <a @click="showModal(record)">编辑</a>
            </template>
        </template>
    </a-table>
    <a-modal centered v-model:visible="visible" title="编辑用户信息" @ok="handleOk">
        <a-form :model="formState" v-bind="layout" name="nest-messages" :validate-messages="validateMessages">
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
                <a-input v-model:value="formState.user.country" v-show="false" placeholder="国家" />
                <a-select placeholder="国家" label-in-value style="width: 100%" v-model:value="formState.user.country"
                    :options="countrys" @change="countryChange" />
            </a-form-item>
            <a-form-item :name="['user', 'role']" label="角色" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.role" v-show="false" placeholder="角色" />
                <a-select placeholder="角色" label-in-value style="width: 100%" v-model:value="formState.user.role"
                    :options="[{ value: 'researcher' }, { value: 'reviewer' }]" @change="roleChange" />
            </a-form-item>
            <a-form-item :name="['user', 'expertise']" label="归属组别" :rules="[{ required: true }]">
                <a-input v-model:value="formState.user.expertise" v-show="false" placeholder="角色" />
                <a-select mode="multiple" placeholder="所属领域" label-in-value style="width: 100%"
                    :options="expertise_options" v-model:value="expertise_select" @change="expertiseChange" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script setup lang="ts">
import { ref, reactive, Ref } from 'vue'
import { UpdateUserApi, GetUsers } from '@/request/api'
import countrys from '@/variable/countrys'
import { message } from 'ant-design-vue';
import { checkEmail } from '@/utils/index'

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
const expertise_select: Ref<any[]> = ref([])
const getUsers = () => {
    users.value = []
    GetUsers().then((res: any) => {
        if (res.errno === 0) {
            users.value = res.data['users'];
            
        }
    })
}
getUsers();

const visible = ref<boolean>(false);
const showModal = (value: any) => {
    formState.user.username = value.username;
    formState.user.name = value.name;
    formState.user.email = value.email;
    formState.user.school = value.school;
    formState.user.country = value.country;
    formState.user.role = value.role;
    formState.user.expertise = value.expertise;
    const expertises = value.expertise.split(',');
    expertise_select.value = []
    for (var i = 0; i < expertises.length; i++) {
        expertise_select.value.push({ value: expertises[i] })
    }
    visible.value = true;
}
const handleOk = (e: MouseEvent) => {
    if (formState.user.name === '') { message.error('姓名不能为空'); return; }
    if (formState.user.school === '') { message.error('学校不能为空'); return; }
    if (formState.user.country === '') { message.error('国家不能为空'); return; }
    if (formState.user.expertise === '') { message.error('至少有一个擅长领域'); return; }
    if (formState.user.email === '') { message.error('邮件不能为空'); return; }
    if (!checkEmail(formState.user.email)) { message.error('邮件不合法'); return; }
    UpdateUserApi(formState.user.username, formState.user.name, formState.user.school, formState.user.country, formState.user.expertise, formState.user.email, formState.user.role).then((res: any) => {
        console.log(res)
        if (res.errno === 0) {
            getUsers();
        }
    }).catch((err: any) => {
        console.log(err);
    })
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

const countryChange = (value: any) => {
    formState.user.country = value['value'];
};
const roleChange = (value: any) => {
    formState.user.role = value['value'];
};
const expertise_options: { value: string }[] = [];
expertise_options.push({ value: 'CV' })
expertise_options.push({ value: 'NLP' })
expertise_options.push({ value: 'ML' })
const expertiseChange = (values: any) => {
    var expertises = [];
    for (var i = 0; i < values.length; i++) {
        expertises.push(values[i]['value']);
    }
    formState.user.expertise = expertises.toString()
};
</script>

<style scoped lang="scss">
</style>