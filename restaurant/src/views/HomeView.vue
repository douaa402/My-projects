<template>
  <div class="home">Home</div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: "HomeView",
  mounted() {
    let user = localStorage.getItem('user-info');
    if (!user) {
      this.redirectTo({ val: 'signUp' });
    }
  },
  methods: {
    // Correct usage of mapActions to map Vuex action
    ...mapActions(['redirectTo']),
  },
};
</script>

