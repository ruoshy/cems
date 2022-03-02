import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/',
        name: 'index',
        component: () => import('../views/index')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/login')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/register/register')
    },
    {
        path: '/enter',
        name: 'enter',
        component: () => import('../views/enter/enter'),
        children: [
            {
                path: '',
                alias: 'index',
                name: 'enter_index',
                meta: {
                    role: 'exam'
                },
                component: () => import('../views/enter/index/index')
            },
            {
                path: 'signUp',
                name: 'signUp',
                meta: {
                    role: 'exam'
                },
                component: () => import('../views/enter/signUp/signUp')
            },
            {
                path: 'pay',
                name: 'pay',
                meta: {
                    role: 'exam'
                },
                component: () => import('../views/enter/pay/pay')
            }
        ]
    },
    {
        path: '/mp',
        name: 'mp',
        component: () => import('../views/mp/mp'),
        children: [
            {
                path: '',
                alias: 'home',
                name: 'home',
                meta: {
                    role: 'admin'
                },
                component: () => import('../views/mp/home/home')
            },
            {
                path: 'subject',
                name: 'subject',
                meta: {
                    role: 'admin'
                },
                component: () => import('../views/mp/subject/subject')
            },
            {
                path: 'staff',
                name: 'staff',
                meta: {
                    role: 'admin'
                },
                component: () => import('../views/mp/staff/staff')
            },
            {
                path: 'exam',
                name: 'exam',
                meta: {
                    role: 'admin'
                },
                component: () => import('../views/mp/exam/exam')
            },
            {
                path: 'room',
                name: 'room',
                meta: {
                    role: 'admin'
                },
                component: () => import('../views/mp/room/room')
            },
            {
                path: 'examinee',
                name: 'examinee',
                meta: {
                    role: 'staff'
                },
                component: () => import('../views/mp/examinee/examinee')
            },
            {
                path: 'invigilate',
                name: 'invigilate',
                meta: {
                    role: 'staff'
                },
                component: () => import('../views/mp/invigilate/invigilate')
            },
            {
                path: 'abnormity',
                name: 'abnormity',
                meta: {
                    role: 'staff'
                },
                component: () => import('../views/mp/abnormity/abnormity')
            },
        ]
    },
]

export default createRouter({
    history: createWebHistory(),
    routes
})
