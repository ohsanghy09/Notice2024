<template>
    <v-container class="d-flex justify-center align-center" style="min-height: 100vh; background-color: #fef3f3;">
      <v-card class="pa-8" max-width="500">
        <v-card-title class="text-h5 text-center font-weight-bold">회원 탈퇴</v-card-title>
        <v-card-text>
          <p class="text-center" style="font-size: 16px; color: #b71c1c;">
            정말 회원 탈퇴를 진행하시겠습니까? <br/>
            탈퇴를 위해 아이디를 입력하세요.
          </p>
          <v-form ref="form" lazy-validation>
            <!-- 아이디 입력 -->
            <v-text-field
              v-model="userId"
              label="아이디"
              outlined
              clearable
              dense
              class="mt-6"
              :rules="[rules.required, rules.minLength]"
              style="font-size: 18px;"
            >
            </v-text-field>
          </v-form>
        </v-card-text>
  
        <!-- 버튼 영역 -->
          <v-btn
            color="error"
            block
            class="mb-4"
            style="font-size: 18px; padding: 12px;"
            @click="RemoveMember"
          >
            회원 탈퇴
          </v-btn>
          <v-btn
            color="primary"
            block
            style="font-size: 18px; padding: 12px;"
            @click="cancel"
          >
            취소
          </v-btn>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {

      // 아이디 입력 변수
      userId : '',

      // template 관련 변수
      rules: {              // 입력 유효성 검사 조건 변수
      required: value => !!value || '필수 입력 사항입니다.',
      minLength: value => !!(value && value.length >= 6) || '최소 6자 이상 입력하세요.',
    },

      // 응답 변수 
      response : null,


      };
    },
    methods: {

      // 회원탈퇴 메서드
      async RemoveMember() {


        if(localStorage.getItem("userId") !== this.userId){
          this.$toast.error("본인 아이디를 입력해주세요.");
          return;
        }


        if(confirm("회원탈퇴 하시겠습니까?")){
        
        // 아이디 입력값 형식이 유효할 경우
        if(this.rules.required(this.userId) === true && this.rules.minLength(this.userId) === true){
          
          // 데이터 변환
          const UserId = {
            userId : this.userId
          }

          // http 통신
          try{
  
            // 회원탈퇴 (http 통신)
            this.response = await this.$axios.post("/api/user/RemoveUser", UserId);

            console.log(this.response)

            // // 통신 후 입력 아이디가 데이터베이스에 존재하지 않을 경우
            if(this.response.status !== 200){
              this.$toast.error("해당 아이디가 존재하지 않습니다.");
              return;
            }

            // 통신 후 입력 아이디가 데이터베이스에 존재할 경우

            // 회원탈퇴 메시지 출력
            this.$toast.info(this.response.data.message);

            // 로컬스토리지 내역 삭제
            localStorage.clear();

            alert("회원탈퇴가 완료되어 로그인 화면으로 이동합니다.");

            this.$router.push("/Login");
            

          }
          // http 통신 에러 핸들링
          catch(error){
            this.$toast.error("서버와 통신이 불안정합니다. 다시 시도해주세요.");
          }
        }
        
        // 아이디 입력형식이 올바르지 않을 경우
        else{
          this.$toast.error("올바른 아이디 형식으로 입력해주세요.");
        }
      }
      },




      // 취소버튼 클릭 메서드
      cancel() {
        if (confirm("홈 화면으로 이동하시겠습니까?")) {
        // 확인(OK) 버튼을 눌렀을 때
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
  