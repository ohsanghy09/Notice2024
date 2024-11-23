<template>
  <v-container class="d-flex justify-center align-center" style="min-height: 100vh; background-color: #f0f4f8;">
    <v-card class="pa-8" max-width="600">
      <v-card-title class="text-h5 text-center font-weight-bold">회원가입</v-card-title>
      <v-card-text>
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
            <template #append>
              <v-btn
                text
                color="primary"
                @click="checkId"
                style="font-size: 16px; padding: 0 16px;"
              >중복 확인</v-btn>
            </template>
          </v-text-field>

          <!-- 비밀번호 입력 -->
          <v-text-field
            v-model="password"
            label="비밀번호"
            outlined
            clearable
            dense
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
      <v-card-actions class="d-flex justify-center">
        <v-btn 
          color="success" 
          block 
          @click="AddMember"
          style="font-size: 18px; padding: 12px;"
        >
          회원가입
        </v-btn>
      </v-card-actions>
    </v-card>


  </v-container>
</template>

<script>
export default {
  data() {
    return {
      // user 입력값 변수
      userId: '',
      password: '',
      
      // template 변수
      showPassword: false,  // 비밀번호 표시 변수
      rules: {              // 입력 유효성 검사 조건 변수
        required: value => !!value || '필수 입력 사항입니다.',
        minLength: value => !!(value && value.length >= 6) || '최소 6자 이상 입력하세요.',
      },

      // http 통신 변수
      response : null,

      // 아이디 중복 확인 변수
      check : false, // 상태 변수
      nowCheckId : '' // 현재 중복확인한 아이디 변수


    };
  },
  methods: {
    // 아이디 중복확인 메서드
    async checkId() {      
      // 입력 유효성 검사

      // 아이디 입력값이 룰에 맞을 경우
      if (this.rules.required(this.userId) === true && this.rules.minLength(this.userId) === true) {
        
        // 데이터 변환
        const CHECKID = {
          userId : this.userId
        }

        // http 통신
        try{

          // 아이디 중복 확인(http 통신)
          this.response = await this.$axios.post("/api/user/checkId", CHECKID)

          // 해당 아이디를 사용할 수 없는 경우 안내문구 출력 및 중복확인 변수 상태 해제
          if(this.response.data.status === "NO"){
            this.$toast.error(this.response.data.message);
            this.check = false; // 중복확인 변수 상태 해제
            return;
          }

          // 해당 아이디를 사용할 수 있는 경우 안내문구 출력 및 중복확인 변수 상태 설정
          this.$toast.success(this.response.data.message);
          this.check = true;  // 중복확인 변수 상태 설정
          this.nowCheckId = this.userId; // 현재 중복확인 아이디 변수 설정

        }

        // http 통신 에러 핸들링
        catch(error){
          this.$toast.error("서버와 통신이 불안정합니다. 다시 시도해주세요.");
        }

      } 
      // 아이디 입력값이 룰에 맞지 않을 경우
      else {
        this.$toast.error('올바른 아이디 형식으로 입력해주세요.');
      }
    },




    // 회원가입 승인 메서드
    async AddMember() {

      // 입력 유효성 검사

      // 아이디 중복확인을 하지 않은 경우
      if(this.check === false){
        this.$toast.error("아이디 중복확인이 필요합니다.");
        return;
      }

      // 비밀번호 입력 형식이 잘못된 경우
      if(this.rules.required(this.password) !== true || this.rules.minLength(this.password) !== true){
        this.$toast.error("올바른 비밀번호 형식으로 입력해주세요.");
        return;
      }

      // 현재 입력한 아이디와 중복확인한 아이디가 맞지 않는 경우
      if(this.nowCheckId !== this.userId){
        this.$toast.error(`현재 입력한 아이디가 중복확인한 아이디와 맞지 않습니다.\n 현재 중복확인된 아이디 : ${this.nowCheckId}`);
        return;
      }


      // 데이터 변환
      const USER = {
        userId : this.userId,
        password : this.password
      }

      // http 통신
      try{

        // 회원 정보 추가(http 통신)
        this.response = await this.$axios.post('/api/user/AddUser', USER);

        // 회원 가입 성공
        this.$toast.success(this.response.data);
        this.$router.push('/Login');
      }

      // http 통신 에러 핸들링
      catch(error){
        this.$toast.error("서버와 통신이 불안정합니다. 다시 시도해주세요.");
      }

    },
  },
};
</script>

<style scoped>
.v-btn {
  font-size: 16px;
  text-transform: none;
}

.v-text-field {
  font-size: 18px;
}

.v-card {
  border-radius: 12px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.1);
}
</style>
