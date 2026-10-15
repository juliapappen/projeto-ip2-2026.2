package com.alimentabem.repository;

import com.alimentabem.model.Doador;
import java.util.List;

public interface IRepositorioDoador {
    void inserir(Doador doador);
    Doador buscarPorDocumento(String documento);
    List<Doador> listarTodos();
    void atualizar(Doador doador);
    void remover(Doador doador);
}