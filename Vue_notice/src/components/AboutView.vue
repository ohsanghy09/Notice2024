<template>
  <v-container>
    <v-btn color="primary" @click="showSnackbar = true">
      Show Snackbar
    </v-btn>

    <v-snackbar
      v-model="showSnackbar"
      absolute
      top
      right
      :timeout="3000"
    >
      This is a snackbar!
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      showSnackbar: false // 스낵바 표시 여부
    };
  }
};
</script>
