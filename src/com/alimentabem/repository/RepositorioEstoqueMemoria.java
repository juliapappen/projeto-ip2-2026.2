package com.alimentabem.repository;

import com.alimentabem.model.Alimento;
import com.alimentabem.model.LoteEstoque;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioEstoqueMemoria extends RepositorioMemoria<LoteEstoque> implements IRepositorioEstoque {

    @Override
    public void adicionarLote(LoteEstoque lote) {
        inserir(lote);
    }

    @Override
    public LoteEstoque buscarPorId(String id) {
        if (id == null) return null;
        return elementos.stream()
                .filter(l -> l.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<LoteEstoque> buscarPorAlimento(Alimento alimento) {
        if (alimento == null) return List.of();
        return buscarPorAlimento(alimento.getNome());
    }

    @Override
    public List<LoteEstoque> buscarPorAlimento(String nomeAlimento) {
        if (nomeAlimento == null) return List.of();
        return elementos.stream()
                .filter(l -> l.getAlimento().getNome().equalsIgnoreCase(nomeAlimento.trim()))
                .collect(Collectors.toList());
    }
}