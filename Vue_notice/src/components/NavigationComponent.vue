<template>
    <v-container>
      <!-- 네비게이션 드로어 -->
      <v-navigation-drawer v-model="drawer" app>
        <v-list>
          <v-list-item @click="navigate('Notice')">
            <v-list-item-title>공지사항</v-list-item-title>
          </v-list-item>
          <v-list-item @click="navigate('NoticeBoard')">
            <v-list-item-title>게시판</v-list-item-title>
          </v-list-item>
          <v-list-item @click="navigate('Logout')">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
          <v-list-item @click="navigate('RemoveMember')">
            <v-list-item-title>회원탈퇴</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
  
      <!-- 상단바 -->
      <v-app-bar app>
        <v-app-bar-nav-icon @click="MenuBtn"/>
        <v-toolbar-title><h3>Menu</h3></v-toolbar-title>
      </v-app-bar>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        drawer: false, // Drawer 열림/닫힘 상태
      };
    },
    methods: {
        // 네비게이션 내부 메서드
      navigate(page) {
        
        // 공지사항으로 라우팅
        if(page === "Notice"){
            this.$router.push('/Notice');
        }

        // 게시판으로 라우팅
        if(page === "NoticeBoard"){
            this.$router.push('/NoticeBoard');
        }

        // 로그아웃 창으로 라우팅
        if(page === "Logout"){
            this.$router.push('/Logout');
        }

        // 회원탈퇴 창으로 라우팅
        if(page === "RemoveMember"){
            this.$router.push('/RemoveMember');
        }


        this.drawer = false; // 메뉴 클릭 시 Drawer 닫기
      },

      // 메뉴 버튼 메서드
      async MenuBtn(){
        // 토큰이 있어야만 해당 서비스 사용할 수 있음. (해당 토큰 검증 필요)
        
        // 토큰 검증 함수 실행 
        const check = await this.$checkToken();

        if(check){
          this.drawer = !this.drawer;
          return;
        }
        
      }
    },
  };
  </script>
  
  <style scoped>
  /* 필요에 따라 스타일 추가 */
  </style>
  