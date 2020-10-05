<template>
  <router-view></router-view>
</template>

<script>
import Home from "@/views/Home";
export default {
  name: 'app',
  components: {
    Home
  }
}
</script>