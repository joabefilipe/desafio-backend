package com.example.dasafio_tecnico.security;

import com.example.dasafio_tecnico.DTO.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.units}")
    private String units;

    @Value("${weather.api.lang}")
    private String lang;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherDTO buscarTempoPorCidade(String cidade) {

        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("q", cidade)
                .queryParam("appid", apiKey)
                .queryParam("units", units)
                .queryParam("lang", lang)
                .toUriString();

        return restTemplate.getForObject(url, WeatherDTO.class);
    }
}
