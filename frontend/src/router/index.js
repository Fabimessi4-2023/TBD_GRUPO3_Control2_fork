import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Tasks from '../views/Tasks.vue'
import CreateTask from '../views/CreateTask.vue'
import Nearest from '../views/analysis/Nearest.vue'
import Radius from '../views/analysis/Radius.vue'
import Clusters from '../views/analysis/Clusters.vue'

const routes = [
  { path: '/', name: 'Dashboard', component: Dashboard },
  { path: '/tasks', name: 'Tasks', component: Tasks },
  { path: '/tasks/create', name: 'CreateTask', component: CreateTask },
  { path: '/analysis/nearest', name: 'Nearest', component: Nearest },
  { path: '/analysis/radius', name: 'Radius', component: Radius },
  { path: '/analysis/clusters', name: 'Clusters', component: Clusters },
  { path: '/:pathMatch(.*)*', redirect: '/' },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
