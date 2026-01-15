import http from "./http";

export type StatusTarefa = "ATIVO" | "DESATIVADO" | string;

export type Tarefa = {
  id?: number;
  titulo: string;
  descricao: string;
  data: string;
  status: StatusTarefa;
};

export type TarefaPayload = {
  titulo: string;
  descricao: string;
  data: string;
  status: StatusTarefa;
};


export type Pagina<T> = {
  content: T[];
  number: number;
  size: number;
  totalPages: number;
  totalElements: number;
  last: boolean;
};

export async function listarTarefas(pagina = 0, tamanho = 9) {
  const res = await http.get("/tarefa", { params: { page: pagina, size: tamanho } });
  return res.data as Pagina<Tarefa> | Tarefa[];
}

export async function buscarTarefaPorId(id: number) {
  const res = await http.get(`/tarefa/${id}`);
  return res.data as Tarefa;
}


export async function deletarTarefa(id: number) {
  return http.delete(`/tarefa/${id}`);
}


export async function criarTarefa(payload: TarefaPayload) {
  return http.post("/tarefa", payload);
}

export async function atualizarTarefa(id: number, payload: TarefaPayload) {
  return http.put(`/tarefa/${id}`, payload);
}