<template>
  <v-container>

    <!-- 우측 하단 공지사항 추가 버튼 -->
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

      <!-- 우측 하단 공지사항 삭제 버튼 (빨간색) -->
      <v-btn
            fab
            fixed
            bottom
            right
            color="red"
            @click="deleteAll"
            style="margin-right: 70px;" 
          >
            <v-icon color="white">mdi-delete</v-icon>
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

      <!-- 공지사항 수정 다이얼로그 -->
      <v-dialog v-model="update_dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">공지사항 수정</span>
          </v-card-title>
  
          <v-card-text>
            <v-text-field
              v-model="update_title"
              label="제목"
            ></v-text-field>
            <v-text-field
              readonly
              v-model="update_writer"
              label="작성자"
            ></v-text-field>
            <v-textarea
              v-model="update_content"
              label="내용"
            ></v-textarea>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="update_dialog = false">취소</v-btn>
            <v-btn color="blue darken-1" text @click="updateNotice">수정</v-btn>
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

        <v-row>
          <v-col>
            <v-card class="pa-5" outlined>
              <v-card-title>가장 최근 등록된 공지사항</v-card-title>
              <v-card-text>
                <div v-if="recent_id">
                  <h2>{{ recent_title }}</h2>
                  <p>{{ recent_content }}</p>
                  <p>{{ recent_writer }}</p>
                  <p>{{ recent_time }}</p>
                </div>
                <div v-else>
                  <p>가장 최근 등록된 공지사항이 없습니다.</p>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>


        <v-row>
          <v-col>
            <v-card class="pa-5" outlined>
              <v-card-title>
                현재 선택된 공지사항
                <!-- 수정 버튼 -->
                <v-btn icon @click="updateClickBtn">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <!-- 삭제 버튼 -->
                <v-btn icon @click="deleteNotice">
                  <v-icon color="red">mdi-delete</v-icon>
                </v-btn>
              </v-card-title>
              <v-card-text>
                <div v-if="select_id">
                  <h2>{{ select_title }}</h2>
                  <p>{{ select_content }}</p>
                  <p>{{ select_writer }}</p>
                  <p>{{ select_time }}</p>
                </div>
                <div v-else>
                  <p>현재 선택된 공지사항이 없습니다.</p>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>


         <!-- 이전 공지사항 목록 표시 -->
         <v-row class="mt-5">
          <v-col>
            <v-card outlined>
              <v-card-title>이전 공지사항 목록</v-card-title>
              <v-list>
                <v-list-item
                  v-for="notice in notices"
                  :key="notice.time"
                  @click="selectNotice(notice)"
                >
                  <v-list-item-content>
                    <v-list-item-title><h3>{{ notice.title }}</h3> <p>{{ notice.writer }}</p></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>
        </v-row>

      

   
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

      // 공지사항 목록 변수
      notices : [],

      // 공지사항 선택 관련 변수
      select_id: '',
      select_title: '',
      select_content: '',
      select_writer:'',
      select_time:'',

      // 공지사항 수정 다이얼로그 변수
      update_dialog: false,

      // 공지사항 수정 관련 변수
      update_id: '',
      update_title: '',
      update_content: '',
      update_writer:'',
      update_time:'',

      // 공지사항 삭제 관련 변수
      delete_id: '',

      // 가장 최근 등록된 공지사항 관련 변수
      recent_id: '',
      recent_title: '',
      recent_writer: '',
      recent_content: '',
      recent_time: ''
      

      
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

        // 성공하면 목록 초기화
        this.getNotice();

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

    // 공지사항 조회
    async getNotice() {
      try{
      const response = await this.$axios.get('http://localhost:8080/api/notice/find');
      this.notices = response.data; // http 통신으로 받은 데이터를 공지사항 배열 변수에 저장
      
      // console.log(this.notices) // 정렬 전 목록 데이터

      // 배열 변수에 내용을 날짜 기준으로 정렬하여 저장
      this.notices.sort((a, b) =>{
        return new Date(b.time) - new Date(a.time);
      })

      // console.log(this.notices) // 정렬 후 목록 데이터

      // 가장 최근 등록된 공지사항 설정
      await this.recentNotice();


      }catch(error){
        this.snackbar = true;
        this.snackbarMessage = "현재 공지사항 목록이 존재하지 않습니다. 공지사항을 추가해주세요.";
        return;
      }
      

    },

    // 이전 공지사항 목록 클릭시 동작하는 메서드(현재 선택된 공지사항으로 적용)
    selectNotice(notice){
      this.select_id = notice.id;
      this.select_title = notice.title;
      this.select_writer = notice.writer;
      this.select_content = notice.content;
      this.select_time = notice.time;
    },

    // 수정 버튼을 클릭하면 나타나는 메서드(수정 다이얼로그 출력)
    updateClickBtn(){
      if(!this.select_id){
        this.snackbar = true;
        this.snackbarMessage = "이전 공지사항 목록에서 수정할 공지사항을 선택해주세요."
        return;
      }
      
      // 수정 다이얼로그 열기
      this.update_dialog = true;

      // 수정 다이얼로그 텍스트에 현재 선택한 데이터 저장
      this.update_id = this.select_id;
      this.update_title = this.select_title;
      this.update_writer = this.select_writer;
      this.update_content = this.select_content;
      this.update_time = this.select_time;
    },

    //공지사항 수정 메서드
    async updateNotice() {

    // 입력 조건(공백 제거)
    if (!this.update_title.trim()){
      this.snackbar = true;
      this.snackbarMessage = "제목을 입력해주세요."
      return;
    }
    if (!this.update_content.trim()){
      this.snackbar = true;
      this.snackbarMessage = "내용을 입력해주세요."
      return;
    }

    // 현재 시간 객체 생성
    this.generateTime();  // 다이얼로그에 공지사항 수정 버튼 클릭 시 현재 시간 객체에 바인딩

    // http 통신 데이터 선언(공지사항 수정)
    const updateNotice = {
      id : this.update_id,
      title : this.update_title,
      content : this.update_content,
      time : this.now_time
    };


try{

  // 3초 이상 지연 시 로딩 다이얼로그 출력
  this.loadingTimer = setTimeout(() => {
    this.isLoading = true;
    this.isLoadingMessage = "공지사항 수정 중.."
  }, 3000)


  // 공지사항 수정 요청
  await this.$axios.post('http://localhost:8080/api/notice/update', updateNotice);
  
  // 공지사항 수정 요청이 3초보다 빨리 된다면 타이머 제거
  clearTimeout(this.loadingTimer);

  // 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
  this.isLoading = false;

  // 성공하면 목록 초기화
  await this.getNotice();

  // 추가 안내문구 출력
  this.snackbar = true;
  this.snackbarMessage = "공지사항이 수정되었습니다.";

  // 현재 선택한 공지사항으로 데이터 저장
  this.selectNotice(this.notices[0])


  // 현재 출력된 다이얼로그 닫기
  this.update_dialog = false;
}

// 통신에 오류가 발생할 경우
catch(error){
  this.snackbar = true;
  this.snackbarMessage = "서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.";
}
},
    
