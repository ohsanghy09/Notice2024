<template>
  <v-container>
    <!-- 우측 하단 공지사항 추가 버튼 -->
    <v-btn fab fixed bottom right color="primary" @click="addClickBtn" rounded>
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
      rounded
      style="margin-right: 70px;"
    >
      <v-icon color="white" size="28">mdi-delete</v-icon>
    </v-btn>

    <!-- 공지사항 추가 다이얼로그 -->
    <v-dialog v-model="add_dialog" max-width="500px">
      <v-card rounded elevation="3">
        <v-card-title>
          <span class="headline">공지사항 추가</span>
        </v-card-title>

        <v-card-text>
          <v-text-field v-model="add_title" label="제목"></v-text-field>
          <v-text-field v-model="add_writer" label="작성자"></v-text-field>
          <v-textarea v-model="add_content" label="내용"></v-textarea>
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
      <v-card rounded elevation="3">
        <v-card-title>
          <span class="headline">공지사항 수정</span>
        </v-card-title>

        <v-card-text>
          <v-text-field v-model="update_title" label="제목"></v-text-field>
          <v-text-field readonly v-model="update_writer" label="작성자"></v-text-field>
          <v-textarea v-model="update_content" label="내용"></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="update_dialog = false">취소</v-btn>
          <v-btn color="blue darken-1" text @click="updateNotice">수정</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 스낵바: 알림 메시지 표시 -->
    <v-snackbar
      v-model="snackbar"
      :timeout="snackbarTimeout"
      transition="slide-y-reverse-transition"
      color="blue-grey lighten-4"
      text-color="black"
      rounded
    >
      {{ snackbarMessage }}
    </v-snackbar>

    <!-- 로딩 다이얼로그 -->
    <v-dialog v-model="isLoading" persistent hide-overlay width="300">
      <v-card color="#FFC107" dark rounded elevation="3">
        <v-card-text>
          <div class="text-center">
            <h3>{{ isLoadingMessage }}</h3>
          </div>
          <v-progress-circular
            indeterminate
            color="white"
            size="64"
            width="6"
          ></v-progress-circular>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- 공지사항 목록 및 카드 -->
    <v-row>
      <v-col>
        <v-card class="pa-5" outlined elevation="3" rounded>
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
        <v-card class="pa-5" outlined elevation="3" rounded>
          <v-card-title>
            현재 선택된 공지사항
            <v-btn icon @click="updateClickBtn">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon @click="deleteNotice">
              <v-icon color="red" size="28">mdi-delete</v-icon>
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
        <v-card outlined elevation="3" rounded>
          <v-card-title>이전 공지사항 목록</v-card-title>
          <v-list>
            <v-list-item
              v-for="notice in notices"
              :key="notice.time"
              @click="selectNotice(notice)"
            >
              <v-list-item-content>
                <v-list-item-title>
                  <h3>{{ notice.title }}</h3>
                  <p>{{ notice.writer }}</p>
                </v-list-item-title>
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
  data() {
    return {
      snackbar: false,
      snackbarMessage: '',
      snackbarTimeout: 4000,
      isLoading: false,
      isLoadingMessage: '',
      loadingTimer: null,
      now_time: null,
      add_dialog: false,
      add_title: '',
      add_content: '',
      add_writer: '',
      notices: [],
      select_id: '',
      select_title: '',
      select_content: '',
      select_writer: '',
      select_time: '',
      update_dialog: false,
      update_id: '',
      update_title: '',
      update_content: '',
      update_writer: '',
      update_time: '',
      delete_id: '',
      recent_id: '',
      recent_title: '',
      recent_writer: '',
      recent_content: '',
      recent_time: ''
    };
  },
  methods: {
    generateTime() {
      this.now_time = new Date();
      this.now_time.setHours(this.now_time.getHours() + 9);
      this.now_time = this.now_time.toISOString().slice(0, 19).replace('T', ' ');
    },
    addClickBtn() {
      this.add_dialog = true;
      this.add_title = '';
      this.add_content = '';
      this.add_writer = '';
    },
    async addNotice() {
      if (!this.add_title.trim()) {
        this.snackbar = true;
        this.snackbarMessage = '제목을 입력해주세요.';
        return;
      }
      if (!this.add_writer.trim()) {
        this.snackbar = true;
        this.snackbarMessage = '작성자를 입력해주세요.';
        return;
      }
      if (!this.add_content.trim()) {
        this.snackbar = true;
        this.snackbarMessage = '내용을 입력해주세요.';
        return;
      }

      this.generateTime();

      const addNotice = {
        title: this.add_title,
        content: this.add_content,
        writer: this.add_writer,
        time: this.now_time
      };

      try {
        this.loadingTimer = setTimeout(() => {
          this.isLoading = true;
          this.isLoadingMessage = '공지사항 등록 중..';
        }, 3000);

        await this.$axios.post('http://localhost:8080/api/notice/add', addNotice);

        clearTimeout(this.loadingTimer);
        this.isLoading = false;
        this.getNotice();
        this.snackbar = true;
        this.snackbarMessage = '공지사항이 추가되었습니다.';
        this.add_dialog = false;
      } catch (error) {
        this.snackbar = true;
        this.snackbarMessage = '서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.';
      }
    },
    async getNotice() {
      try {
        const response = await this.$axios.get('http://localhost:8080/api/notice/find');
        this.notices = response.data;
        this.notices.sort((a, b) => {
          return new Date(b.time) - new Date(a.time);
        });
        await this.recentNotice();
      } catch (error) {
        this.snackbar = true;
        this.snackbarMessage = '현재 공지사항 목록이 존재하지 않습니다. 공지사항을 추가해주세요.';
        
      }
    },
    selectNotice(notice) {
      this.select_id = notice.id;
      this.select_title = notice.title;
      this.select_writer = notice.writer;
      this.select_content = notice.content;
      this.select_time = notice.time;
    },
    updateClickBtn() {
      if (!this.select_id) {
        this.snackbar = true;
        this.snackbarMessage = '이전 공지사항 목록에서 수정할 공지사항을 선택해주세요.';
        return;
      }
      this.update_dialog = true;
      this.update_id = this.select_id;
      this.update_title = this.select_title;
      this.update_writer = this.select_writer;
      this.update_content = this.select_content;
      this.update_time = this.select_time;
    },
    async updateNotice() {
      if (!this.update_title.trim()) {
        this.snackbar = true;
        this.snackbarMessage = '제목을 입력해주세요.';
        return;
      }
      if (!this.update_content.trim()) {
        this.snackbar = true;
        this.snackbarMessage = '내용을 입력해주세요.';
        return;
      }

      this.generateTime();

      const updateNotice = {
        id: this.update_id,
        title: this.update_title,
        content: this.update_content,
        time: this.now_time
      };

      try {
        this.loadingTimer = setTimeout(() => {
          this.isLoading = true;
          this.isLoadingMessage = '공지사항 수정 중..';
        }, 3000);

        await this.$axios.post('http://localhost:8080/api/notice/update', updateNotice);

        clearTimeout(this.loadingTimer);
        this.isLoading = false;
        this.getNotice();
        this.snackbar = true;
        this.snackbarMessage = '공지사항이 수정되었습니다.';
        this.selectNotice(this.notices[0]);
        this.update_dialog = false;
      } catch (error) {
        this.snackbar = true;
        this.snackbarMessage = '서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.';
      }
    },
    async deleteNotice() {
      this.delete_id = this.select_id;
      if (!this.delete_id) {
        this.snackbar = true;
        this.snackbarMessage = '이전 공지사항 목록에서 삭제할 공지사항을 선택해주세요.';
        return;
      }

      const deleteNotice = {
        id: this.delete_id
      };

      try {
        this.loadingTimer = setTimeout(() => {
          this.isLoading = true;
          this.isLoadingMessage = '공지사항 삭제 중..';
        }, 3000);

        await this.$axios.post('http://localhost:8080/api/notice/delete', deleteNotice);

        clearTimeout(this.loadingTimer);
        this.isLoading = false;
        await this.getNotice();
        this.snackbar = true;
        this.snackbarMessage = '공지사항이 삭제되었습니다.';
        this.select_id = '';
        
        // 가장 최근 공지사항 초기화
        await this.resetRecentNotice();

      } catch (error) {
        this.snackbar = true;
        this.snackbarMessage = '서버 통신에서 에러가 발생했습니다. 다시 시도해주세요.';
      }
    },
    async deleteAll() {
      await this.$axios.delete('http://localhost:8080/api/notice/deleteAll');
      this.getNotice();
      this.snackbar = true;
      this.snackbarMessage = '공지사항 목록이 전체 삭제 되었습니다.';

      // 가장 최근 공지사항 초기화
      await this.resetRecentNotice();
    },

    // 가장 최근 등록된 공지사항 추가 메서드
    async recentNotice() {
      this.recent_id = this.notices[0].id;
      this.recent_title = this.notices[0].title;
      this.recent_writer = this.notices[0].writer;
      this.recent_content = this.notices[0].content;
      this.recent_time = this.notices[0].time;
    },

    // 가장 최근 등록된 공지사항 초기화 메서드
    async resetRecentNotice() {
      this.recent_id = '';
      this.recent_title = '';
      this.recent_writer = '';
      this.recent_content = '';
      this.recent_time = '';
    }
  },
  created() {
    this.getNotice();
  }
};
</script>

<style>
.custom-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
}

.v-card {
  border-radius: 12px;
}
</style>
