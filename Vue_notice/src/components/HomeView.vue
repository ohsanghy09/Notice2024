<template>
    <v-container fluid>
      <!-- 전체를 수직 중앙 정렬 -->
      <v-row align="center" justify="center" style="min-height: 100vh">
        
        <v-col cols="12">
          <v-row class="align-center">
            <!-- 버튼을 왼쪽에 추가 -->
            <v-col cols="5" justify="start">
              <v-btn color="secondary" @click="NoticeClick" x-large>공지사항</v-btn>
            </v-col>
  
            <!-- 상단 큰 문구 -->
            <v-col cols="auto" justify="center" class="text-center">
              <h1>풍경 갤러리</h1>
            </v-col>
          </v-row>
          
          <!-- 버튼 4개 배치 -->
          <v-row justify="center" class="my-5">
            <v-col cols="3">
              <v-btn block color="sky blue" @click="showImages(1)">푸른 하늘</v-btn>
            </v-col>
            <v-col cols="3">
              <v-btn block color="primary" @click="showImages(2)">바다</v-btn>
            </v-col>
            <v-col cols="3">
              <v-btn block color="soft orange" @click="showImages(3)">해질녘</v-btn>
            </v-col>
            <v-col cols="3">
              <v-btn block color="success" @click="showImages(4)">그 외</v-btn>
            </v-col>
          </v-row>
  
          <!-- 중앙 사각형 (선택된 사진을 표시하는 공간) -->
          <v-row justify="center" class="my-5">
            <v-col cols="8" class="text-center">
              <v-card outlined height="500px" class="d-flex align-center justify-center">
                <!-- 선택된 이미지 삽입 -->
                <v-img 
                  v-if="selectedImage"
                  :src="selectedImage" 
                  alt="선택된 사진" 
                  max-height="100%" 
                  class="selected-image"
                ></v-img>
                <v-card-text v-else>
                  <h1>버튼을 클릭하고 이미지를 클릭해주세요</h1>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
  
          <!-- 버튼을 누르면 이미지들이 나오는 섹션 -->
          <v-row justify="start" class="my-5" v-if="showImageList">
            <v-col cols="4" v-for="(image, index) in images" :key="index" class="text-center">
              <v-card outlined>
                <!-- 이미지 클릭 시 선택된 이미지를 card에 삽입 -->
                <v-img 
                  :src="image.url" 
                  :alt="image.title" 
                  height="200px" 
                  @click="handleImageClick(image.url)" 
                  class="image-effect"
                  :class="{'selected': selectedImage === image.url}"
                ></v-img>
              </v-card>
            </v-col>
          </v-row>
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
        images: [],  // 이미지와 제목 데이터를 배열로 관리, 처음에는 빈 배열로 시작
      };
    },
    methods: {
      showImages(buttonNumber) {
        this.showImageList = true;
  
        // 버튼에 따라 다른 이미지 목록을 설정
        if (buttonNumber === 1) {
          this.images = [
            {
              url: require('@/assets/sky/sky (1).jpg'),
              title: "sky 1"
            },
            {
              url: require('@/assets/sky/sky (2).jpg'),
              title: "sky 2"
            },
            {
              url: require('@/assets/sky/sky (3).jpg'),
              title: "sky 3"
            },
            {
              url: require('@/assets/sky/sky (4).jpg'),
              title: "sky 4"
            },
            {
              url: require('@/assets/sky/sky (5).jpg'),
              title: "sky 5"
            },
            {
              url: require('@/assets/sky/sky (6).jpg'),
              title: "sky 6"
            },
            {
              url: require('@/assets/sky/sky (7).jpg'),
              title: "sky 7"
            },
            {
              url: require('@/assets/sky/sky (8).jpg'),
              title: "sky 8"
            },
            {
              url: require('@/assets/sky/sky (9).jpg'),
              title: "sky 9"
            },
            {
              url: require('@/assets/sky/sky (10).jpg'),
              title: "sky 10"
            },
            {
              url: require('@/assets/sky/sky (11).jpg'),
              title: "sky 11"
            },
            {
              url: require('@/assets/sky/sky (12).jpg'),
              title: "sky 12"
            },
            {
              url: require('@/assets/sky/sky (13).jpg'),
              title: "sky 13"
            },
            {
              url: require('@/assets/sky/sky (14).jpg'),
              title: "sky 14"
            }
          ];

            this.selectedImage = this.images[0].url

        } else if (buttonNumber === 2) {
          this.images = [
            {
              url: require('@/assets/sea/sea (1).jpg'),
              title: "sea 1"
            },
            {
              url: require('@/assets/sea/sea (2).jpg'),
              title: "sea 2"
            },
            {
              url: require('@/assets/sea/sea (3).jpg'),
              title: "sea 3"
            },
            {
              url: require('@/assets/sea/sea (4).jpg'),
              title: "sea 4"
            },
            {
              url: require('@/assets/sea/sea (5).jpg'),
              title: "sea 5"
            }
          ];

          this.selectedImage = this.images[0].url;

        } else if (buttonNumber === 3) {
          this.images = [
            {
              url: require('@/assets/sunset/sunset (1).jpg'),
              title: "sunset 1"
            },
            {
              url: require('@/assets/sunset/sunset (2).jpg'),
              title: "sunset 2"
            },
            {
              url: require('@/assets/sunset/sunset (3).jpg'),
              title: "sunset 3"
            },
            {
              url: require('@/assets/sunset/sunset (4).jpg'),
              title: "sunset 4"
            },
            {
              url: require('@/assets/sunset/sunset (5).jpg'),
              title: "sunset 5"
            },
            {
              url: require('@/assets/sunset/sunset (6).jpg'),
              title: "sunset 5"
            }
          ];

          this.selectedImage = this.images[0].url
        }else if (buttonNumber === 4) {
          this.images = [
            {
              url: require('@/assets/And so on/And so on (1).jpg'),
              title: "And so on 1"
            },
            {
              url: require('@/assets/And so on/And so on (2).jpg'),
              title: "And so on 2"
            },
            {
              url: require('@/assets/And so on/And so on (3).jpg'),
              title: "And so on 3"
            },
            {
              url: require('@/assets/And so on/And so on (4).jpg'),
              title: "And so on 4"
            },
            {
              url: require('@/assets/And so on/And so on (5).jpg'),
              title: "And so on 5"
            },
            {
              url: require('@/assets/And so on/And so on (6).jpg'),
              title: "And so on 6"
            },
            {
              url: require('@/assets/And so on/And so on (7).jpg'),
              title: "And so on 7"
            },
            {
              url: require('@/assets/And so on/And so on (8).jpg'),
              title: "And so on 8"
            },
            {
              url: require('@/assets/And so on/And so on (10).jpg'),
              title: "And so on 10"
            },
            {
              url: require('@/assets/And so on/And so on (11).jpg'),
              title: "And so on 11"
            }
          ];

          this.selectedImage = this.images[0].url
        }
      },
      handleImageClick(imageUrl) {
        this.selectedImage = imageUrl;  // 클릭한 이미지를 선택된 이미지로 설정
      },
      NoticeClick(){
        // 공지사항 페이지로 리다이렉트
      this.$router.push('/Notice');
      }
    },

    created(){
      if(localStorage.getItem("first")){
        this.$toast.success("로그인에 성공하였습니다.");
        localStorage.removeItem("first");
        return;
      }
    }
  };
  </script>
  
  <style scoped>
/* 이미지 클릭 시 확대 효과 */
.image-effect {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.image-effect:hover {
  transform: scale(1.05); /* 이미지 확대 효과 */
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
}

/* 선택된 이미지 스타일 */
.selected {
  border: 3px solid #42A5F5; /* 선택된 이미지 테두리 강조 */
  transform: scale(1.1); /* 선택된 이미지 더 크게 확대 */
}
</style>
  