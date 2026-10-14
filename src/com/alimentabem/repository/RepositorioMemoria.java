package com.alimentabem.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RepositorioMemoria<T> implements Repositorio<T> {
    protected final List<T> elementos = new ArrayList<>();

    @Override
    public void inserir(T entidade) {
        if (entidade != null) {
            elementos.add(entidade);
        }
    }

    @Override
    public List<T> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(elementos));
    }

    @Override
    public void atualizar(T entidade) {
        int index = elementos.indexOf(entidade);
        if (index != -1) {
            elementos.set(index, entidade);
        }
    }

    @Override
    public void remover(T entidade) {
        elementos.remove(entidade);
    }
}