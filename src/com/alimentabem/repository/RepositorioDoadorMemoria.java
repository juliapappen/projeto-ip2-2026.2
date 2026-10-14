package com.alimentabem.repository;

import com.alimentabem.model.Doador;

public class RepositorioDoadorMemoria extends RepositorioMemoria<Doador> implements IRepositorioDoador {

    @Override
    public Doador buscarPorDocumento(String documento) {
        if (documento == null) return null;
        return elementos.stream()
                .filter(d -> d.getDocumento().equalsIgnoreCase(documento) || d.getDocumento().contains(documento))
                .findFirst()
                .orElse(null);
    }
}