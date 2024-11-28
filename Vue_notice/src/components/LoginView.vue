<template>
    <v-container class="d-flex justify-center align-center" style="min-height: 100vh; background-color: #f0f4f8;">
      <v-card class="pa-8" max-width="500">
        <v-card-title class="text-h5 text-center font-weight-bold">로그인</v-card-title>
        <v-card-text>
          <v-form ref="form" lazy-validation>
            <!-- 아이디 입력 -->
            <v-text-field
              v-model="userId"
              label="아이디"
              outlined
              clearable
              dense
              @keydown.enter="login"
              class="mt-6"
              :rules="[rules.required, rules.minLength]"
              style="font-size: 18px;"
            ></v-text-field>
  
            <!-- 비밀번호 입력 -->
            <v-text-field
              v-model="password"
              label="비밀번호"
              outlined
              clearable
              dense
              @keydown.enter="login"
              class="mt-6"
              :type="showPassword ? 'text' : 'password'"
              :rules="[rules.required, rules.minLength]"
              style="font-size: 18px;"
            >
              <template #append>
                <v-btn
                  icon
                  @click="showPassword = !showPassword"
                >
                  <v-icon large>{{ showPassword ? 'mdi-eye-off' : 'mdi-eye' }}</v-icon>
                </v-btn>
              </template>
            </v-text-field>
          </v-form>
        </v-card-text>
  
        <!-- 버튼 영역 -->
          <v-btn
            color="primary"
            block
            class="mb-4"
            style="font-size: 18px; padding: 12px;"
            @click="login"
          >
            로그인
          </v-btn>
          <v-btn
            color="success"
            block
            class="mb-4"
            style="font-size: 18px; padding: 12px;"
            @click="signUp"
          >
            회원가입
          </v-btn>
          <v-btn
            color="grey"
            block
            class="mb-4"
            style="font-size: 18px; padding: 12px;"
            @click="guest"
          >
            게스트 로그인
          </v-btn>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {

        // user 데이터 변수
        userId: '', // 아이디 변수
        password: '', // 비밀번호 변수
        
        
        // template 변수
        showPassword: false,  // 비밀번호 표시 여부 변수
        rules: {              // 입력 유효성 검사 조건 변수
        required: value => !!value || '필수 입력 사항입니다.',
        minLength: value => !!(value && value.length >= 6) || '최소 6자 이상 입력하세요.',
      },


      // 응답 변수
      response : null,
      };
    },
    methods: {
      // 로그인 메서드
      async login() {

      // 아이디 입력 형식이 잘못된 경우
      if(this.rules.required(this.userId) !== true || this.rules.minLength(this.userId) !== true){
        this.$toast.error("올바른 아이디 형식으로 입력해주세요.");
        return;
      }
        
      // 비밀번호 입력 형식이 잘못된 경우
      if(this.rules.required(this.password) !== true || this.rules.minLength(this.password) !== true){
        this.$toast.error("올바른 비밀번호 형식으로 입력해주세요.");
        return;
      }

      // 데이터 변환
      const USER = {
        userId : this.userId,
        password : this. password
      }

      // http 통신
      try{
        
        // 로그인 (http 통신)
        this.response = await this.$axios.post("/api/security/login", USER);
        
        // 토큰 값 로컬스토리지에 저장
        localStorage.setItem("token", this.response.headers["authorization"]);

        // 로그인 후 메인 페이지로 이동할 것이기 때문에 로컬스토리지에 처음 로그인 저장
        localStorage.setItem('first', "first");

        // 로그인 후 아이디 값 로컬스터토리지에 저장
        localStorage.setItem('userId', this.response.data.userId);

        // 홈페이지로 이동
        this.$router.push('/home');
      }
      
      // http 통신 에러 핸들링
      catch(error){
        if(error.response.status === 401){
          this.$toast.error("사용자 인증에 실패하였습니다.");
          localStorage.clear();
          return;
        }

        this.$toast.error("서버와 통신이 불안정합니다. 다시 시도해주세요.");

      }

      },

      // 회원가입 메서드
      signUp() {
        this.$router.push('/AddMember');
      },
      // 게스트 로그인 메서드
      guest() {
        this.$router.push('/Home');

      },
    },

    created(){
      if (localStorage.getItem('token')){
        if(confirm("현재 사용자 인증 상태입니다. 로그아웃 하시겠습니까?")){
          localStorage.clear();
        }
        else{
          this.$router.push('/home');
        }
      }
      }
  };
  </script>
  
  <style scoped>
  </style>
  