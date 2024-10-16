<template>
  <div>
    <h1>메시지 수정</h1>
    <input v-model="message" placeholder="메시지를 입력하세요" />
    <button @click="sendMessage">메시지 보내기</button>
    <div v-if="updatedMessage">
      <h3>수정된 메시지:</h3>
      <p>{{ updatedMessage }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      message: '',
      updatedMessage: '' // 수정된 메시지를 저장할 변수
    };
  },
  methods: {
    sendMessage() {
      axios.post('http://localhost:8080/api/messages', { message: this.message })
        .then((response) => {
          // 서버에서 반환된 수정된 메시지를 저장
          this.updatedMessage = response.data.message;
        })
        .catch((error) => {
          console.error('메시지 전송 실패:', error);
        });
    }
  }
};
</script>
