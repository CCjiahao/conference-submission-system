<template>
    <a-row justify="center">
        <a-col :span="5">
            <div class="logo">
                <img src="@/assets/logo.png" />
                <span class="title">ECNU期刊投稿系统</span>
            </div>
            <p style="text-align: center;">East China Normal University 求实创造，为人师表</p>
            <a-form :model="formState" name="normal_login" class="login-form" @finish="onFinish"
                @finishFailed="onFinishFailed">
                <a-form-item name="username" :rules="[{ required: true, message: '请输入用户名!' }]">
                    <a-input v-model:value="formState.username" placeholder="用户名" />
                </a-form-item>

                <a-form-item name="password"
                    :rules="[{ required: true, message: '请输入密码!' }, { min: 6, message: '密码最短6位' }]">
                    <a-input-password v-model:value="formState.password" placeholder="密码" />
                </a-form-item>
                
                <a-form-item name="password2"
                    :rules="[{ required: true, message: '请再次输入密码!' }, { min: 6, message: '密码最短6位' }]">
                    <a-input-password v-model:value="formState.password2" placeholder="重复密码" />
                </a-form-item>

                <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }]">
                    <a-input v-model:value="formState.email" placeholder="邮箱" />
                </a-form-item>

                <a-form-item name="code" :rules="[{ required: true, message: '请输入验证码!' }]">
                    <a-input-group compact>
                        <a-input v-model:value="formState.code" style="width: calc(100% - 100px)" />
                        <a-button type="primary" style="width: 100px;" @click="getCode">获取验证码</a-button>
                    </a-input-group>
                </a-form-item>

                <a-form-item>
                    <a-button :disabled="disabled" type="primary" html-type="submit" style="width: 100%">
                        修改密码
                    </a-button>
                </a-form-item>
            </a-form>
        </a-col>
    </a-row>
</template>
<script setup lang="ts">
import { reactive, computed } from 'vue';
import { GetVerificationCodeByUsernameAndEmailApi, ForgetApi } from '@/request/api'
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';

interface FormState {
    username: string;
    email: string;
    password: string;
    password2: string;
    code: string;
}
const formState = reactive<FormState>({
    username: '',
    email: '',
    password: '',
    password2: '',
    code: '',
});

const expertise_options: { value: string }[] = [];
expertise_options.push({ value: 'CV' })
expertise_options.push({ value: 'NLP' })
expertise_options.push({ value: 'ML' })

const router = useRouter();
const onFinish = (values: any) => {
    ForgetApi(formState.username, formState.password, formState.email, formState.code).then((res: any) => {
        if (res.errno === 0) {
            message.info('修改密码成功，请登录！');
            router.push("/login");
        }
    }).catch((err: any) => {
        console.log(err);
    })
};

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
};

const getCode = () => {
    if(formState.username == '') {
        message.error('请输入用户名');
        return;
    }
    if(formState.email == '') {
        message.error('请输入邮箱');
        return;
    }
    GetVerificationCodeByUsernameAndEmailApi(formState.username, formState.email).then((res: any) => {
        if (res.errno === 0) {
            message.info('验证码发送成功，请在十分钟内使用');
        }
    }).catch((err: any) => {
        console.log(err);
    })
};

const disabled = computed(() => {
    return !(formState.username && formState.password && formState.password2 && (formState.password == formState.password2) && formState.code);
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
