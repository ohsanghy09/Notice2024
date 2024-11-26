// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
import Notice from '@/components/NoticeNavigationView.vue';
import Main from '@/components/MainView.vue'
import Home from '@/components/HomeView.vue'
import AddMember from '@/components/AddMemberView.vue'
import RemoveMember from '@/components/RemoveMemberView.vue';
import Login from '@/components/LoginView.vue'
import Navigation from '@/components/NavigationComponent.vue'
import Logout from '@/components/LogoutView.vue'


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
      path: '/home',
      name: 'home',
      component: Home
    },
    {
      path: '/AddMember',
      name: 'AddMember',
      component: AddMember
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/RemoveMember',
      name: 'RemoveMember',
      component: RemoveMember
    },
    {
      path: '/Navigation',
      name: 'Navigation',
      component: Navigation
    },
    {
      path: '/Logout',
      name: 'Logout',
      component: Logout
    }
  ]
});
