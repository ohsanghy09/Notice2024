<template>
  <v-container>

    <!-- 우측 하단 버튼 -->
    <v-btn
            fab
            fixed
            bottom
            right
            color="primary"
            @click="addClickBtn"
          >
            <v-icon>mdi-plus</v-icon>
          </v-btn>

      <!-- 공지사항 추가 다이얼로그 -->
      <v-dialog v-model="add_dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">공지사항 추가</span>
          </v-card-title>
  
          <v-card-text>
            <v-text-field
              v-model="add_title"
              label="제목"
            ></v-text-field>
            <v-text-field
              v-model="add_writer"
              label="작성자"
            ></v-text-field>
            <v-textarea
              v-model="add_content"
              label="내용"
            ></v-textarea>
          </v-card-text>
  
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="add_dialog = false">취소</v-btn>
            <v-btn color="blue darken-1" text @click="addNotice">추가</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- 스낵바: 알림 메시지 표시 -->
      <v-snackbar v-model="snackbar" :timeout="snackbarTimeout">
              {{ snackbarMessage }}
      </v-snackbar>

       <!-- 로딩 다이얼로그 -->
      <v-dialog v-model="isLoading" persistent hide-overlay width="300">
                    <v-card color="#FFC107" dark>
                      <v-card-text>
                        <div class="text-center">
                          <h3>{{ isLoadingMessage }}</h3>
                        </div>
                        <v-progress-linear
                          indeterminate
                          color="white"
                          class="mb-0"
                        ></v-progress-linear>
                      </v-card-text>
                    </v-card>
                  </v-dialog>
        <!-- 투명한 오버레이, 클릭 차단 -->
        <div v-if="isLoading" class="custom-overlay"></div>

      

   
    </v-container>
</template>

<script>
export default {
  data(){
    return {
      // 스낵바 설정
      snackbar: false, // 스낵바 표시 여부
      snackbarMessage: '',
      snackbarTimeout: 4000, // 스낵바 표시 시간

      isLoading: false, // 로딩 다이얼로그 상태
      isLoadingMessage: '', // 로딩 메시지

      loadingTimer:null, // 로딩 타이머

      // 날짜 관련 함수
      now_time:null,

      add_dialog: false, // 공지사항 등록 다이얼로그 상태 변수

      // 공지사항 등록 관련 변수
      add_title: '',
      add_content: '',
      add_writer:'',
      add_time:'',
      
    }
  },
  

  methods :{
    // 현재 시간 생성 메서드
    generateTime(){
      this.now_time = new Date() // 현재 날짜 객체 생성  
      this.now_time.setHours(this.now_time.getHours() + 9) // UTC - 협정 세계시에서 9시간을 객체에 추가
      this.now_time = this.now_time.toISOString().slice(0, 19).replace('T', ' ')  // 새로 설정된 시간을 문자열로 바꾸어 변수에 저장
    },

    // 공지사항 추가 버튼 클릭시 텍스트 필드 초기화 메서드
    addClickBtn(){
      this.add_dialog = true;
      this.add_title = '';
      this.add_content = '';
      this.add_writer = '';
    },

    // 공지사항 추가 메서드
    async addNotice() {

      // 입력 조건(공백 제거)
      if (!this.add_title.trim()){
        this.snackbar = true;
        this.snackbarMessage = "제목을 입력해주세요."
        return;
      }
      if (!this.add_writer.trim()){
        this.snackbar = true;
        this.snackbarMessage = "작성자를 입력해주세요."
        return;
      }
      if (!this.add_content.trim()){
        this.snackbar = true;
        this.snackbarMessage = "내용을 입력해주세요."
        return;
      }

      // 현재 시간 객체 생성
      this.generateTime();  // 다이얼로그에 공지사항 추가 버튼 클릭 시 현재 시간 객체에 바인딩
      
      // http 통신 데이터 선언(공지사항 등록)
      const addNotice = {
        title : this.add_title,
        content : this.add_content,
        writer : this.add_writer,
        time : this.now_time
      };


      try{

        // 3초 이상 지연 시 로딩 다이얼로그 출력
        this.loadingTimer = setTimeout(() => {
          this.isLoading = true;
          this.isLoadingMessage = "공지사항 등록 중.."
        }, 3000)


        // 공지사항 등록 요청
        await this.$axios.post('http://localhost:8080/api/notice/add', addNotice);
        
        // 공지사항 등록 요청이 3초보다 빨리 된다면 타이머 제거
        clearTimeout(this.loadingTimer);

        // 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
        this.isLoading = false;

        // 추가 안내문구 출력
        this.snackbar = true;
        this.snackbarMessage = "공지사항이 추가되었습니다.";

        // 현재 출력된 다이얼로그 닫기
        this.add_dialog = false;
      }
      
      // 통신에 오류가 발생할 경우
      catch(error){
        this.snackbar = true;
        this.snackbarMessage = "서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.";
      }
      
      
    },
  }
}


</script>