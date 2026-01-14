package com.example.dasafio_tecnico.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonProperty("name")
    private String cidade;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Weather[] weather;

    @JsonProperty("wind")
    private Wind wind;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        @JsonProperty("temp")
        private Double temperatura;

        @JsonProperty("feels_like")
        private Double sensacaoTermica;

        @JsonProperty("humidity")
        private Integer umidade;

        @JsonProperty("pressure")
        private Integer pressao;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("main")
        private String condicao;

        @JsonProperty("description")
        private String descricao;

        @JsonProperty("icon")
        private String icone;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        @JsonProperty("speed")
        private Double velocidade;
    }

    public String getDescricaoTempo() {
        if (weather != null && weather.length > 0) {
            return weather[0].getDescricao();
        }
        return "N/A";
    }

    public String getCondicaoTempo() {
        if (weather != null && weather.length > 0) {
            return weather[0].getCondicao();
        }
        return "N/A";
    }
}