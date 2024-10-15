import Vue from 'vue'
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify';
import axios from 'axios';

Vue.config.productionTip = false

Vue.prototype.$axios = axios.create({
  baseURL: 'http://localhost:8080'
})

new Vue({
  vuetify,
  router,
  render: h => h(App),
}).$mount('#app')
