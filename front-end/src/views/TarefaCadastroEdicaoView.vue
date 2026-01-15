<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  atualizarTarefa,
  buscarTarefaPorId,
  criarTarefa,
  type StatusTarefa,
  type TarefaPayload,
} from "../api/tarefa";

const route = useRoute();
const router = useRouter();

const id = computed(() => {
  const raw = route.params.id;
  return raw ? Number(raw) : null;
});

const modo = computed<"criar" | "editar">(() =>
  id.value ? "editar" : "criar"
);
const tituloPagina = computed(() =>
  modo.value === "criar" ? "Cadastrar tarefa" : "Editar tarefa"
);
const textoBotao = computed(() =>
  modo.value === "criar" ? "Cadastrar" : "Salvar"
);

const titulo = ref("");
const descricao = ref("");
const data = ref(""); // datetime-local
const status = ref<StatusTarefa>("ATIVO");

const carregando = ref(false);
const salvando = ref(false);
const erro = ref<string | null>(null);

function isoParaDatetimeLocal(iso: string) {
  if (!iso) return "";
  return iso.slice(0, 16);
}
function datetimeLocalParaIso(dt: string) {
  if (!dt) return "";
  return dt.length === 16 ? `${dt}:00` : dt;
}

async function carregar() {
  if (!id.value) return;

  carregando.value = true;
  erro.value = null;
  try {
    const t = await buscarTarefaPorId(id.value);
    titulo.value = t.titulo;
    descricao.value = t.descricao;
    data.value = isoParaDatetimeLocal(t.data);
    status.value = t.status;
  } catch {
    erro.value = "Não foi possível carregar a tarefa para edição.";
  } finally {
    carregando.value = false;
  }
}

async function salvar() {
  erro.value = null;
  salvando.value = true;

  const payload: TarefaPayload = {
    titulo: titulo.value,
    descricao: descricao.value,
    data: datetimeLocalParaIso(data.value),
    status: status.value,
  };

  try {
    if (modo.value === "criar") {
      await criarTarefa(payload);
    } else if (id.value) {
      await atualizarTarefa(id.value, payload);
    }
    router.push("/tarefa");
  } catch {
    erro.value = "Não foi possível salvar. Verifique permissões (ADMIN) e API.";
  } finally {
    salvando.value = false;
  }
}

function voltar() {
  router.push("/tarefa");
}

onMounted(carregar);
</script>

<template>
  <div class="pagina">
    <header class="topo">
      <div>
        <h1 class="titulo">{{ tituloPagina }}</h1>
        <p class="subtitulo">Preencha os campos e salve</p>
      </div>

      <button class="btn btn--fantasma" @click="voltar">Voltar</button>
    </header>

    <div v-if="erro" class="erro">{{ erro }}</div>

    <div class="card">
      <div v-if="carregando" class="muted">Carregando…</div>

      <form v-else @submit.prevent="salvar" class="form">
        <div class="field">
          <label>Título</label>
          <input v-model="titulo" required />
        </div>

        <div class="field">
          <label>Descrição</label>
          <textarea v-model="descricao" required rows="3" />
        </div>

        <div class="grid2">
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
        </div>

        <button class="btn" type="submit" :disabled="salvando">
          {{ salvando ? "Salvando..." : textoBotao }}
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.pagina {
  width: min(900px, 92vw);
  margin: 24px auto 56px;
}
.topo {
  display: flex;
  justify-content: space-between;
  align-items: end;
  gap: 16px;
  margin-bottom: 16px;
}
.titulo {
  margin: 0;
  font-size: 26px;
}
.subtitulo {
  margin: 6px 0 0;
  opacity: 0.75;
}

.card {
  background: #1a1a1a;
  border: 1px solid #334155;
  border-radius: 14px;
  padding: 16px;
}

.form {
  display: grid;
  gap: 12px;
}
.field {
  display: grid;
  gap: 6px;
}
.grid2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}
@media (max-width: 700px) {
  .grid2 {
    grid-template-columns: 1fr;
  }
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

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid transparent;
  cursor: pointer;
  background: #2563eb;
  color: white;
}
.btn--fantasma {
  background: #242424;
  border-color: #334155;
}

.erro {
  background: rgba(176, 0, 32, 0.15);
  border: 1px solid rgba(176, 0, 32, 0.5);
  padding: 12px;
  border-radius: 12px;
  margin-bottom: 14px;
}
.muted {
  opacity: 0.75;
}
</style>
