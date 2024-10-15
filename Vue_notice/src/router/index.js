// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/components/HomeView.vue';
import About from '@/components/AboutView.vue';

Vue.use(Router);

export default new Router({
  mode: 'history', // 'hash' 모드도 가능하지만, history 모드 추천
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/about',
      name: 'About',
      component: About
    }
  ]
});
