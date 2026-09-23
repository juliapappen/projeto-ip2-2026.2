package com.alimentabem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Distribuicao {
    private final InstituicaoBeneficiaria instituicao;
    private final LocalDate dataDistribuicao;
    private final List<ItemDistribuicao> itens = new ArrayList<>();
    private final int familiasAtendidas;
    
    public Distribuicao(InstituicaoBeneficiaria instituicao, LocalDate dataDistribuicao, int familiasAtendidas) {
        this.instituicao = instituicao;
        this.dataDistribuicao = dataDistribuicao;
        this.familiasAtendidas = familiasAtendidas;
    }
    
    public void adicionarItem(ItemDistribuicao item) { itens.add(item); }
    public InstituicaoBeneficiaria getInstituicao() { return instituicao; }
    public LocalDate getDataDistribuicao() { return dataDistribuicao; }
    public List<ItemDistribuicao> getItens() { return Collections.unmodifiableList(itens); }
    public int getFamiliasAtendidas() { return familiasAtendidas; }

    @Override
    public String toString() {
        return "Distribuição para " + instituicao.getNome() + " em " + dataDistribuicao + " (" + familiasAtendidas + " famílias, " + itens.size() + " item(ns))";
    }
}