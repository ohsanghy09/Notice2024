// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/components/HomeView.vue';
import About from '@/components/AboutView.vue';
import Prac from '@/components/PracticeView.vue'

Vue.use(Router);

export default new Router({
  mode: 'history', 
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
    },
    {
      path: '/practice',
      name: 'practice',
      component: Prac
    }
  ]
});
