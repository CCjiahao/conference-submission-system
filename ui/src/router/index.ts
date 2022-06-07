import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Layout from '@/layout/index.vue'
import { GetUserByTokenApi } from '@/request/api'
import { userStore } from '@/store/user'


const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/login/Login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/login/Register.vue')
    },
    {
        path: '/forget',
        name: 'forget',
        component: () => import('@/views/login/Forget.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const store = userStore();
    const token = localStorage.getItem('token');
    if(token && store.isNull) {
        GetUserByTokenApi(token).then((res: any) => {
            if (res.errno == 0) {
                const user = res.data['user'];
                store.setState(user)
                if(user.role == 'researcher') {
                    router.addRoute({
                        path: '/',
                        name: 'Home',
                        redirect: '/submission/my'
                    })
                }
                else if(user.role == 'reviewer') {
                    router.addRoute({
                        path: '/',
                        name: 'Home',
                        redirect: '/review/info'
                    })
                }
                else {
                    router.addRoute({
                        path: '/',
                        name: 'Home',
                        redirect: '/chairman/index'
                    })
                }
                if(user.role == 'researcher' || user.role == 'reviewer') {
                    router.addRoute(
                        {
                            path: '/submission',
                            name: 'submission',
                            component: Layout,
                            meta: {},
                            children: [
                                {
                                    path: '/submission/my',
                                    name: 'my_submission',
                                    component: () => import('@/views/submission/my.vue'),
                                    meta: {}
                                },
                                {
                                    path: '/submission/new',
                                    name: 'submission/new',
                                    component: () => import('@/views/submission/new.vue'),
                                    meta: {}
                                }
                            ]
                        }
                    )
                }
                if(user.role == 'chairman' || user.role == 'reviewer') {
                    router.addRoute({
                        path: '/review',
                        name: 'review',
                        component: Layout,
                        meta: {},
                        children: [
                            {
                                path: '/review/info',
                                name: 'review_info',
                                component: () => import('@/views/review/info.vue'),
                                meta: {}
                            },
                            {
                                path: '/review/log',
                                name: 'review_log',
                                component: () => import('@/views/review/log.vue'),
                                meta: {}
                            }
                        ]
                    })
                }
                if(user.role == 'chairman') {
                    router.addRoute({
                        path: '/chairman',
                        name: 'chairman',
                        component: Layout,
                        meta: {},
                        children: [
                            {
                                path: '/chairman/index',
                                name: 'chairman_index',
                                component: () => import('@/views/chairman/index.vue'),
                                meta: {}
                            },
                            {
                                path: '/chairman/manage/user',
                                name: 'chairman_manage_user',
                                component: () => import('@/views/chairman/manage/user.vue'),
                                meta: {}
                            },
                            {
                                path: '/chairman/manage/paper',
                                name: 'chairman_manage_paper',
                                component: () => import('@/views/chairman/manage/paper.vue'),
                                meta: {}
                            }
                        ]
                    })
                }
                next(to.fullPath);
            }
            else {
                next('/login')
            }
        })
    }
    else {
        if (to.fullPath != '/login' && to.fullPath != '/register' && to.fullPath != '/forget' && !token) {
            next('/login');
        }
        else{
            next();
        }
    }
})

export default router 