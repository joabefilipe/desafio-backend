<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import {
  listarTarefas,
  deletarTarefa,
  type Pagina,
  type Tarefa,
} from "../api/tarefa";
import { useRouter } from "vue-router";
import { Pencil, Trash2, Plus, Search } from "lucide-vue-next";
import ConfirmarExclusaoModal from "../components/ConfirmarExclusaoModal.vue";
import FiltroTarefasModal from "../components/FiltroTarefasModal.vue";
import { useTarefasFiltroStore } from "../stores/tarefasFiltro";
import WeatherModal from "../components/WeatherModal.vue";

const weatherAberto = ref(false);
const router = useRouter();

const tarefas = ref<Tarefa[]>([]);
const carregando = ref(false);
const erro = ref<string | null>(null);

const pagina = ref(0);
const tamanho = ref(9);
const temMais = ref(true);
const sentinela = ref<HTMLElement | null>(null);
const filtroStore = useTarefasFiltroStore();
const modalFiltroAberto = ref(false);

const titulo = computed(() => `Tarefas (${tarefas.value.length})`);

const modalExcluirAberto = ref(false);
const tarefaSelecionada = ref<Tarefa | null>(null);
const excluindo = ref(false);
const erroExcluir = ref<string | null>(null);

function formatarData(iso: string) {
  try {
    return new Date(iso).toLocaleString("pt-BR");
  } catch {
    return iso;
  }
}

function classeStatus(status: string) {
  const s = (status || "").toUpperCase();
  if (s.includes("DESAT")) return "badge badge--cinza";
  if (s.includes("ATIV")) return "badge badge--verde";
  return "badge badge--azul";
}

function normalizarResposta(data: Pagina<Tarefa>) {
  return { itens: data.content ?? [], ultima: data.last ?? true };
}

async function aplicarFiltro() {
  tarefas.value = [];
  pagina.value = 0;
  temMais.value = true;

  await carregarMais();
}

async function carregarMais() {
  if (carregando.value || !temMais.value) return;

  carregando.value = true;
  erro.value = null;

  try {
    const data = await listarTarefas(pagina.value, tamanho.value, {
      titulo: filtroStore.titulo,
      status: filtroStore.status,
    });

    const { itens, ultima } = normalizarResposta(data as Pagina<Tarefa>);

    if (!itens.length) {
      temMais.value = false;
      return;
    }

    tarefas.value.push(...itens);
    pagina.value += 1;
    if (ultima) temMais.value = false;
  } catch {
    erro.value =
      "Não foi possível carregar as tarefas. Verifique login/token e API.";
  } finally {
    carregando.value = false;
  }
}

function abrirExcluir(t: Tarefa) {
  tarefaSelecionada.value = t;
  erroExcluir.value = null;
  modalExcluirAberto.value = true;
}

function fecharExcluir() {
  modalExcluirAberto.value = false;
  tarefaSelecionada.value = null;
  erroExcluir.value = null;
}

async function confirmarExcluir() {
  const id = tarefaSelecionada.value?.id;
  if (!id) {
    erroExcluir.value = "Não foi possível excluir: tarefa sem ID.";
    return;
  }

  excluindo.value = true;
  erroExcluir.value = null;

  try {
    await deletarTarefa(id);
    tarefas.value = tarefas.value.filter((x) => x.id !== id);
    fecharExcluir();
  } catch {
    erroExcluir.value =
      "Não foi possível excluir. Verifique permissão (ADMIN) e API.";
  } finally {
    excluindo.value = false;
  }
}

function irParaCriar() {
  router.push("/tarefa/nova");
}

function irParaEditar(t: Tarefa) {
  if (!t.id) return;
  router.push(`/tarefa/${t.id}`);
}

function sair() {
  localStorage.removeItem("token");
  router.push("/login");
}

onMounted(async () => {
  await carregarMais();

  const obs = new IntersectionObserver(
    (entries) => {
      if (entries[0]?.isIntersecting) carregarMais();
    },
    { root: null, rootMargin: "250px", threshold: 0 }
  );

  if (sentinela.value) obs.observe(sentinela.value);
});
</script>

