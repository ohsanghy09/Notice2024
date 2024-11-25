<template>
    <v-container>
      <!-- 네비게이션 드로어 -->
      <v-navigation-drawer v-model="drawer" app>
        <v-list>
          <v-list-item @click="navigate('Notice')">
            <v-list-item-title>공지사항</v-list-item-title>
          </v-list-item>
          <v-list-item @click="navigate('Board')">
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
        if(page === "Board"){
            this.$router.push('/Board');
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
      MenuBtn(){
        // 토큰이 있어야만 해당 서비스 사용할 수 있음. (해당 토큰 검증 필요)
        if(localStorage.getItem("token")){

            const token = localStorage.getItem('token');
            console.log(token);

            this.drawer = !this.drawer;
            return;
        }

        // 경고창
        if (confirm("해당 서비스를 이용하기 위해서는 로그인이 필요합니다.\n로그인 화면으로 이동하시겠습니까?")) {
            // 확인(OK) 버튼을 눌렀을 때
            this.$router.push("/Login");
        }
      }
    },
  };
  </script>
  
  <style scoped>
  /* 필요에 따라 스타일 추가 */
  </style>
  