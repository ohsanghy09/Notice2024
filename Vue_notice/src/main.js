import Vue from 'vue'
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify';
import axios from 'axios';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import Navigation from '@/components/NavigationComponent.vue'

Vue.component('NavigationComponent', Navigation);


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

// 토큰 인증 함수
Vue.prototype.$checkToken = async function(){


  // 토큰 추출
  const token = localStorage.getItem('token');
  console.log(token)

  // 로컬스토리지에 토큰이 존재하지 않을 경우
  if(!token){

    // 기본 메시지
    alert("해당 서비스는 사용자 인증이 필요한 서비스입니다.");
    
    // 강제 URL로 요청을 보낼 경우
    if (this.$route.name !== 'home'){
      alert("로그인 화면으로 이동합니다.");
      this.$router.push("Login");
      return;
    }

    // 홈화면에서 서비스 요청을 보낼 경우
    if(confirm("로그인 화면으로 이동하시겠습니까?")){
      this.$router.push("Login");
      return;
    }

    return;

  }
  
  // 로컬 스토리지에 토큰이 존재할 경우

  // http 통신
  try{

    // token 통신(http)
    const response = await this.$axios.post("/api/security/validate", null, 
      {
        headers : {Authorization: token}
    });

    // 토큰을 보내지 않았을 경우 (거의 필요 없음)
    if (response.status !== 200){
    alert("해당 서비스는 사용자 인증이 필요한 서비스입니다.");
    this.$router.push("Login");
      return;
    }

    // 사용자 토큰이 인증되었을 경우
    return true;

  }

  // http 통신 에러 핸들링
  catch(error){

    // 사용자 토큰 인증이 되지 않았을 경우
    if(error.response.status === 401){
      this.$toast.error(error.response.data);
      localStorage.clear();
      this.$router.push("/Login");
      return false;
    }

    // 서버와 통신이 불안정할 경우
    this.$toast.error("서버와 통신이 불안정합니다. 다시 시도해주세요.");
  }
  


}


Vue.prototype.$axios = axios.create({
  baseURL: 'http://localhost:8080'
})

new Vue({
  vuetify,
  router,
  render: h => h(App),
}).$mount('#app')
