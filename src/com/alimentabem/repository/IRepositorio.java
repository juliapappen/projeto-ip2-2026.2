package com.alimentabem.repository;

import java.util.List;

public interface IRepositorio<T> {
    void inserir(T elemento);
    T buscarPorId(String id);
    List<T> listarTodos();
    void remover(String id);
}