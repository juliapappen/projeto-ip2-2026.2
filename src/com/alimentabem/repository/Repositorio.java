package com.alimentabem.repository;

import java.util.List;

public interface Repositorio<T> {
    void inserir(T entidade);
    List<T> listarTodos();
    void atualizar(T entidade);
    void remover(T entidade);
}