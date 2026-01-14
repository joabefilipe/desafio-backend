package com.example.dasafio_tecnico.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherRespostaDTO {
    private String cidade;
    private Double temperatura;
    private String condicao;
    private Integer umidade;
    private String icone;

    public WeatherRespostaDTO(WeatherDTO weatherOriginal) {
        this.cidade = weatherOriginal.getCidade();
        this.temperatura = weatherOriginal.getMain().getTemperatura();
        this.umidade = weatherOriginal.getMain().getUmidade();
        this.condicao = weatherOriginal.getDescricaoTempo();

        if (weatherOriginal.getWeather() != null && weatherOriginal.getWeather().length > 0) {
            this.icone = weatherOriginal.getWeather()[0].getIcone();
        }
    }
}
