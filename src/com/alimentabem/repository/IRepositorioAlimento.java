package com.alimentabem.repository;

import com.alimentabem.model.Alimento;

public interface IRepositorioAlimento extends IRepositorio<Alimento> {
    Alimento buscarPorNome(String nome);
    void atualizar(Alimento alimento);
}