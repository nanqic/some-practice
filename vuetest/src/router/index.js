import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import BookAdd from "@/components/BookAdd";
import BookList from "@/components/BookList";
import BookUpdate from "@/components/BookUpdate";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '图书管理',
    show: true,
    component: Home,
    redirect: "/list",
    children: [
      {
        path: '/list',
        name: '图书列表',
        component: BookList,
        show: true
      },
      {
        path: '/add',
        name: '新增图书',
        component: BookAdd,
        show: true
      },
      {
        path: '/update',
        component: BookUpdate,
        show: false //不遍历
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
