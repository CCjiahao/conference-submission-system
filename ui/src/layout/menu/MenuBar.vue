<template>
    <div>
        <menu-logo></menu-logo>
        <a-menu v-model:openKeys="menuState.openKeys" v-model:selectedKeys="menuState.selectedKeys" mode="inline"
            theme="dark" @openChange="onOpenChange">
            <a-sub-menu key="submission" v-if="user.role != 'chairman'">
                <template #icon>
                    <form-outlined />
                </template>
                <template #title>投稿</template>
                <a-menu-item key="my_submission">
                    <router-link to="/submission/my">我的投稿</router-link>
                </a-menu-item>
                <a-menu-item key="new_submission">
                    <router-link to="/submission/new">新建投稿</router-link>
                </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="review" v-if="user.role != 'researcher'">
                <template #icon>
                    <file-search-outlined />
                </template>
                <template #title>审稿</template>
                <a-menu-item key="review_info">
                    <router-link to="/review/info">审稿人信息</router-link>
                </a-menu-item>
                <a-menu-item key="review_hall">
                    <router-link to="/review/hall">审稿大厅</router-link>
                </a-menu-item>
                <a-menu-item key="review_log">
                    <router-link to="/review/log">审稿记录</router-link>
                </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="chairman" v-if="user.role == 'chairman'">
                <template #icon>
                    <team-outlined />
                </template>
                <template #title>会议主席</template>
                <a-menu-item key="chairman_index">
                    <router-link to="/chairman/index">会议主席首页</router-link>
                </a-menu-item>
                <a-menu-item key="chairman_manage_user">
                    <router-link to="/chairman/manage/user">用户管理界面</router-link>
                </a-menu-item>
                <a-menu-item key="chairman_manage_paper">
                    <router-link to="/chairman/manage/paper">论文管理界面</router-link>
                </a-menu-item>
            </a-sub-menu>
        </a-menu>
    </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue';
import MenuLogo from './MenuLogo.vue';
import { FormOutlined, TeamOutlined, FileSearchOutlined } from '@ant-design/icons-vue';
import { userStore } from '@/store/user';
import { useRoute } from 'vue-router';

// 获取用户信息
const store = userStore();
const user = store.getState

// 获取路由
const router = useRoute()

// 定义路由对应的key
var routeMap: { [key: string]: [string, string]; } = {
    "/submission/my" : ["submission", "my_submission"],
    "/submission/new" : ["submission", "new_submission"],
    "/review/info" : ["review", "review_info"],
    "/review/hall" : ["review", "review_hall"],
    "/review/log" : ["review", "review_log"],
    "/review/detail":["review","review_detail"],
    "/review/edit":["review","review_edit"],
    "/chairman/index" : ["chairman", "chairman_index"],
    "/chairman/manage/user" : ["chairman", "chairman_manage_user"],
    "/chairman/manage/paper" : ["chairman", "chairman_manage_paper"],
};

// 定义menu的状态
const menuState = reactive({
    rootSubmenuKeys: ['submission', 'review', 'chairman'],
    selectedKeys: [routeMap[router.path][1]],
    openKeys: [routeMap[router.path][0]]
});

// 每次只能打开一个菜单
const onOpenChange = (openKeys: string[]) => {
    if(openKeys.length >= 2) {
        menuState.openKeys = [openKeys[openKeys.length - 1]]
    }
};
</script>