//공지사항 삭제 메서드
async deleteNotice() {

// 삭제할 데이터 아이디 저장
this.delete_id = this.select_id

// 현재 선택한 공지사항이 없이 삭제하려는 경우
if(!this.delete_id){
  this.snackbar = true;
  this.snackbarMessage = "이전 공지사항 목록에서 삭제할 공지사항을 선택해주세요.";
  return;
}

// http 통신 데이터 선언(공지사항 삭제)
const deleteNotice = {
  id : this.delete_id
}

try{

// 3초 이상 지연 시 로딩 다이얼로그 출력
this.loadingTimer = setTimeout(() => {
this.isLoading = true;
this.isLoadingMessage = "공지사항 삭제 중.."
}, 3000)


// 공지사항 삭제 요청
await this.$axios.post('http://localhost:8080/api/notice/delete', deleteNotice);

// 공지사항 삭제 요청이 3초보다 빨리 된다면 타이머 제거
clearTimeout(this.loadingTimer);

// 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
this.isLoading = false;

// 성공하면 목록 초기화
await this.getNotice();

// 추가 안내문구 출력
this.snackbar = true;
this.snackbarMessage = "공지사항이 삭제되었습니다.";

// 현재 선택한 공지사항 삭제
this.select_id = ''


// 현재 출력된 다이얼로그 닫기
this.update_dialog = false;
}

// 통신에 오류가 발생할 경우
catch(error){
this.snackbar = true;
this.snackbarMessage = "서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.";
}
},

// 전체 삭제
async deleteAll(){
    await this.$axios.delete('http://localhost:8080/api/notice/deleteAll')
    this.getNotice();
    this.snackbar = true;
    this.snackbarMessage = "공지사항 목록이 전체 삭제 되었습니다."

  },
  // 가장 최근 등록된 공지사항 관련 메서드
  async recentNotice(){
    this.recent_id = this.notices[0].id
    this.recent_title = this.notices[0].title
    this.recent_writer = this.notices[0].writer
    this.recent_content = this.notices[0].content
    this.recent_time = this.notices[0].time
  }

},























  // 페이지 생성 후 동작하는 라이프 사이클
  created(){
    this.getNotice(); // 공지사항 조회하는 메서드
  }
}


</script>