<template>
  <div class="pagina">
    <header class="topo">
      <div>
        <h1 class="titulo">{{ titulo }}</h1>
      </div>

      <div class="acoes">
        <button
          class="btn btn--fantasma"
          @click="carregarMais"
          :disabled="carregando || !temMais"
        >
          Carregar mais
        </button>
        <button
          class="btn btn--fantasma icon-only"
          title="Filtrar"
          @click="modalFiltroAberto = true"
        >
          <Search :size="18" />
        </button>
        <button class="btn btn--fantasma" @click="weatherAberto = true">
          Clima
        </button>

        <WeatherModal :open="weatherAberto" @close="weatherAberto = false" />

        <button class="btn" @click="irParaCriar">
          <Plus :size="18" /> Cadastrar
        </button>

        <button class="btn btn--danger" @click="sair">Sair</button>
      </div>
    </header>

    <div v-if="erro" class="erro">{{ erro }}</div>

    <section class="grid">
      <article v-for="(t, i) in tarefas" :key="t.id ?? i" class="card">
        <div class="card__cabecalho">
          <h3 class="card__titulo">{{ t.titulo }}</h3>

          <div class="acoes-card">
            <span :class="classeStatus(t.status)">{{ t.status }}</span>

            <button class="icon-btn" title="Editar" @click="irParaEditar(t)">
              <Pencil :size="18" />
            </button>

            <button
              class="icon-btn icon-btn--perigo"
              title="Excluir"
              @click="abrirExcluir(t)"
            >
              <Trash2 :size="18" />
            </button>
          </div>
        </div>

        <p class="card__descricao">{{ t.descricao }}</p>

        <div class="card__rodape">
          <span class="meta">📅 {{ formatarData(t.data) }}</span>
          <span class="meta">🧾 ID: {{ t.id ?? "-" }}</span>
        </div>
      </article>
    </section>

    <footer class="rodape">
      <p v-if="carregando" class="muted">Carregando tarefas…</p>
      <p v-else-if="!temMais" class="muted">Fim da lista ✅</p>
      <div ref="sentinela" style="height: 1px"></div>
    </footer>
    <FiltroTarefasModal
      :open="modalFiltroAberto"
      @close="modalFiltroAberto = false"
      @apply="aplicarFiltro"
    />

    <ConfirmarExclusaoModal
      :open="modalExcluirAberto"
      :titulo="tarefaSelecionada?.titulo"
      :loading="excluindo"
      :erro="erroExcluir"
      @close="fecharExcluir"
      @confirm="confirmarExcluir"
    />
  </div>
</template>

<style scoped>
.pagina {
  width: min(1100px, 92vw);
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

.acoes {
  display: flex;
  gap: 10px;
  align-items: center;
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
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
.btn--danger {
  background: #b00020;
}

.erro {
  background: rgba(176, 0, 32, 0.15);
  border: 1px solid rgba(176, 0, 32, 0.5);
  padding: 12px;
  border-radius: 12px;
  margin-bottom: 14px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
}
@media (max-width: 900px) {
  .grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
@media (max-width: 620px) {
  .grid {
    grid-template-columns: 1fr;
  }
}

.card {
  background: #1a1a1a;
  border: 1px solid #334155;
  border-radius: 14px;
  padding: 14px;
  transition: transform 120ms ease, border-color 120ms ease;
}
.card:hover {
  transform: translateY(-2px);
  border-color: #4b5563;
}

.card__cabecalho {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  margin-bottom: 10px;
}
.card__titulo {
  margin: 0;
  font-size: 16px;
  line-height: 1.2;
}
.card__descricao {
  margin: 0 0 12px;
  opacity: 0.85;
  font-size: 14px;
}

.card__rodape {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  opacity: 0.75;
  font-size: 12px;
}

.acoes-card {
  display: flex;
  align-items: center;
  gap: 8px;
}
.icon-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  margin: 0;
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  line-height: 0;
}
.icon-btn--perigo {
  color: #ff6b6b;
}

.badge {
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 999px;
  border: 1px solid #334155;
  white-space: nowrap;
}
.badge--verde {
  background: rgba(19, 115, 51, 0.25);
  border-color: rgba(19, 115, 51, 0.7);
}
.badge--cinza {
  background: rgba(148, 163, 184, 0.18);
  border-color: rgba(148, 163, 184, 0.5);
}
.badge--azul {
  background: rgba(37, 99, 235, 0.18);
  border-color: rgba(37, 99, 235, 0.55);
}

.rodape {
  margin-top: 16px;
  text-align: center;
}
.muted {
  opacity: 0.75;
}
</style>
