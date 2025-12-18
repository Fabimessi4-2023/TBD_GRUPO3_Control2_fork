<template>
  <v-app>
  <v-card>
      <v-layout>
        <v-navigation-drawer
          app
          expand-on-hover
          permanent
          rail
        >
        <v-list>
          <v-list-item>
          </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
          <v-list-item prepend-icon="mdi-folder" title="Gestion de tareas" value="myfiles" :to="'/tasks'" link></v-list-item>
          <v-list-item prepend-icon="mdi-account-multiple" title="Análisis Geoespacial" value="shared" :to="'/analysis/nearest'" link></v-list-item>
        </v-list>
      </v-navigation-drawer>

        <v-main style="height: 100vh">
          <router-view />
        </v-main>
      </v-layout>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: 'MainLayout',
}
</script>