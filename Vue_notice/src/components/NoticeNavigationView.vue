<template>
  <v-container>

    <!-- 우측 하단 공지사항 추가 버튼 -->
    <v-btn
      fab
      fixed
      bottom
      right
      color="primary"
      dark
      @click="addClickBtn"
      style="box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);"
    >
      <v-icon>mdi-plus</v-icon>
    </v-btn>

    <!-- 우측 하단 공지사항 삭제 버튼 (빨간색) -->
    <v-btn
      fab
      fixed
      bottom
      right
      color="error"
      dark
      @click="deleteAll"
      style="margin-right: 70px; box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);"
    >
      <v-icon>mdi-delete</v-icon>
    </v-btn>

    <!-- 공지사항 추가 다이얼로그 -->
    <v-dialog v-model="add_dialog" max-width="500px">
      <v-card elevation="2" class="rounded-xl">
        <v-card-title class="headline primary--text">공지사항 추가</v-card-title>
        <v-card-text>
          <v-text-field v-model="add_title" label="제목" outlined></v-text-field>
          <v-text-field readonly v-model="add_writer" label="작성자" outlined></v-text-field>
          <v-textarea v-model="add_content" label="내용" outlined></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" text @click="add_dialog = false">취소</v-btn>
          <v-btn color="primary" dark @click="addNotice">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 공지사항 수정 다이얼로그 -->
    <v-dialog v-model="update_dialog" max-width="500px">
      <v-card elevation="2" class="rounded-xl">
        <v-card-title class="headline primary--text">공지사항 수정</v-card-title>
        <v-card-text>
          <v-text-field v-model="update_title" label="제목" outlined></v-text-field>
          <v-text-field readonly v-model="update_writer" label="작성자" outlined></v-text-field>
          <v-textarea v-model="update_content" label="내용" outlined></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" text @click="update_dialog = false">취소</v-btn>
          <v-btn color="primary" dark @click="updateNotice">수정</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 스낵바: 알림 메시지 표시 -->
    <v-snackbar v-model="snackbar" :timeout="snackbarTimeout" top>
      {{ snackbarMessage }}
    </v-snackbar>

    <!-- 로딩 다이얼로그 -->
    <v-dialog v-model="isLoading" persistent hide-overlay width="300">
      <v-card color="#FFC107" dark class="rounded-xl">
        <v-card-text>
          <div class="text-center">
            <h3>{{ isLoadingMessage }}</h3>
          </div>
          <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- 투명한 오버레이, 클릭 차단 -->
    <div v-if="isLoading" class="custom-overlay"></div>

    <!-- 가장 최근 등록된 공지사항 -->
    <v-row>

      <!-- 화살표 아이콘 버튼을 카드 왼쪽 바깥에 배치 -->
  <v-col cols="12" class="d-flex align-center" style="position: relative;">
    <v-btn icon @click="BackClick">
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
  </v-col>

      <v-col>
        <v-card class="pa-5 notice-card rounded-xl" outlined>
          <v-card-title class="primary--text">
            <v-icon left>mdi-bell-ring-outline</v-icon>
            최근 공지사항
          </v-card-title>
          <v-card-text>
            <div v-if="recent_id">
              <h2 class="title">{{ recent_title }}</h2>
              <p>{{ recent_content }}</p>
              <p class="writer">작성자: {{ recent_writer }}</p>
              <p class="time">작성날짜 : {{ recent_time }}</p>
            </div>
            <div v-else>
              <p>최근 공지사항이 없습니다.</p>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- 현재 선택된 공지사항 -->
    <v-row>
      <v-col>
        <v-card class="pa-5 notice-card rounded-xl" outlined>
          <v-card-title class="primary--text">
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
              <h2 class="title">{{ select_title }}</h2>
              <p>{{ select_content }}</p>
              <p class="writer">작성자: {{ select_writer }}</p>
              <p class="time">작성날짜: {{ select_time }}</p>
            </div>
            <div v-else>
              <p>현재 선택된 공지사항이 없습니다.</p>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- 테두리로 감싼 공지사항 목록 영역 -->
    <v-card class="mt-5 pa-5 rounded-xl" elevation="3" outlined style="border-radius: 12px;">
      <v-card-title class="primary--text">공지사항 목록</v-card-title>

      <!-- 이전 공지사항 목록 및 검색 -->
      <v-row class="mt-5" align="center" justify="start">
        <!-- 기본 옵션 버튼 -->
        <v-col cols="auto">
          <v-menu offset-y transition="scale-transition" bottom min-width="200">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="success" v-bind="attrs" v-on="on" large class="rounded-xl">
                {{ searchOption }}
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="selectOption('전체')">
                <v-list-item-title>전체</v-list-item-title>
              </v-list-item>
              <v-list-item @click="selectOption('제목')">
                <v-list-item-title>제목</v-list-item-title>
              </v-list-item>
              <v-list-item @click="selectOption('작성자')">
                <v-list-item-title>작성자</v-list-item-title>
              </v-list-item>
              <v-list-item @click="selectOption('내용')">
                <v-list-item-title>내용</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-col>

        <!-- 검색어 입력 필드 -->
        <v-col cols="5">
          <v-text-field
            v-model="search_text"
            label="검색어를 입력해주세요."
            outlined
            dense
            hide-details
            class="rounded-xl"
          ></v-text-field>
        </v-col>

        <!-- 검색 버튼 -->
        <v-col cols="auto">
          <v-btn color="primary" large @click="searchBtn(1)" class="rounded-xl">
            검색
          </v-btn>
        </v-col>
      </v-row>

      <!-- 공지사항 목록 -->
      <v-list class="mt-4">
        <v-list-item
          v-for="notice in notices"
          :key="notice.time"
          @click="selectNotice(notice)"
          class="notice-list-item"
          style="border-bottom: 1px solid #eee; padding: 10px 0;"
        >
          <v-list-item-content>
            <v-list-item-title>
              <strong>{{ notice.title }}</strong>
            </v-list-item-title>
            <v-list-item-subtitle>{{ notice.writer }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <!-- 페이지네이션 버튼들을 중앙으로 배치 -->
      <v-row justify="center" align="center" class="mt-4">
        <!-- 이전 페이지로 이동 -->
        <v-btn
          :disabled="current_button_Page === 1"
          @click="prevPage"
          class="nav-btn"
          outlined
        >
          이전
        </v-btn>

        <v-btn
          v-for="n in currentButtons"
          :key="n"
          @click="getByNotice(n)"
          class="photo-button"
          :color="activeButton === n ? 'success' : 'primary'"
          outlined
          style="margin-left: 5px; margin-right: 5px;"
        >
          {{ n }}
        </v-btn>

        <!-- 다음 페이지로 이동 -->
        <v-btn
          :disabled="current_button_Page === totalButtonPages"
          @click="nextPage"
          class="nav-btn"
          outlined
        >
          다음
        </v-btn>
      </v-row>
    </v-card>
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
      recent_time: '',
      

      current_button_Page: 1, // 현재 버튼 페이지
      set_button_page : 5, // 현재 버튼페이지에 나타낼 버튼 개수
      totalButton: null, // 전체 버튼 개수

      activeButton:null, // 현재 선택된 버튼    
      
      searchOption: '', // 현재 선택된 검색 옵션
      searchText:'', // 검색할 내용
      search_title: '', // 제목 검색(검색 옵션)
      search_writer: '', // 작성자 검색(검색 옵션)
      search_content: '', // 내용 검색(검색 옵션),

      search_text: '', // 검색어

      search_status:false, // 현재 검색 상태

      
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
      this.add_writer = localStorage.getItem("userId");
    },

    // 공지사항 추가 메서드
    async addNotice() {

      // 입력 조건(공백 제거)
      if (!this.add_title.trim()){
        this.$toast.error("제목을 입력해주세요.")
        return;
      }
      if (!this.add_writer.trim()){
        this.$toast.error("작성자를 입력해주세요.")
        return;
      }
      if (!this.add_content.trim()){
        this.toast.error("내용을 입력해주세요.")
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
        await this.$axios.post('/api/notice/add', addNotice);
        
        // 공지사항 등록 요청이 3초보다 빨리 된다면 타이머 제거
        clearTimeout(this.loadingTimer);

        // 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
        this.isLoading = false;

        // 성공하면 목록 초기화
        this.getByNotice(1);

        // 추가 안내문구 출력
        this.$toast.success("공지사항이 추가되었습니다.");

        // 현재 출력된 다이얼로그 닫기
        this.add_dialog = false;
      }
      
      // 통신에 오류가 발생할 경우
      catch(error){
        this.$toast.error("서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.");
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
        this.$toast.info("이전 공지사항 목록에서 수정할 공지사항을 선택해주세요.");
        return;
      }

      if(this.select_writer !== localStorage.getItem('userId')){
      this.$toast.error("작성자만 수정할 수 있습니다.");
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
      this.$toast.error("제목을 입력해주세요.");
      return;
    }
    if (!this.update_content.trim()){
      this.$toast.error("내용을 입력해주세요.");
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
  await this.$axios.post('/api/notice/update', updateNotice);
  
  // 공지사항 수정 요청이 3초보다 빨리 된다면 타이머 제거
  clearTimeout(this.loadingTimer);

  // 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
  this.isLoading = false;

  // 성공하면 목록 초기화
  await this.getByNotice(1);

  // 가장 최근 공지사항 초기화
  await this.recentNotice();

  // 추가 안내문구 출력
  this.$toast.success("공지사항이 수정되었습니다.");

  // 현재 선택한 공지사항으로 데이터 저장
  this.selectNotice(this.notices[0])


  // 현재 출력된 다이얼로그 닫기
  this.update_dialog = false;
}

// 통신에 오류가 발생할 경우
catch(error){
  this.$toast.error("서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.");
}
},
    
