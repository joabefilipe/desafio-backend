import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import TarefaView from "../views/TarefaView.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/login" },
    { path: "/login", component: LoginView },
    { path: "/tarefa", component: TarefaView },
  ],
});

export default router;
