import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    },
    {
      path: '/customer',
      name: 'customer',
      component: () => import('../views/CustomerPage.vue')
    },
    {
      path: '/charge',
      name: 'charge',
      component: () => import('../views/ChargePage.vue'),
      props: true
    },
    {
      path: '/history',
      name: 'history',
      component: () => import('../views/HistoryPage.vue'),
      props: true
    }
  ]
})

export default router
