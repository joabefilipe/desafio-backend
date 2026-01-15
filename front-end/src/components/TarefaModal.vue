<script setup lang="ts">
import { computed, ref, watch } from "vue";
import type { StatusTarefa, Tarefa, TarefaPayload } from "../api/tarefa";

const props = defineProps<{
  open: boolean;
  modo: "criar" | "editar";
  tarefa?: Tarefa | null;
  loading?: boolean;
  erro?: string | null;
}>();

const emit = defineEmits<{
  (e: "close"): void;
  (e: "submit", payload: TarefaPayload): void;
}>();

const titulo = ref("");
const descricao = ref("");
const data = ref(""); // input datetime-local
const status = ref<StatusTarefa>("ATIVO");

const tituloModal = computed(() =>
  props.modo === "criar" ? "Cadastrar tarefa" : "Editar tarefa"
);
const textoBotao = computed(() =>
  props.modo === "criar" ? "Cadastrar" : "Salvar"
);

function isoParaDatetimeLocal(iso: string) {
  // "2026-01-01T10:00:00" -> "2026-01-01T10:00"
  if (!iso) return "";
  return iso.slice(0, 16);
}

function datetimeLocalParaIso(dt: string) {
  // "2026-01-01T10:00" -> "2026-01-01T10:00:00"
  if (!dt) return "";
  return dt.length === 16 ? `${dt}:00` : dt;
}

watch(
  () => props.open,
  (isOpen) => {
    if (!isOpen) return;

    const t = props.tarefa ?? null;

    titulo.value = t?.titulo ?? "";
    descricao.value = t?.descricao ?? "";
    data.value = isoParaDatetimeLocal(t?.data ?? "");
    status.value = (t?.status as StatusTarefa) ?? "ATIVO";
  }
);

function onSubmit() {
  emit("submit", {
    titulo: titulo.value,
    descricao: descricao.value,
    data: datetimeLocalParaIso(data.value),
    status: status.value,
  });
}

function close() {
  emit("close");
}
</script>

<template>
  <div v-if="open" class="overlay" @click.self="close">
    <div class="modal">
      <div class="header">
        <h2>{{ tituloModal }}</h2>
        <button class="x" @click="close">×</button>
      </div>

      <form @submit.prevent="onSubmit">
        <div class="field">
          <label>Título</label>
          <input v-model="titulo" required />
        </div>

        <div class="field">
          <label>Descrição</label>
          <textarea v-model="descricao" required rows="3" />
        </div>

        <div class="field">
          <label>Data</label>
          <input v-model="data" type="datetime-local" required />
        </div>

        <div class="field">
          <label>Status</label>
          <select v-model="status" required>
            <option value="ATIVO">ATIVO</option>
            <option value="DESATIVADO">DESATIVADO</option>
          </select>
        </div>

        <button class="primary" type="submit" :disabled="loading">
          {{ loading ? "Salvando..." : textoBotao }}
        </button>

        <p v-if="erro" class="error">{{ erro }}</p>
      </form>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  display: grid;
  place-items: center;
  padding: 16px;
}
.modal {
  width: 100%;
  max-width: 520px;
  background: #1a1a1a;
  border: 1px solid #334155;
  color: rgba(255, 255, 255, 0.92);
  border-radius: 14px;
  padding: 16px;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}
.x {
  border: none;
  background: transparent;
  font-size: 22px;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.9);
}
.field {
  margin-bottom: 12px;
  display: grid;
  gap: 6px;
}
input,
select,
textarea {
  padding: 10px;
  border: 1px solid #334155;
  border-radius: 10px;
  background: #242424;
  color: rgba(255, 255, 255, 0.92);
}
.primary {
  width: 100%;
  padding: 10px;
  border-radius: 10px;
  border: none;
  cursor: pointer;
  background: #2563eb;
  color: white;
}
.error {
  color: #ff6b6b;
  margin-top: 10px;
}
</style>
