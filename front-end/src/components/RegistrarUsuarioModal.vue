<script setup lang="ts">
import { ref, watch } from "vue";
import { registrarUsuario, type UserRole } from "../api/usuario";

const props = defineProps<{
  open: boolean;
}>();

const emit = defineEmits<{
  (e: "close"): void;
  (
    e: "registered",
    payload: { login: string; password: string; role: UserRole }
  ): void;
}>();

const login = ref("");
const password = ref("");
const role = ref<UserRole>("USER");

const loading = ref(false);
const error = ref<string | null>(null);
const success = ref<string | null>(null);

watch(
  () => props.open,
  (isOpen) => {
    if (isOpen) {
      login.value = "";
      password.value = "";
      role.value = "USER";
      error.value = null;
      success.value = null;
    }
  }
);

async function onSubmit() {
  error.value = null;
  success.value = null;
  loading.value = true;

  try {
    await registrarUsuario({
      login: login.value,
      password: password.value,
      role: role.value,
    });
    success.value = "Usuário cadastrado com sucesso!";
    emit("registered", {
      login: login.value,
      password: password.value,
      role: role.value,
    });

    setTimeout(() => emit("close"), 600);
  } catch (e: any) {
    error.value = "Não foi possível cadastrar. Talvez o login já exista.";
  } finally {
    loading.value = false;
  }
}

function close() {
  emit("close");
}
</script>

<template>
  <div v-if="open" class="overlay" @click.self="close">
    <div class="modal">
      <div class="header">
        <h2>Cadastrar usuário</h2>
        <button class="x" @click="close">×</button>
      </div>

      <form @submit.prevent="onSubmit">
        <div class="field">
          <label>Login</label>
          <input v-model="login" required />
        </div>

        <div class="field">
          <label>Senha</label>
          <input v-model="password" type="password" required />
        </div>

        <div class="field">
          <label>Role</label>
          <select v-model="role" required>
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </div>

        <button class="primary" type="submit" :disabled="loading">
          {{ loading ? "Cadastrando..." : "Cadastrar" }}
        </button>

        <p v-if="success" class="success">{{ success }}</p>
        <p v-if="error" class="error">{{ error }}</p>
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
  max-width: 420px;
  background: #1a1a1a;
  color: rgba(255, 255, 255, 0.87);
  border-radius: 12px;
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
}
.field {
  margin-bottom: 12px;
  display: grid;
  gap: 6px;
}
input,
select {
  padding: 10px;
  border: 1px solid #334155;
  border-radius: 8px;
  background: #242424;
  color: rgba(255, 255, 255, 0.87);
}
select option {
  background: #242424;
  color: rgba(255, 255, 255, 0.87);
}
.primary {
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
}
.error {
  color: #b00020;
  margin-top: 10px;
}
.success {
  color: #137333;
  margin-top: 10px;
}
</style>
