<template>
  <div>
    <h1>게시글 작성</h1>
    <input v-model="title" placeholder="제목" />
    <textarea v-model="content" placeholder="내용"></textarea>
    <button @click="submitPost">작성하기</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      content: ''
    };
  },
  methods: {
    submitPost() {
      const postData = {
        title: this.title,
        content: this.content
      };

      axios
        .post('http://localhost:8080/api/posts', postData)
        .then((response) => {
          console.log('게시글이 성공적으로 전송되었습니다:', response.data);
        })
        .catch((error) => {
          console.error('게시글 전송 실패:', error);
        });
    }
  }
};
</script>
