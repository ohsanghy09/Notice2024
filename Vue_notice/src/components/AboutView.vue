<template>
  <v-container fluid>
    <!-- 상단 큰 문구 -->
    <v-row justify="center" class="text-center">
      <v-col cols="12">
        <h1>여기는 홈입니다!</h1>
      </v-col>
    </v-row>

    <!-- 버튼 4개 배치 -->
    <v-row justify="center" class="my-5">
      <v-col cols="3">
        <v-btn block color="primary" @click="showImages">버튼 1</v-btn>
      </v-col>
      <v-col cols="3">
        <v-btn block color="primary">버튼 2</v-btn>
      </v-col>
      <v-col cols="3">
        <v-btn block color="primary">버튼 3</v-btn>
      </v-col>
      <v-col cols="3">
        <v-btn block color="primary">버튼 4</v-btn>
      </v-col>
    </v-row>

    <!-- 중앙 사각형 (선택된 사진을 표시하는 공간) -->
    <v-row justify="center" class="my-5">
      <v-col cols="8" class="text-center">
        <v-card outlined height="400px">
          <!-- 선택된 이미지 삽입 -->
          <v-img 
            v-if="selectedImage"
            :src="selectedImage" 
            alt="선택된 사진" 
            max-height="100%" 
          ></v-img>
          <v-card-text v-else>
            이미지를 선택해주세요!
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- 버튼을 누르면 이미지 3개와 제목이 나오는 섹션 -->
    <v-row justify="center" class="my-5" v-if="showImageList">
      <v-col cols="4" v-for="(image, index) in images" :key="index" class="text-center">
        <v-card outlined>
          <!-- 이미지 클릭 시 선택된 이미지를 card에 삽입 -->
          <v-img 
            :src="image.url" 
            :alt="image.title" 
            height="200px" 
            @click="handleImageClick(image.url)" 
          ></v-img>
          <v-card-title>{{ image.title }}</v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      showImageList: false,  // 버튼 클릭 시 이미지가 나타남
      selectedImage: null,  // 선택된 이미지를 저장
      images: [  // 이미지와 제목 데이터를 배열로 관리
        {
          url: "https://example.com/image1.jpg",
          title: "이미지 1"
        },
        {
          url: "https://example.com/image2.jpg",
          title: "이미지 2"
        },
        {
          url: "https://example.com/image3.jpg",
          title: "이미지 3"
        }
      ]
    };
  },
  methods: {
    showImages() {
      this.showImageList = true;  // 버튼을 클릭하면 이미지들이 나타남
    },
    handleImageClick(imageUrl) {
      this.selectedImage = imageUrl;  // 클릭한 이미지를 선택된 이미지로 설정
    }
  }
};
</script>

<style scoped>
/* 원하는 대로 추가 스타일을 넣을 수 있습니다. */
</style>
