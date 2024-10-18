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

<!-- <template>
  <div class="photo-buttons">
    <h1>사진 목록</h1>

    <div>
      <v-btn
        v-for="n in currentButtons"
        :key="n"
        @click="handleClick(n)"
        class="photo-button"
        color="primary"
      >
        {{ n }}
      </v-btn>
    </div>

    <div class="pagination-controls">
      <v-btn
        :disabled="currentPage === 1"
        @click="prevPage"
        class="nav-btn"
      >
        이전
      </v-btn>

      <v-btn
        :disabled="currentPage === totalPages"
        @click="nextPage"
        class="nav-btn"
      >
        다음
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      totalButtons: 35, // 버튼의 총 개수
      buttonsPerPage: 10, // 한 페이지에 표시할 버튼 수
      currentPage: 1 // 현재 페이지
    };
  },
  computed: {
    // 현재 페이지에 표시할 버튼들 계산
    currentButtons() {
      const start = (this.currentPage - 1) * this.buttonsPerPage + 1;
      // const end = this.currentPage * this.buttonsPerPage;
      return Array.from({ length: this.buttonsPerPage }, (_, i) => start + i).filter(n => n <= this.totalButtons);
    },
    // 총 페이지 수 계산
    totalPages() {
      return Math.ceil(this.totalButtons / this.buttonsPerPage);
    }
  },
  methods: {
    // 이전 페이지로 이동
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    // 다음 페이지로 이동
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    // 버튼 클릭 처리
    async handleClick(n) {
      console.log(`${n}번 버튼을 클릭했습니다.`);
      const response = await this.$axios.get("http://localhost:8080/api/notice/count")
      console.log(response.data)
    }
  }
};
</script>

<style scoped>
.photo-buttons {
  text-align: center;
  margin-top: 50px;
}

.photo-button {
  margin: 5px;
}

.pagination-controls {
  margin-top: 20px;
}

.nav-btn {
  margin: 5px;
}
</style> -->

