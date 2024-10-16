<template>
  <div>
    <h1>메시지 목록</h1>
    <ul>
      <li v-for="message in messages" :key="message.id">
        {{ message.message }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      messages: []  // 서버에서 가져온 메시지 리스트를 저장
    };
  },
  mounted() {
    // 페이지가 로드될 때 서버로부터 메시지 데이터를 가져옵니다.
    this.getMessages();
  },
  methods: {
    getMessages() {
      axios.get('http://localhost:8080/api/messages')
        .then((response) => {
          this.messages = response.data;  // 서버로부터 받은 데이터를 messages 배열에 저장
          console.log(this.messages)
        })
        .catch((error) => {
          console.error('메시지 가져오기 실패:', error);
        });
    }
  }
};
</script>
