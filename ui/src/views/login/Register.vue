<template>
    <a-row justify="center">
        <a-col :span="5">
            <div class="logo">
                <img src="@/assets/logo.png" />
                <span class="title">ECNU会议投稿系统</span>
            </div>
            <p style="text-align: center;">East China Normal University 求实创造，为人师表</p>
            <a-form :model="formState" name="normal_login" class="login-form" @finish="onFinish"
                @finishFailed="onFinishFailed">
                <a-form-item name="username" :rules="[{ required: true, message: '请输入用户名!' }]">
                    <a-input v-model:value="formState.username" placeholder="用户名" />
                </a-form-item>

                <a-form-item name="name" :rules="[{ required: true, message: '请输入真实姓名!' }]">
                    <a-input v-model:value="formState.name" placeholder="真实姓名" />
                </a-form-item>

                <a-form-item name="school" :rules="[{ required: true, message: '请输入学校/组织!' }]">
                    <a-input v-model:value="formState.school" placeholder="学校/组织" />
                </a-form-item>

                <a-form-item name="country" :rules="[{ required: true, message: '请输入国家!' }]">
                    <a-input v-model:value="formState.country" placeholder="国家" />
                </a-form-item>

                <a-form-item name="expertise" :rules="[{ required: true, message: '请输入所属领域!' }]">
                    <a-input v-model:value="formState.expertise" v-show="false" placeholder="所属领域" />
                    <a-select v-model:value="formState.expertise" mode="multiple" placeholder="所属领域" label-in-value
                        style="width: 100%" :options="expertise_options" />
                </a-form-item>

                <a-form-item name="country" :rules="[{ required: true, message: '请输入国家!' }]">
                    <a-input v-model:value="formState.country" placeholder="国家" />
                </a-form-item>

                <a-form-item name="password"
                    :rules="[{ required: true, message: '请输入密码!' }, { min: 6, message: '密码最短6位' }]">
                    <a-input-password v-model:value="formState.password" placeholder="密码" />
                </a-form-item>

                <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }]">
                    <a-input v-model:value="formState.email" placeholder="邮箱" />
                </a-form-item>

                <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }]">
                    <a-input v-model:value="formState.email" placeholder="邮箱" />
                </a-form-item>

                <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }]">
                    <a-input-group compact>
                        <a-input v-model:value="formState.code" style="width: 70%" />
                        <a-button type="primary" style="width: 30%;">Submit</a-button>
                    </a-input-group>
                </a-form-item>

                <a-form-item>
                    <a-button :disabled="disabled" type="primary" html-type="submit">
                        注册
                    </a-button>
                </a-form-item>
            </a-form>
        </a-col>
    </a-row>
</template>
<script setup lang="ts">
import { useRouter } from 'vue-router';
import { reactive, computed } from 'vue';
import { LoginApi } from '@/request/api'
interface FormState {
    username: string;
    name: string;
    email: string;
    school: string;
    country: string;
    expertise: string[];
    password: string;
    code: string;
}
const formState = reactive<FormState>({
    username: '',
    name: '',
    email: '',
    school: '',
    country: '',
    expertise: [],
    password: '',
    code: '',
});
const router = useRouter();
const expertise_options: { value: string }[] = [];
expertise_options.push({ value: 'CV' })
expertise_options.push({ value: 'NLP' })
expertise_options.push({ value: 'ML' })
const onFinish = (values: any) => {
    console.log('Success:', values);
};

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
    return !(formState.username && formState.password);
});
</script>
<style scoped lang='scss'>
.logo {
    height: 70px;
    border: none;
    line-height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-left: 18px;
    color: #ffffff;

    img {
        width: 25px;
        height: 25px;
        margin-left: 7px;
    }

    .title {
        height: 50px;
        text-align: center;
        color: #000000;
        box-sizing: border-box;
        font-weight: 600;
        line-height: 50px;
        font-size: 13px;
        vertical-align: middle;
    }
}
</style>