//공지사항 삭제 메서드
async deleteNotice() {

// 삭제할 데이터 아이디 저장
this.delete_id = this.select_id

// 현재 선택한 공지사항이 없이 삭제하려는 경우
if(!this.delete_id){
  this.$toast.info("이전 공지사항 목록에서 삭제할 공지사항을 선택해주세요.");
  return;
}

// 현재 작성자가 다른 작성자의 공지사항을 삭제하려는 경우
else if(this.select_writer !== localStorage.getItem("userId")){
  this.$toast.error("작성자만 삭제할 수 있습니다.");
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
await this.$axios.post('/api/notice/delete', deleteNotice);

// 공지사항 삭제 요청이 3초보다 빨리 된다면 타이머 제거
clearTimeout(this.loadingTimer);

// 만약 3초가 지나 clearTimeout이 무효가 되어 로딩 다이얼로그 표시 상태에서 통신이 성공할 경우 
this.isLoading = false;

// 성공하면 목록 초기화
await this.getByNotice(1);

// 추가 안내문구 출력
this.$toast.success("공지사항이 삭제되었습니다.");

// 현재 선택한 공지사항 삭제
this.select_id = ''


// 현재 출력된 다이얼로그 닫기
this.update_dialog = false;
}

// 통신에 오류가 발생할 경우
catch(error){
this.$toast.error("서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.");
}
},

