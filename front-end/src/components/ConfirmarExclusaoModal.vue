<script setup lang="ts">
const props = defineProps<{
  open: boolean;
  titulo?: string;
  loading?: boolean;
  erro?: string | null;
}>();

const emit = defineEmits<{
  (e: "close"): void;
  (e: "confirm"): void;
}>();
</script>

<template>
  <div v-if="open" class="overlay" @click.self="emit('close')">
    <div class="modal">
      <h2>Excluir tarefa</h2>
      <p class="muted">
        Tem certeza que deseja excluir
        <strong>{{ titulo ?? "esta tarefa" }}</strong
        >?
      </p>

      <p v-if="erro" class="erro">{{ erro }}</p>

      <div class="acoes">
        <button
          class="btn btn--fantasma"
          :disabled="loading"
          @click="emit('close')"
        >
          Cancelar
        </button>
        <button
          class="btn btn--perigo"
          :disabled="loading"
          @click="emit('confirm')"
        >
          {{ loading ? "Excluindo..." : "Excluir" }}
        </button>
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
  max-width: 420px;
  background: #1a1a1a;
  border: 1px solid #334155;
  border-radius: 14px;
  padding: 16px;
}

.muted {
  opacity: 0.8;
  margin-top: 8px;
}

.erro {
  margin-top: 12px;
  padding: 10px;
  border-radius: 12px;
  background: rgba(176, 0, 32, 0.15);
  border: 1px solid rgba(176, 0, 32, 0.5);
}

.acoes {
  display: flex;
  justify-content: end;
  gap: 10px;
  margin-top: 16px;
}

.btn {
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid transparent;
  cursor: pointer;
  color: white;
}

.btn--fantasma {
  background: #242424;
  border-color: #334155;
}

.btn--perigo {
  background: #b00020;
}
</style>
