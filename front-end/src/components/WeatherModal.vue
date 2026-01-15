<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { buscarWeatherHoje, type WeatherResposta } from "../api/weather";

const props = defineProps<{
  open: boolean;
}>();

const emit = defineEmits<{
  (e: "close"): void;
}>();

const cidade = ref("");
const loading = ref(false);
const erro = ref<string | null>(null);
const resultado = ref<WeatherResposta | null>(null);

const podeBuscar = computed(
  () => cidade.value.trim().length >= 2 && !loading.value
);

async function buscar() {
  erro.value = null;
  resultado.value = null;

  const c = cidade.value.trim();
  if (!c) return;

  loading.value = true;
  try {
    resultado.value = await buscarWeatherHoje(c);
  } catch (e: any) {
    // se quiser, pode ler o status aqui
    erro.value = "Não foi possível buscar o clima. Verifique a cidade e a API.";
  } finally {
    loading.value = false;
  }
}

function fechar() {
  emit("close");
}

watch(
  () => props.open,
  (aberto) => {
    if (aberto) {
      // reset ao abrir (opcional)
      erro.value = null;
      resultado.value = null;
      // cidade.value = "";
    }
  }
);

// Se quiser mostrar o ícone do OpenWeather, normalmente a URL é assim:
const iconUrl = computed(() =>
  resultado.value?.icone
    ? `https://openweathermap.org/img/wn/${resultado.value.icone}@2x.png`
    : ""
);
</script>

<template>
  <div v-if="open" class="overlay" @click.self="fechar">
    <div class="modal">
      <header class="header">
        <h2 class="title">Clima de hoje</h2>
        <button class="x" @click="fechar">✕</button>
      </header>

      <div class="body">
        <label class="label">Cidade</label>
        <div class="row">
          <input
            v-model="cidade"
            placeholder="Ex: Florianópolis"
            @keydown.enter.prevent="buscar"
          />
          <button class="btn" :disabled="!podeBuscar" @click="buscar">
            {{ loading ? "Buscando..." : "Buscar" }}
          </button>
        </div>

        <p v-if="erro" class="erro">{{ erro }}</p>

        <div v-if="resultado" class="card">
          <div class="card-top">
            <div>
              <h3 class="cidade">{{ resultado.cidade }}</h3>
              <p class="condicao">{{ resultado.condicao }}</p>
            </div>

            <img
              v-if="iconUrl"
              :src="iconUrl"
              alt="Ícone do clima"
              class="icon"
            />
          </div>

          <div class="grid">
            <div class="item">
              <span class="k">Temperatura</span>
              <span class="v">{{ resultado.temperatura.toFixed(1) }}°C</span>
            </div>

            <div class="item">
              <span class="k">Umidade</span>
              <span class="v">{{ resultado.umidade }}%</span>
            </div>
          </div>
        </div>

        <div class="footer">
          <button class="btn ghost" @click="fechar">Fechar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: grid;
  place-items: center;
  padding: 18px;
  z-index: 999;
}
.modal {
  width: min(560px, 94vw);
  background: #111827;
  border: 1px solid #334155;
  border-radius: 14px;
  overflow: hidden;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #334155;
}
.title {
  margin: 0;
  font-size: 18px;
}
.x {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  font-size: 18px;
}
.body {
  padding: 16px;
}
.label {
  display: block;
  margin-bottom: 6px;
  opacity: 0.85;
}
.row {
  display: flex;
  gap: 10px;
  align-items: center;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #334155;
  border-radius: 10px;
  background: #0b1220;
  color: rgba(255, 255, 255, 0.92);
}

.btn {
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid transparent;
  cursor: pointer;
  background: #2563eb;
  color: white;
  white-space: nowrap;
}
.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.btn.ghost {
  background: #0b1220;
  border-color: #334155;
}

.erro {
  margin: 10px 0 0;
  background: rgba(176, 0, 32, 0.15);
  border: 1px solid rgba(176, 0, 32, 0.5);
  padding: 10px;
  border-radius: 10px;
}

.card {
  margin-top: 12px;
  background: #0b1220;
  border: 1px solid #334155;
  border-radius: 12px;
  padding: 12px;
}
.card-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}
.cidade {
  margin: 0;
  font-size: 16px;
}
.condicao {
  margin: 4px 0 0;
  opacity: 0.8;
}
.icon {
  width: 64px;
  height: 64px;
}

.grid {
  margin-top: 10px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}
.item {
  border: 1px solid #334155;
  border-radius: 10px;
  padding: 10px;
}
.k {
  display: block;
  font-size: 12px;
  opacity: 0.75;
}
.v {
  display: block;
  font-size: 14px;
  margin-top: 4px;
}

.footer {
  margin-top: 14px;
  display: flex;
  justify-content: end;
}
</style>
