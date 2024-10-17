<template>
    <v-app>
      <!-- 상단에 큰 공지사항 표시하는 부분 -->
      <v-container>
        <v-row>
          <v-col>
            <v-card class="pa-5" outlined>
              <v-card-title>가장 최근 등록된 공지사항</v-card-title>
              <v-card-text>
                <div v-if="dayNotice">
                  <h2>{{ dayNotice.title }}</h2>
                  <p>{{ dayNotice.content }}</p>
                </div>
                <div v-else>
                  <p>공지사항이 없습니다~</p>
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
                <v-btn icon @click="openEditDialog(currentNotice)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <!-- 삭제 버튼 -->
                <v-btn icon @click="deleteNotice(currentNotice)">
                  <v-icon color="red">mdi-delete</v-icon>
                </v-btn>
              </v-card-title>
              <v-card-text>
                <div v-if="currentNotice">
                  <h2>{{ currentNotice.title }}</h2>
                  <p>{{ currentNotice.content }}</p>
                </div>
                <div v-else>
                  <p>공지사항이 없습니다.</p>
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
                  v-for="(notice, index) in notices"
                  :key="index"
                  @click="selectNotice(notice)"
                >
                  <v-list-item-content>
                    <v-list-item-title>{{ notice.title }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
  
      <!-- 우측 하단 버튼 -->
      <v-btn
        fab
        fixed
        bottom
        right
        color="primary"
        @click="dialog = true"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
  
      <!-- 공지사항 추가 다이얼로그 -->
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">공지사항 추가</span>
          </v-card-title>
  
          <v-card-text>
            <v-text-field
              v-model="newNotice.title"
              label="제목"
            ></v-text-field>
            <v-textarea
              v-model="newNotice.content"
              label="내용"
            ></v-textarea>
          </v-card-text>
  
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">취소</v-btn>
            <v-btn color="blue darken-1" text @click="addNotice">추가</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
  
      <!-- 공지사항 수정 다이얼로그 -->
      <v-dialog v-model="editDialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">공지사항 수정</span>
          </v-card-title>
  
          <v-card-text>
            <v-text-field
              v-model="editNotice.title"
              label="제목"
            ></v-text-field>
            <v-textarea
              v-model="editNotice.content"
              label="내용"
            ></v-textarea>
          </v-card-text>
  
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="editDialog = false">취소</v-btn>
            <v-btn color="blue darken-1" text @click="saveEdit">저장</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-app>
  </template>
  
  <script>
  export default {
    data() {
      return {
        dialog: false, // 다이얼로그 열림 여부 (추가)
        editDialog: false, // 다이얼로그 열림 여부 (수정)
        currentNotice: null, // 현재 선택된 공지사항
        newNotice: {
          title: '',
          content: '',
        }, // 새 공지사항 추가를 위한 데이터
        editNotice: {
          title: '',
          content: '',
        }, // 수정 공지사항을 위한 데이터
        notices: [
          { title: '첫 번째 공지사항', content: '첫 번째 공지사항 내용' },
          { title: '두 번째 공지사항', content: '두 번째 공지사항 내용' },
        ], // 기존 공지사항 목록
        dayNotice: {
          title: '',
          content: '',
        },
      };
    },
    mounted() {
      // 페이지가 로드되면 가장 최근 공지사항을 표시
      if (this.notices.length > 0) {
        this.currentNotice = this.notices[this.notices.length - 1];
      }
    },
    methods: {
      addNotice() {
        // 공지사항을 추가하고 다이얼로그를 닫음
        if (this.newNotice.title && this.newNotice.content) {
          const newAddedNotice = { ...this.newNotice };
          this.notices.push(newAddedNotice);
          this.dayNotice = newAddedNotice;
          this.newNotice.title = '';
          this.newNotice.content = '';
          this.dialog = false;
        }
      },
      selectNotice(notice) {
        // 공지사항 선택 시 현재 공지사항 업데이트
        this.currentNotice = notice;
      },
      openEditDialog(notice) {
        // 수정 다이얼로그 열고 선택된 공지사항 데이터를 편집용으로 설정
        this.editNotice = { ...notice };
        this.editDialog = true;
      },
      saveEdit() {
        // 수정 내용을 저장
        if (this.editNotice.title && this.editNotice.content) {
          const index = this.notices.findIndex(n => n === this.currentNotice);
          if (index !== -1) {
            this.notices.splice(index, 1, { ...this.editNotice });
            this.currentNotice = this.editNotice;
            this.dayNotice = this.editNotice;
          }
          this.editDialog = false;
        }
      },
      deleteNotice(notice) {
        // 공지사항 삭제
        const index = this.notices.indexOf(notice);
        if (index !== -1) {
          this.notices.splice(index, 1);
  
          // 현재 공지사항 삭제 시 다음 공지사항을 선택하거나 없을 경우 null 설정
          if (this.currentNotice === notice) {
            this.currentNotice = this.notices[this.notices.length - 1] || null;
          }
  
          // 최근 공지사항도 갱신
          this.dayNotice = this.notices[this.notices.length - 1] || null;
        }
      },
    },
  };
  </script>
  
  <style>
  body {
    font-family: "Roboto", sans-serif;
  }
  </style>
  