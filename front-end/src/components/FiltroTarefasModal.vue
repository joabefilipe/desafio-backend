<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { useTarefasFiltroStore } from "../stores/tarefasFiltro";
import type { StatusTarefa } from "../api/tarefa";

const props = defineProps<{ open: boolean }>();
const emit = defineEmits<{
  (e: "close"): void;
  (e: "apply"): void;
}>();

const store = useTarefasFiltroStore();

const tituloLocal = ref(store.titulo);
const statusLocal = ref<StatusTarefa | "">(store.status);

watch(
  () => props.open,
  (open) => {
    if (open) {
      tituloLocal.value = store.titulo;
      statusLocal.value = store.status;
    }
  }
);

const temFiltro = computed(
  () => !!tituloLocal.value.trim() || !!statusLocal.value
);

function aplicar() {
  store.setTitulo(tituloLocal.value);
  store.setStatus(statusLocal.value);
  emit("apply");
  emit("close");
}

function limpar() {
  tituloLocal.value = "";
  statusLocal.value = "";
  store.limpar();
  emit("apply");
  emit("close");
}

function close() {
  emit("close");
}
</script>

<template>
  <div v-if="open" class="overlay" @click.self="close">
    <div class="modal">
      <div class="header">
        <h2>Filtrar tarefas</h2>
        <button class="x" @click="close">×</button>
      </div>

      <div class="field">
        <label>Título</label>
        <input v-model="tituloLocal" placeholder="Ex.: Criar DTOs" />
      </div>

      <div class="field">
        <label>Status</label>
        <select v-model="statusLocal">
          <option value="">Todos</option>
          <option value="ATIVO">ATIVO</option>
          <option value="DESATIVADO">DESATIVADO</option>
        </select>
      </div>

      <div class="acoes">
        <button class="btn btn--fantasma" @click="close">Cancelar</button>
        <button
          class="btn btn--fantasma"
          @click="limpar"
          :disabled="!temFiltro"
        >
          Limpar
        </button>
        <button class="btn" @click="aplicar">Aplicar</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.55);
  display: grid;
  place-items: center;
  padding: 16px;
  z-index: 50;
}

.modal {
  width: 100%;
  max-width: 460px;
  background: #1a1a1a;
  border: 1px solid #334155;
  border-radius: 14px;
  padding: 14px;
  color: rgba(255, 255, 255, 0.9);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.x {
  border: none;
  background: transparent;
  color: rgba(255, 255, 255, 0.9);
  font-size: 22px;
  cursor: pointer;
}

.field {
  display: grid;
  gap: 6px;
  margin-bottom: 12px;
}

input,
select {
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #334155;
  background: #242424;
  color: rgba(255, 255, 255, 0.9);
}

.acoes {
  display: flex;
  gap: 10px;
  justify-content: end;
  margin-top: 8px;
}

.btn {
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid transparent;
  cursor: pointer;
  background: #2563eb;
  color: white;
}

.btn--fantasma {
  background: #242424;
  border: 1px solid #334155;
}
</style>
