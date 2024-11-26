<template>
    <v-container class="d-flex justify-center align-center" style="min-height: 100vh; background-color: #f4f4f4;">
      <v-card class="pa-8" max-width="500">
        <v-card-title class="text-h5 text-center font-weight-bold">로그아웃</v-card-title>
        <v-card-text>
          <p class="text-center" style="font-size: 16px; color: #1a237e;">
            정말 로그아웃 하시겠습니까? <br />
            로그아웃 후에는 다시 로그인해야 합니다.
          </p>
        </v-card-text>
  
        <!-- 버튼 영역 -->
          <v-btn
            color="error"
            block
            class="mb-4"
            style="font-size: 18px; padding: 12px; max-width: 300px;"
            @click="logout"
          >
            로그아웃
          </v-btn>
          <v-btn
            color="primary"
            block
            style="font-size: 18px; padding: 12px; max-width: 300px;"
            @click="cancel"
          >
            취소
          </v-btn>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    methods: {
      // 로그아웃 메서드
      async logout() {
        if (confirm("로그아웃 하시겠습니까?")) {

            // 로컬스토리지 내역 삭제
            localStorage.clear();
  
            // 로그아웃 성공 메시지
            this.$toast.info("로그아웃이 완료되었습니다.");
  
            // 로그인 화면으로 이동
            this.$router.push("/login");

        }
      },
  
      // 취소 버튼 클릭 시 동작
      cancel() {
        if (confirm("홈 화면으로 이동하시겠습니까?")) {
          this.$router.push("/home");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  p {
    margin: 0;
  }
  </style>
  