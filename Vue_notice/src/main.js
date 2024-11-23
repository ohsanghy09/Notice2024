import Vue from 'vue'
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify';
import axios from 'axios';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';

Vue.config.productionTip = false


let isToastCooldown = false;
Vue.use(Toast, {
  filterBeforeCreate: (toast) => {
    if (isToastCooldown) {
      return false; // 일정 시간 동안 토스트를 생성하지 않음
    }
    isToastCooldown = true;
    setTimeout(() => {
      isToastCooldown = false; // 5초 후 다시 토스트 생성 가능
    }, 1500);
    return toast; // 조건에 따라 토스트를 생성
  },
});

Vue.prototype.$axios = axios.create({
  baseURL: 'http://localhost:8080'
})

new Vue({
  vuetify,
  router,
  render: h => h(App),
}).$mount('#app')
