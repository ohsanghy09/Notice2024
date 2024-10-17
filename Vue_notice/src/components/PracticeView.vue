<template>
  <div>
    <h2>날짜 기준 정렬된 리스트</h2>
    <ul>
      <li v-for="(item, index) in sortedDates" :key="index">
        {{ item.title }} - {{ item.date }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dates: [
        { title: "제목1", date: "2024-10-17 11:30:45" },
        { title: "제목2", date: "2024-10-16 11:30:45" },
        { title: "제목3", date: "2024-10-11 11:30:45" },
        { title: "제목4", date: "2024-10-18 11:30:45" },
        { title: "제목5", date: "2024-10-12 11:30:45" }
      ],
      new_data : []
    };
  },
  computed: {
    sortedDates() {
      return [...this.dates].sort((a, b) => { // 얕은 복사로 배열 복사 후 sort 메서드 설정
        return new Date(a.date) - new Date(b.date); // 문자열의 타입을 Date 객체로 변환하여 계산하면 a-b = -? 일경우 a가 이전 인덱스로 설정됨.
      });
    }
  },
  watch : {
    sortedDates(newSortedDates){
      this.new_data = newSortedDates;
    }

  },
  created(){
    this.new_data = this.sortedDates;
    console.log(this.new_data);
  }
};
</script>
