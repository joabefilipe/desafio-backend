import http from "./http";

export type WeatherResposta = {
  cidade: string;
  temperatura: number;
  condicao: string;
  umidade: number;
  icone: string;
};

export async function buscarWeatherHoje(cidade: string) {
  const res = await http.get("/weather/hoje", { params: { cidade } });
  return res.data as WeatherResposta;
}
