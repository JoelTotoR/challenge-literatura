package com.aluracursos.literatura.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class <T> clase);
}
