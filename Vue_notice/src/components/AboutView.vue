<template>
  <v-container>
    <div>
      <h1>공지사항 목록</h1>
      <ul>
        <li v-for="notice in notices" :key="notice.id">
          {{ notice.id }}<br />
          {{ notice.title }}<br />
          {{ notice.content }}<br />
          {{ notice.writer }}<br />
          {{ notice.time }}<br />

        </li>
      </ul>
    </div>


    <v-text-field v-model="add_title" label="제목"></v-text-field>
    <v-text-field v-model="add_content" label="내용"></v-text-field>
    <v-text-field v-model="add_writer" label="작성자"></v-text-field>
    <v-text-field v-model="add_time" label="시간"></v-text-field>

    <v-btn @click="addNotice">등록</v-btn>


    <v-text-field v-model="update_id" label="아이디"></v-text-field>
    <v-text-field v-model="update_title" label="제목"></v-text-field>
    <v-text-field v-model="update_content" label="내용"></v-text-field>
    <v-text-field v-model="update_writer" label="작성자"></v-text-field>
    <v-text-field v-model="update_time" label="시간"></v-text-field>

    <v-btn @click="updateNotice">수정</v-btn>

  </v-container>
</template>

<script>
export default {
  data() {
    return {
      notices : [],
      
      // 공지사항 등록 관련 변수
      add_title: '',
      add_content: '',
      add_writer:'',
      add_time:'',

      // 공지사항 수정 관련 변수
      update_id: '',
      update_title: '',
      update_content: '',
      update_writer:'',
      update_time:'',
    };
  },
  mounted() {
    // 페이지가 로드될 때 서버로부터 메시지 데이터를 가져옵니다.
    this.getNotice();
  },
  methods: {
    async getNotice() {
      const response = await this.$axios.get('http://localhost:8080/api/notice/find');
      this.notices = response.data;
      console.log(this.notices)
    },


    // 공지사항 수정
    async updateNotice() {

      const updateNotice = {
        id : this.update_id,
        title : this.update_title,
        content : this.update_content,
        writer : this.update_writer,
        time : this. update_time
      }

      console.log(updateNotice)
      await this.$axios.post('http://localhost:8080/api/notice/update', updateNotice);
      this.getNotice();
    },

    async DeleteMessage() {
      const deleteId = {
        id: this.deleteId,
      };

      console.log(deleteId);
      await this.$axios.post('http://localhost:8080/api/messages/delete', deleteId);
      this.getMessages();
    },

    // 공지사항 추가
    async addNotice() {
      const addNotice = {
        title : this.add_title,
        content : this.add_content,
        writer : this.add_writer,
        time : this.add_time
      };

      console.log(addNotice);
      await this.$axios.post('http://localhost:8080/api/notice/add', addNotice);
      this.getNotice();
    },
  },
};
</script>
