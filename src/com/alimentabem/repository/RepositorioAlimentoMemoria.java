package com.alimentabem.repository;

import com.alimentabem.model.Alimento;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAlimentoMemoria implements IRepositorioAlimento {
    private final List<Alimento> alimentos = new ArrayList<>();

    @Override
    public void inserir(Alimento alimento) {
        if (alimento != null) {
            alimentos.add(alimento);
        }
    }

    @Override
    public Alimento buscarPorId(String id) {
        if (id == null) return null;
        for (Alimento a : alimentos) {
            if (id.equalsIgnoreCase(a.getId())) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Alimento buscarPorNome(String nome) {
        if (nome == null) return null;
        for (Alimento a : alimentos) {
            if (a.getNome() != null && a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Alimento> listarTodos() {
        return new ArrayList<>(alimentos);
    }

    @Override
    public void atualizar(Alimento alimento) {
        if (alimento == null) return;
        for (int i = 0; i < alimentos.size(); i++) {
            if (alimentos.get(i).getNome().equalsIgnoreCase(alimento.getNome())) {
                alimentos.set(i, alimento);
                return;
            }
        }
    }

    @Override
    public void remover(String id) {
        if (id == null) return;
        alimentos.removeIf(a -> 
            (a.getId() != null && a.getId().equalsIgnoreCase(id)) ||
            (a.getNome() != null && a.getNome().equalsIgnoreCase(id))
        );
    }
}