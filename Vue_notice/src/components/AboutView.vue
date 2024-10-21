<template>
  <v-container>
    <!-- 이전 공지사항 목록 -->
    <v-row class="mt-5" align="center" justify="space-between">
      <v-col cols="12" md="3" order="1">
        <v-card-title class="primary--text">이전 공지사항 목록</v-card-title>
      </v-col>

      <v-col cols="12" md="2" order="2" class="d-flex">
        <v-menu offset-y transition="scale-transition" bottom min-width="200" class="mr-2">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="success" v-bind="attrs" v-on="on" style="width: 120px; height: 50px;">
              {{ searchOption }}
            </v-btn>
          </template>

          <v-list>
            <v-list-item @click="selectOption('기본 옵션')">
              <v-list-item-title>기본 옵션</v-list-item-title>
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

      <v-col cols="12" md="5" order="3" class="d-flex">
        <!-- 검색 텍스트 필드 추가 -->
        <v-text-field
          v-model="searchText"
          label="검색어 입력"
          outlined
          style="width: 250px; height: 50px; margin-right: 8px;"
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="2" order="4" class="d-flex">
        <!-- 검색 버튼 추가 -->
        <v-btn color="primary" @click="performSearch" style="width: 100px; height: 50px;">검색</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      searchOption: '기본 옵션',
      searchText: ''
    };
  },
  methods: {
    selectOption(option) {
      this.searchOption = option;
    },
    performSearch() {
      // 검색 로직을 구현하세요.
      console.log(`Searching for: ${this.searchText} with option: ${this.searchOption}`);
    }
  }
};
</script>

<style scoped>
.notice-card {
  width: 100%;
}

/* 추가적인 스타일을 통해 간격을 조정할 수 있습니다. */
.v-menu {
  margin-right: 8px; /* 메뉴와 버튼 간의 간격을 줄임 */
}
</style>
