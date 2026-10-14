package com.alimentabem.service;

import com.alimentabem.model.Alimento;
import com.alimentabem.model.CategoriaAlimento;
import com.alimentabem.model.UnidadeMedida;
import java.util.List;

public interface AlimentoService {
    Alimento cadastrar(String nome, CategoriaAlimento categoria, UnidadeMedida unidadeMedida);
    Alimento buscarPorNome(String nome);
    List<Alimento> listarTodos();
    void atualizar(String nomeAntigo, String novoNome, CategoriaAlimento novaCategoria, UnidadeMedida novaUnidade);
    void remover(String nome);
}