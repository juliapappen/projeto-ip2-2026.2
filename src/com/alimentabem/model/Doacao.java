package com.alimentabem.model;

import com.alimentabem.exception.ValidacaoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Doacao {
    private final Doador doador;
    private final LocalDate dataDoacao;
    private final List<ItemDoacao> itens = new ArrayList<>();
    
    public Doacao(Doador doador) {
        this(doador, LocalDate.now());
    }

    public Doacao(Doador doador, LocalDate dataDoacao) {
        if (doador == null) {
            throw new IllegalArgumentException("Toda doação precisa de um doador.");
        }
        if (dataDoacao == null) {
            throw new IllegalArgumentException("A data da doação é obrigatória.");
        }
        this.doador = doador;
        this.dataDoacao = dataDoacao;
    }

    public void adicionarItem(ItemDoacao item) {
        if (item.getDataValidade().isBefore(dataDoacao)) {
            throw new ValidacaoException(
                "Não é possível cadastrar o item '" + item.getAlimento().getNome() + "' : a validade (" + item.getDataValidade() + ") já está vencida na data da doação (" + dataDoacao + ").");
        }
        itens.add(item);
    }

    public void validarQueTemItens() {
        if (itens.isEmpty()) {
            throw new ValidacaoException("Não é possível cadastrar uma doação sem itens.");
        }
    }

    public Doador getDoador() { return doador; }
    public LocalDate getDataDoacao() { return dataDoacao; }
    public List<ItemDoacao> getItens() { return Collections.unmodifiableList(itens); }

    @Override
    public String toString() {
        return "Doação de " + doador.getNome() + " em " + dataDoacao + " com " + itens.size() + " item(ns).";
    }
}