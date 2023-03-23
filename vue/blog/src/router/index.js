import {createRouter, createWebHistory} from 'vue-router'
import store from "@/store";



const routers = [
    {
        path:'/',
        name:'index',

        redirect:'/home',
        children:[
            {
                path:"home",
                name:"home",
                component: () => import('../views/home.vue'),
            },
            {
                path:"aboutView",
                name:"aboutView",
                component: () => import('../views/aboutView.vue')
            }
        ]
    },
    {
        path: '/login',
        name:'login',
        //路由守卫
        beforeEnter:(to,from,next)=>{
            if(store.state.userModule.token !== null && store.state.userModule.token !== 'null' || localStorage.getItem("token")){
                next('/admin')
            }else {
                next()
            }
        },
        component: () => import('../views/login')
    },
    {
        path:'/admin',
        name:'admin',
        redirect: '/admin/userController',
        //路由守卫
        beforeEnter:(to,from,next)=>{
            console.log(store.state.userModule.token)
            if(store.state.userModule.token !== null && store.state.userModule.token !== 'null' || localStorage.getItem("token")){
                next()
            }else {
                next('/login')
            }
        },
        children: [
            {
                path: 'userController',
                name: 'userController',
                component: () => import('../views/admin/userController')
            },
            {
                path: 'articleController',
                name: 'articleController',
                component: () => import('../views/admin/articleController')
            },
            {
                path: 'labelController',
                name: 'labelController',
                component: () => import('../views/admin/labelController')
            },
            {
                path: 'sortController',
                name: 'sortController',
                component: () => import('../views/admin/sortController')
            }
        ],
        component: () => import('../views/admin/index')
    }

]


const router = createRouter(
    {
        //路由模式为history 首页为"/"
        history:createWebHistory('/'),
        /*打包注意切换状态*/
        // history:createWebHashHistory('/'),
        //路由对象
        routes:routers,
    }
)

export default router