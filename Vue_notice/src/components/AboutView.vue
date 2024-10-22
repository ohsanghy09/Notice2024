<template>
  <v-app>
    <v-container class="text-center">
      <v-row justify="center">
        <!-- 이미지 표시 -->
        <v-col cols="12">
          <v-img :src="currentImage" max-width="500" max-height="400" alt="Scenic Image"></v-img>
        </v-col>
      </v-row>

      <v-row justify="center" class="mt-5">
        <!-- 다음 이미지로 전환하는 버튼 -->
        <v-col cols="auto">
          <v-btn color="primary" @click="changeImage">다음 사진</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      // 이미지 배열
      images: [
        'https://via.placeholder.com/500x400.png?text=Image+1',
        'https://via.placeholder.com/500x400.png?text=Image+2',
        'https://via.placeholder.com/500x400.png?text=Image+3'
      ],
      currentIndex: 0 // 현재 이미지 인덱스
    };
  },
  computed: {
    // 현재 표시할 이미지
    currentImage() {
      return this.images[this.currentIndex];
    }
  },
  methods: {
    // 버튼을 클릭하면 다음 이미지로 변경
    changeImage() {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    }
  }
};
</script>

<style scoped>
v-img {
  margin: auto;
}
</style>