import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
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
    if (to.fullPath == '/login' || to.fullPath == '/register' || to.fullPath == '/forget') next();
    else if (token) {
        if (store.isNull) {
            GetUserByTokenApi(token).then((res: any) => {
                if (res.errno == 0) {
                    const user = res.data['user'];
                    store.setState(user)
                    if (user.role == 'researcher') {
                        router.addRoute({
                            path: '/',
                            name: 'Home',
                            redirect: '/submission/my'
                        })
                        if(to.fullPath == '/review/info' || to.fullPath == '/chairman/index') {
                            to.fullPath = '/submission/my';
                        }
                    }
                    else if (user.role == 'reviewer') {
                        router.addRoute({
                            path: '/',
                            name: 'Home',
                            redirect: '/review/info'
                        })
                        if(to.fullPath == '/submission/my' || to.fullPath == '/chairman/index') {
                            to.fullPath = '/review/info';
                        }
                    }
                    else {
                        router.addRoute({
                            path: '/',
                            name: 'Home',
                            redirect: '/chairman/index'
                        })
                        if(to.fullPath == '/review/info' || to.fullPath == '/submission/my') {
                            to.fullPath = '/chairman/index';
                        }
                    }
                    if (user.role == 'researcher' || user.role == 'reviewer') {
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
                                        path: '/submission/rebuttal',
                                        name: 'submission/rebuttal',
                                        component: () => import('@/views/review/review.vue'),
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
                    if (user.role == 'chairman' || user.role == 'reviewer') {
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
                                    path: '/review/hall',
                                    name: 'review_hall',
                                    component: () => import('@/views/review/hall.vue'),
                                    meta: {}
                                },
                                {
                                    path: '/review/log',
                                    name: 'review_log',
                                    component: () => import('@/views/review/log.vue'),
                                    meta: {}
                                },
                                {
                                    path: '/review/edit',
                                    name: 'review_edit',
                                    component: () => import('@/views/review/edit.vue'),
                                    meta: {}
                                },
                                {
                                    path: '/review/review',
                                    name: 'review_review',
                                    component: () => import('@/views/review/review.vue'),
                                    meta: {}
                                }
                            ]
                        })
                    }
                    if (user.role == 'chairman') {
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
                                },
                                {
                                    path: '/chairman/manage/expertise',
                                    name: 'chairman_manage_expertise',
                                    component: () => import('@/views/chairman/manage/expertise.vue'),
                                    meta: {}
                                },
                                {
                                    path: '/chairman/review',
                                    name: 'chairman_review',
                                    component: () => import('@/views/review/review.vue'),
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
        } else next();
    }
    else next('/login');
})

export default router 