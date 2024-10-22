<template>
  <div class="gallery-home">
    <header class="gallery-header">
      <h1>풍경 갤러리에 오신 것을 환영합니다!!</h1>
    </header>

    <section class="gallery-description">
      <p>풍경사진을 구경하다 가세요!!</p>
    </section>

    <section class="gallery-login">
      <v-btn class="custom-login-btn" @click="handleLogin">들어가기</v-btn>
    </section>
  </div>
</template>

<script>
export default {
  methods: {
    handleLogin() {
      // 로그인 페이지로 리다이렉트
      this.$router.push('/home');
    }
  }
};
</script>

<style scoped>
.gallery-home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 90vh;
  text-align: center;
}

.gallery-header h1 {
  font-size: 48px; /* 폰트 크기 증가 */
  margin-bottom: 30px;
}

.gallery-description p {
  margin-top: 100px;
  font-weight: bold;
  font-size: 35px; /* 폰트 크기 증가 */
  margin-bottom: 60px;
}

.gallery-login {
  margin-top: 40px;
}

.custom-login-btn {
  background-color: #007BFF !important; /* Vuetify 기본 스타일 무시 */
  color: white !important;
  padding: 20px 80px !important; /* 버튼 크기 증가 */
  font-size: 24px !important; /* 버튼 폰트 크기 증가 */
  border-radius: 10px !important; /* 모서리 둥글게 */
  border: none !important;
  cursor: pointer !important;
  width: auto !important;
  height: auto !important;
  min-width: 300px !important; /* 너비 강제 설정 */
}

.custom-login-btn:hover {
  background-color: #0056b3 !important;
}
</style>
