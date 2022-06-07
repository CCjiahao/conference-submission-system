<template>
    <a-dropdown>
        <a class="ant-dropdown-link" @click.prevent style="color: black !important;">
            {{ user.name }}
            <DownOutlined />
        </a>
        <template #overlay>
            <a-menu>
                <a-menu-item key="change_password">
                    <router-link to="/forget">修改密码</router-link>
                </a-menu-item>
                <a-menu-item key="logout" @click="logout">退出登录</a-menu-item>
            </a-menu>
        </template>
    </a-dropdown>
</template>
<script lang="ts" setup>
import { DownOutlined } from '@ant-design/icons-vue';
import { userStore } from '../../store/user';
import { useRouter } from 'vue-router';

// 获取用户信息
const store = userStore()
const user = store.getState
// 获取路由器
const router = useRouter();

// 退出登录
const logout = () => {
    localStorage.removeItem("token");
    router.push("/login");
}
</script>

