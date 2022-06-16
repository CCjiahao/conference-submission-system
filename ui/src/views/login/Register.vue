<template>
    <div class="main">
            <div class="logo">
                <img src="@/assets/logo.png" />
                <span class="title">ECNU期刊投稿系统</span>
            </div>
            <p style="text-align: center;color:gray">East China Normal University 求实创造，为人师表</p>
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
                    <a-input v-model:value="formState.country" v-show="false" placeholder="国家" />
                    <a-select placeholder="国家" label-in-value
                        style="width: 100%;text-align: left;" :options="countrys" @change="countryChange" />
                </a-form-item>

                <a-form-item name="expertise" :rules="[{ required: true, message: '请输入所属领域!' }]">
                    <a-input v-model:value="formState.expertise" v-show="false" placeholder="所属领域" />
                    <a-select mode="multiple" placeholder="所属领域" label-in-value 
                        style="width: 100%;text-align: left;" :options="expertise_options" @change="expertiseChange" />
                </a-form-item>

                <a-form-item name="password"
                    :rules="[{ required: true, message: '请输入密码!' }, { min: 6, message: '密码最短6位' }]">
                    <a-input-password v-model:value="formState.password" placeholder="密码" />
                </a-form-item>

                <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }, {type: 'email'}]">
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
                        注册
                    </a-button>
                </a-form-item>
            </a-form>
    </div>
</template>
<script setup lang="ts">
import { reactive, computed, Ref, ref } from 'vue';
import { GetVerificationCodeApi, GetExpertisesApi } from '@/request/api'
import { message } from 'ant-design-vue';
import countrys from '@/variable/countrys'
import { RegisterApi } from '@/request/api'
import { useRouter } from 'vue-router';
import { checkEmail } from '@/utils/index'

interface FormState {
    username: string;
    name: string;
    email: string;
    school: string;
    country: string;
    expertise: string;
    password: string;
    code: string;
}
const formState = reactive<FormState>({
    username: '',
    name: '',
    email: '',
    school: '',
    country: '',
    expertise: '',
    password: '',
    code: '',
});

const expertise_options: Ref<{ value: string }[]> = ref([]);
GetExpertisesApi().then((res: any) => {
    if (res.errno === 0) {
        const expertises = res.data['expertises'];
        for (var i = 0; i < expertises.length; i++) {
            expertise_options.value.push({ value: expertises[i]['name'] });
        }
    }
})

const countryChange = (value: any) => {
    formState.country = value['value'];
};

const expertiseChange = (values: any) => {
    var expertises = [];
    for(var i = 0; i < values.length; i++){
        expertises.push(values[i]['value']);
    }
    formState.expertise = expertises.toString()
};

const router = useRouter();
const onFinish = (values: any) => {
    RegisterApi(formState.username, formState.name, formState.school, formState.country, formState.expertise, formState.password, formState.email, formState.code).then((res: any) => {
        if (res.errno === 0) {
            message.info('注册成功，请登录！');
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
    // 这里没有验证有效性，默认有效
    if(!checkEmail(formState.email)) {
        message.error('邮箱格式不正确，请重新输入');
        return;
    }
    GetVerificationCodeApi(formState.email).then((res: any) => {
        if (res.errno === 0) {
            message.info('验证码发送成功，请在十分钟内使用');
        }
    }).catch((err: any) => {
        console.log(err);
    })
};

const disabled = computed(() => {
    return !(formState.username && formState.password);
});

</script>
<style scoped lang='scss'>
.main {
    text-align: center;
	background-color: #fff;
	border-radius: 20px;
	width: 350px;
	height: 600px;
	margin: auto;
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
}


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
        width: 40px;
        height: 40px;
        margin-left: -10px;
    }

    .title {
        height: 50px;
        text-align: center;
        color: #000000;
        box-sizing: border-box;
        font-weight: 350;
        line-height: 50px;
        font-size: 29px;
        vertical-align: middle;
    }
}
</style>
