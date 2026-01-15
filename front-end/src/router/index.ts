import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import TarefaView from "../views/TarefaView.vue";
import TarefaCadastroEdicaoView from "../views/TarefaCadastroEdicaoView.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/login" },
    { path: "/login", component: LoginView },
    { path: "/tarefa", component: TarefaView },
    { path: "/tarefa/nova", component: TarefaCadastroEdicaoView },
    { path: "/tarefa/:id", component: TarefaCadastroEdicaoView, props: true },
  ],
});

export default router;
