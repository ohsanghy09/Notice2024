<template>
  <v-container>
    <div>
      <h1>메시지 목록</h1>
      <ul>
        <li v-for="message in messages" :key="message.id">
          {{ message.id }}<br />
          {{ message.message }}
        </li>
      </ul>
    </div>

    <v-btn @click="PostMessage">보내기</v-btn>

    <v-text-field v-model="content" label="내용"></v-text-field>

    <v-btn @click="DeleteMessage">삭제하기</v-btn>

    <v-text-field v-model="deleteId" label="아이디 번호"></v-text-field>

    <v-btn @click="AddMessage">추가하기</v-btn>

    <v-text-field v-model="addMessage" label="내용"></v-text-field>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      messages: [], // 서버에서 가져온 메시지 리스트를 저장
      content: '',
      deleteId: '',
      addMessage: ''
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
