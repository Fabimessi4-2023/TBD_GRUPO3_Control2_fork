<template>
  <v-container>
    <div class="d-flex align-center mb-6 position-relative">
      <h1 class="text-h4 mx-auto">Mis Tareas</h1>

      <!-- AÑADIR TAREAS FORMULARIO-->
      <v-dialog v-model="dialog" max-width="500">
        <template #activator="{ props }">
          <v-btn v-bind="props" color="primary" prepend-icon="mdi-plus">
            Nueva Tarea
          </v-btn>
        </template>

        <v-card>
          <v-card-title>
            <span class="text-h6">Crear Tarea</span>
          </v-card-title>

          <v-card-text>
            <v-text-field v-model="newTask.title" label="Título" required></v-text-field>
            <v-switch v-model="newTask.completed" label="Completada" />
          </v-card-text>

          <v-card-actions>
            <v-spacer />
            <v-btn text @click="dialog = false">Cancelar</v-btn>
            <v-btn color="primary" @click="addTask">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>

    <v-text-field
      v-model="search"
      label="Buscar tarea..."
      prepend-inner-icon="mdi-magnify"
      variant="outlined"
      hide-details
      class="mb-4"
    ></v-text-field>

    <v-card border flat>
      <v-data-table
        :headers="headers"
        :items="tasks"
        :search="search"
      >
        <template #item="{ item }">
          <tr>
            <td>{{ item.title }}</td>
            <td>
              <v-chip :color="item.completed ? 'success' : 'warning'">
                {{ item.completed ? 'Completada' : 'Pendiente' }}
              </v-chip>
            </td>
            <td>
              <v-btn icon variant="text" color="blue" @click="editTask(item)">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
              <v-btn icon variant="text" color="error" @click="deleteTask(item)">
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'TasksView',
  data() {
    return {
      dialog: false,
      newTask: { title: '', completed: false },
      search: '',
      headers: [
        { title: 'Título', value: 'title' },
        { title: 'Estado', value: 'status' },
        { title: 'Acciones', value: 'actions', sortable: false },
      ],
      tasks: [
        { id: 1, title: 'Tarea A', completed: false },
        { id: 2, title: 'Tarea B', completed: true },
      ],
    }
  },
  methods: {
    addTask() {
      if (!this.newTask.title) return
      const id = this.tasks.length ? Math.max(...this.tasks.map(t => t.id)) + 1 : 1
      this.tasks.push({ id, title: this.newTask.title, completed: this.newTask.completed })
      this.newTask = { title: '', completed: false }
      this.dialog = false
    },
    editTask(item) {
      // placeholder: navegar a edición o abrir modal
      console.log('edit', item)
    },
    deleteTask(item) {
      // placeholder: confirm + borrar
      console.log('delete', item)
    },
  },
}
</script>