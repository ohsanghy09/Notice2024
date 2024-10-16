<template>
  <v-container>
    <div>
      <h1>공지사항 목록</h1>
      <ul>
        <li v-for="message in messages" :key="message.id">
          {{ message.id }}<br />
          {{ message.message }}
        </li>
      </ul>
    </div>

    <v-text-field v-model="notice_title" label="제목"></v-text-field>
    <v-text-field v-model="notice_content" label="내용"></v-text-field>
    <v-text-field v-model="notice_writer" label="작성자"></v-text-field>
    <v-text-field v-model="notice_time" label="시간"></v-text-field>

    <v-btn>전송</v-btn>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      notices : [],
      notice_title: '',
      notice_content: '',
      notice_writer:'',
      notice_time:''
    };
  },
  mounted() {
    // 페이지가 로드될 때 서버로부터 메시지 데이터를 가져옵니다.
    this.getMessages();
  },
  methods: {
    async getMessages() {
      const response = await this.$axios.get('http://localhost:8080/api/messages/find');
      this.messages = response.data;
    },

    async PostMessage() {
      console.log(this.messages[0].id);
      const postId = {
        id: this.messages[0].id,
        message: this.content,
      };

      console.log(postId);
      await this.$axios.post('http://localhost:8080/api/messages/update', postId);
      this.getMessages();
    },

    async DeleteMessage() {
      const deleteId = {
        id: this.deleteId,
      };

      console.log(deleteId);
      await this.$axios.post('http://localhost:8080/api/messages/delete', deleteId);
      this.getMessages();
    },

    async AddMessage() {
      const AddContent = {
        message : this.addMessage
      };

      console.log(AddContent);
      await this.$axios.post('http://localhost:8080/api/messages/add', AddContent);
      this.getMessages();
    },
  },
};
</script>
