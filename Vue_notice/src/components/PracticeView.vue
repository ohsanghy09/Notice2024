<template>
  <v-container>
  <div>
    <h2>날짜 기준 정렬된 리스트</h2>
    <ul>
      <li v-for="(item, index) in dates" :key="index">
        {{ item.title }} - {{ item.date }}
      </li>
    </ul>
  </div>

  <v-btn @click="Sorted">정렬</v-btn>
</v-container>

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

  methods : {
    Sorted(){
      this.dates.sort((a, b) =>{
        return new Date(a.date) - new Date(b.date);
      })
      console.log(this.dates)
    }
  },
  created(){
    // this.Sorted();
  }
};
</script>