// 전체 삭제
async deleteAll(){

  if(confirm(`${localStorage.getItem('userId')}님께서 만든 공지사항 내용들을 전체 삭제 하시겠습니까?`)){

  // 데이터 변환
  const USER = {
    writer : localStorage.getItem('userId')
  }

    const response = await this.$axios.post('/api/notice/deleteAll', USER);

    if(response.status !== 200){
        this.$toast.error("삭제할 데이터가 없습니다.");
        return;
      }


    await this.getByNotice(1);
    this.$toast.success("공지사항 목록이 전체 삭제 되었습니다.");
    // 현재 선택한 공지사항 삭제
    this.select_id = ''
  }

  },
  // 가장 최근 등록된 공지사항 관련 메서드
  async recentNotice(){

    if (this.notices.length === 0){
    this.recent_id = '';
    this.recent_title = '';
    this.recent_writer = '';
    this.recent_content = '';
    this.recent_time = '';
    return;
    }

    
    this.recent_id = this.notices[0].id;
    this.recent_title = this.notices[0].title;
    this.recent_writer = this.notices[0].writer;
    this.recent_content = this.notices[0].content;
    this.recent_time = this.notices[0].time;

   
  },


    // 이전 페이지로 이동
    prevPage() {

      // 만약 현재 버튼 페이지가 1보다 클 경우 하나 빼기
      if (this.current_button_Page > 1) {
        this.current_button_Page--; // 이전 버튼 페이지로 이동
        const start = (this.current_button_Page - 1) * this.set_button_page + 1; // 현재 버튼 페이지의 첫 번째 버튼 체크
        this.getByNotice(start) // 버튼을 기준으로 공지사항을 가져오는 메서드
      }
    },


    // 다음 페이지로 이동
    async nextPage() {
      // 만약 현재 버튼 페이지가 전체 버튼 페이지보다 적을 경우 하나 추가
      if (this.current_button_Page < this.totalButtonPages) {
        this.current_button_Page++; // 다음 버튼 페이지로 이동
        const start = (this.current_button_Page - 1) * this.set_button_page + 1; // 현재 버튼 페이지의 첫 번째 버튼 체크
        this.getByNotice(start); // 버튼을 인자로 넣으면 버튼별 공지사항 가져오는 메서드
      }
    },


    // 총 버튼 및 버튼 페이지 개수
    async countNotice(){

    // 전체 공지사항 개수 가져오기
    const response = await this.$axios.get("/api/notice/count")

    // 전체 공지사항 개수 / 한 페이지에 표시될 공지사항 개수 = 전체 버튼 개수
    this.totalButton = Math.ceil(response.data / 10)

    },


    // 버튼을 기준으로 공지사항 조회 메서드
    async getByNotice(n) {

      // 현재 선택된 버튼 표시
      this.activeButton = n;

      // console.log(`현재 ${n}번 버튼입니다.`);
      
      const start = (n - 1) * 10 + 1 // 공지사항 페이지별 가져와야하는 처음 공지사항의 인덱스

      if (this.search_status){
        this.searchBtn(n)
        return;
      }

      // 전체 목록 조회 메서드
      await this.countNotice();
      

      try{
        const response = await this.$axios.post('/api/notice/getByStart', { start });
        this.notices = response.data

        // 가장 최근 공지사항 목록 조회 메서드
        await this.recentNotice();

      }catch(error){
        this.$toast.error("현재 공지사항이 존재하지 않습니다. 공지사항을 추가해주세요.");

         // 가장 최근 공지사항 목록 조회 메서드
         await this.recentNotice();
        return;
      }
      
    },

    // 검색 선택 메서드
    selectOption(option){

      //현재 검색 상태 취소
      this.search_status = false;

      this.search_text = '';

    
      if(option === "전체"){
        // 전체 공지사항 조회
        this.getByNotice(1)

        // 현재 검색상태 취소
        this.search_status = false;
        
        this.$toast.success("전체 공지사항으로 조회되었습니다.");

        this.searchOption = option;
        return;
      }

      // 현재 검색 메서드 전역 변수로 저장
      this.searchOption = option;


      

    },

    // 검색 버튼 메서드(현재 버튼)
    async searchBtn(n){

      if(this.searchOption === "기본 옵션"){
        this.$toast.error("검색 옵션을 선택해주세요.");
        return;
      }

      if(this.searchOption === "전체"){
        this.$toast.error("검색 옵션을 선택해주세요.");
        return;
      }

      // 옵션 데이터 전달
      const searchOption = {
        option: this.searchOption,
        text: this.search_text
      }


      // 현재 선택된 버튼 표시
      this.activeButton = n;

      try{
        // 전체 공지사항 개수 가져오기(옵션 전체 공지사항 개수 가져오는 EndPoint)
        const response = await this.$axios.post("/api/notice/searchCount", searchOption)
        // 전체 공지사항 개수 / 한 페이지에 표시될 공지사항 개수 = 전체 버튼 개수
        this.totalButton = Math.ceil(response.data / 10) 
      
      } catch(error){
        this.$toast.error("서버에 에러가 발생했습니다.");
        return;
      }

      const start = (n - 1) * 10 + 1 // 공지사항 페이지별 가져와야하는 처음 공지사항의 인덱스

      // 옵션2 설정
      const searchOption2 = {
        option: this.searchOption,
        text: this.search_text,
        start: start
      }

      try{
        //옵션 적용으로 데이터 가져오는 http 통신
        const response = await this.$axios.post("/api/notice/searchNotice", searchOption2)
        this.notices = response.data;
        
        this.$toast.success('검색되었습니다.');

        // 현재 검색상태 
        this.search_status = true;
      }catch(error){
        this.$toast.error("서버에 에러가 발생했습니다.");
        return;
      }
    },

    BackClick(){
      // 홈페이지로 이동
      this.$router.push('/home');
    }

},























  // 페이지 생성 후 동작하는 라이프 사이클
  created(){
    this.getByNotice(1); // 공지사항 조회하는 메서드
    this.selectOption("기본 옵션")
    this.$checkToken();
  },
  computed: {
    // 현재 페이지에 표시할 버튼들 계산
    currentButtons() {
      const start = (this.current_button_Page - 1) * this.set_button_page + 1;
      return Array.from({ length: this.set_button_page }, (_, i) => start + i).filter(n => n <= this.totalButton);
    },
    // 총 페이지 수 계산
    totalButtonPages() {
      // 전체 버튼 개수 / 한 버튼 페이지에 나타낼 버튼의 개수 = 총 버튼 페이지 개수
      return Math.ceil(this.totalButton / this.set_button_page);
    }
  },
}


</script>

<style scoped>
.v-btn {
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

.v-card-title {
  font-weight: bold;
  font-size: 18px;
}

.title {
  font-size: 22px;
  font-weight: bold;
}

.writer, .time {
  font-size: 14px;
  color: #757575;
}

.notice-card {
  background-color: #F5F5F5;
  border-radius: 12px;
}

.notice-list-item {
  transition: background-color 0.3s ease;
}

.notice-list-item:hover {
  background-color: #e0f7fa;
}

.custom-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
}.photo-buttons {
  text-align: center;
  margin-top: 50px;
}

.photo-button {
  margin: 5px;
}

.pagination-controls {
  margin-top: 20px;
}

.nav-btn {
  margin: 5px;
}
</style>
