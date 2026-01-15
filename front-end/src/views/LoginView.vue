<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth";

const login = ref("");
const password = ref("");
const error = ref<string | null>(null);
const loading = ref(false);

const auth = useAuthStore();
const router = useRouter();

async function onSubmit() {
  error.value = null;
  loading.value = true;

  try {
    await auth.login({ login: login.value, password: password.value });
    router.push("/tasks"); // vamos criar no próximo passo
  } catch {
    error.value = "Login inválido. Verifique usuário e senha.";
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div style="max-width: 360px; margin: 48px auto; padding: 16px;">
    <h1 style="margin-bottom: 16px;">Login</h1>

    <form @submit.prevent="onSubmit">
      <div style="margin-bottom: 12px;">
        <label>Login</label>
        <input v-model="login" type="text" required style="width: 100%; padding: 8px;" />
      </div>

      <div style="margin-bottom: 12px;">
        <label>Senha</label>
        <input v-model="password" type="password" required style="width: 100%; padding: 8px;" />
      </div>

      <button type="submit" :disabled="loading" style="width: 100%; padding: 10px;">
        {{ loading ? "Entrando..." : "Entrar" }}
      </button>

      <p v-if="error" style="color: red; margin-top: 10px;">{{ error }}</p>
    </form>
  </div>
</template>