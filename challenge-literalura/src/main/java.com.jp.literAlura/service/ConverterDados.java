package com.jp.literAlura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON", e);
        }
    }
}