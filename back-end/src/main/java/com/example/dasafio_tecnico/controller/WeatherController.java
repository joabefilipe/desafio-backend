package com.example.dasafio_tecnico.controller;

import com.example.dasafio_tecnico.DTO.WeatherDTO;
import com.example.dasafio_tecnico.DTO.WeatherRespostaDTO;
import com.example.dasafio_tecnico.security.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/hoje")
    public ResponseEntity<WeatherRespostaDTO> buscarWeather(
            @RequestParam String cidade) {

        WeatherDTO weather = weatherService.buscarTempoPorCidade(cidade);

        WeatherRespostaDTO resposta = new WeatherRespostaDTO(weather);

        return ResponseEntity.ok(resposta);
    }
}
