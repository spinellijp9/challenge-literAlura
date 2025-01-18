package com.jp.literAlura.service;

public interface IConverterDados {
    <T> T  obterDados(String json, Class<T> classe);
}