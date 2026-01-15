import { defineStore } from "pinia";
import type { StatusTarefa } from "../api/tarefa";

export type FiltroState = {
  titulo: string;
  status: StatusTarefa | "";
};

export const useTarefasFiltroStore = defineStore("tarefasFiltro", {
  state: (): FiltroState => ({
    titulo: "",
    status: "",
  }),

  actions: {
    setTitulo(v: string) {
      this.titulo = v;
    },
    setStatus(v: StatusTarefa | "") {
      this.status = v;
    },
    limpar() {
      this.titulo = "";
      this.status = "";
    },
  },
});
