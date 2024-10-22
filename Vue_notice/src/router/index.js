// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
import Notice from '@/components/NoticeNavigationView.vue';
import About from '@/components/AboutView.vue';
import Main from '@/components/MainView.vue'
import Home from '@/components/HomeView.vue'

Vue.use(Router);

export default new Router({
  mode: 'history', 
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/Notice',
      name: 'Notice',
      component: Notice
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
      path: '/home',
      name: 'home',
      component: Home
    }
  ]
});
