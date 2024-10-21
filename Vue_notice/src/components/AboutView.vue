<template>
  <v-container>
    <!-- 여러 개의 버튼 생성 -->
    <v-row>
      <v-col v-for="(button, index) in buttons" :key="index">
        <v-btn
          :color="activeButton === index ? 'green' : 'primary'"
          @click="setActiveButton(index)"
        >
          Button {{ index + 1 }}
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      activeButton: null, // 클릭된 버튼의 인덱스
      buttons: ['Button 1', 'Button 2', 'Button 3'] // 버튼 배열
    };
  },
  methods: {
    setActiveButton(index) {
      this.activeButton = index; // 클릭된 버튼의 인덱스를 설정
    }
  }
};
</script>

<style>
/* 원하는 스타일을 추가할 수 있습니다 */
</style>
