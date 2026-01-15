<script setup lang="ts">
import { ref } from "vue";
import { useAuthStore } from "../stores/auth";
import RegistrarUsuarioModal from "../components/RegistrarUsuarioModal.vue";
import { useRouter } from "vue-router";

const login = ref("");
const password = ref("");
const error = ref<string | null>(null);
const loading = ref(false);
const showRegister = ref(false);

const auth = useAuthStore();
const router = useRouter();

async function onSubmit() {
  error.value = null;
  loading.value = true;

  try {
    await auth.login({ login: login.value, password: password.value });
    router.push("/tarefa");
  } catch {
    error.value = "Login inválido. Verifique usuário e senha.";
  } finally {
    loading.value = false;
  }
}

function onRegistered(payload: { login: string; password: string }) {
  login.value = payload.login;
  password.value = payload.password;
}
</script>

<template>
  <div class="login-container">
    <h1 style="margin-bottom: 16px">Login</h1>

    <form @submit.prevent="onSubmit">
      <div style="margin-bottom: 12px">
        <label>Login</label>
        <input v-model="login" type="text" required class="input" />
      </div>

      <div style="margin-bottom: 12px">
        <label>Senha</label>
        <input v-model="password" type="password" required class="input" />
      </div>

      <button type="submit" :disabled="loading" class="btn">
        {{ loading ? "Entrando..." : "Entrar" }}
      </button>

      <p v-if="error" style="color: red; margin-top: 10px">
        {{ error }}
      </p>

      <button
        type="button"
        @click="showRegister = true"
        class="btn btn-secondary"
      >
        Cadastrar usuário
      </button>
    </form>

    <RegistrarUsuarioModal
      :open="showRegister"
      @close="showRegister = false"
      @registered="onRegistered"
    />
  </div>
</template>

<style scoped>
.login-container {
  max-width: 360px;
  margin: 48px auto;
  padding: 16px;
  background: #1a1a1a;
  border-radius: 12px;
}

.input {
  width: 100%;
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #334155;
  background: #242424;
  color: rgba(255, 255, 255, 0.87);
}

.btn {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  border-radius: 8px;
  border: 1px solid transparent;
  cursor: pointer;
}

.btn-secondary {
  background: #242424;
}
</style>
