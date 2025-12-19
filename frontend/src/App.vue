<template>
  <router-view v-if="['/login', '/register'].includes($route.path)" />
  <MainLayout v-else />
</template>

<script>
import MainLayout from './layouts/MainLayout.vue'

export default {
  components: { MainLayout },
}
</script>