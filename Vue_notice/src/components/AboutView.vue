<template>
  <div>
    <button @click="fetchNotices(1)">1번 페이지</button>
    <button @click="fetchNotices(2)">2번 페이지</button>
    <!-- 공지사항 목록 -->
    <div v-for="notice in notices" :key="notice.id">
      <h3>{{ notice.title }}</h3>
      <p>{{ notice.content }}</p>
      <p>작성자: {{ notice.writer }}</p>
      <p>날짜: {{ notice.time }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      notices: [] // 공지사항 데이터를 저장할 배열
    };
  },
  methods: {
    async fetchNotices(page) {
      // 각 페이지의 start 값 계산
      const start = (page - 1) * 10 + 1;

      try {
        // 서버로 POST 요청을 보내고 데이터를 받아옴
        const response = await axios.post('http://localhost:8080/api/notice/getByStart', { start });
        this.notices = response.data; // 데이터를 공지사항 목록에 저장
      } catch (error) {
        console.error('데이터를 가져오는 중 오류 발생:', error);
      }
    }
  }
};
</script>
