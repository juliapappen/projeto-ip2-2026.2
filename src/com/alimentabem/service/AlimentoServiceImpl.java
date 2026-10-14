package com.alimentabem.service;

import com.alimentabem.exception.ElementoJaCadastradoException;
import com.alimentabem.exception.ElementoNaoEncontradoException;
import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.Alimento;
import com.alimentabem.model.CategoriaAlimento;
import com.alimentabem.model.UnidadeMedida;
import com.alimentabem.repository.IRepositorioAlimento;

import java.util.List;

public class AlimentoServiceImpl implements AlimentoService {
    private final IRepositorioAlimento repositorio;

    public AlimentoServiceImpl(IRepositorioAlimento repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("O repositório de alimentos não pode ser nulo.");
        }
        this.repositorio = repositorio;
    }

    @Override
    public Alimento cadastrar(String nome, CategoriaAlimento categoria, UnidadeMedida unidadeMedida) {
        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("O nome do alimento não pode ser nulo ou vazio.");
        }
        if (repositorio.buscarPorNome(nome) != null) {
            throw new ElementoJaCadastradoException("Já existe um alimento cadastrado com o nome: " + nome);
        }
        Alimento alimento = new Alimento(nome, categoria, unidadeMedida);
        repositorio.inserir(alimento);
        return alimento;
    }

    @Override
    public Alimento buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("Informe um nome válido para buscar o alimento.");
        }
        Alimento alimento = repositorio.buscarPorNome(nome);
        if (alimento == null) {
            throw new ElementoNaoEncontradoException("Alimento não encontrado: " + nome);
        }
        return alimento;
    }

    @Override
    public List<Alimento> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void atualizar(String nomeAntigo, String novoNome, CategoriaAlimento novaCategoria, UnidadeMedida novaUnidade) {
        Alimento existente = buscarPorNome(nomeAntigo);
        if (novoNome != null && !novoNome.isBlank()) {
            existente.setNome(novoNome);
        }
        if (novaCategoria != null) {
            existente.setCategoria(novaCategoria);
        }
        if (novaUnidade != null) {
            existente.setUnidadeMedida(novaUnidade);
        }
        repositorio.atualizar(existente);
    }

    @Override
    public void remover(String nome) {
        Alimento alimento = buscarPorNome(nome);
        repositorio.remover(alimento.getId());
    }
}