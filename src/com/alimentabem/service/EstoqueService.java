package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.Alimento;
import com.alimentabem.model.LoteEstoque;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EstoqueService {
    private final List<LoteEstoque> lotes = new ArrayList<>();
    
    public void adicionarLote(LoteEstoque lote) { lotes.add(lote); }

    public List<LoteEstoque> listarLotesOrdenadosPorValidade() {
        List<LoteEstoque> copia = new ArrayList<>(lotes);
        copia.sort(Comparator.comparing(LoteEstoque::getDataValidade));
        return Collections.unmodifiableList(copia);
    }

    public List<LoteEstoque> listarItensVencendoEm7Dias() {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(7);
        List<LoteEstoque> resultado = new ArrayList<>();
        for (LoteEstoque lote : lotes) {
            boolean dentroDoPrazo = !lote.getDataValidade().isBefore(hoje) && !lote.getDataValidade().isAfter(limite);
            if (dentroDoPrazo && lote.getQuantidadeDisponivel() > 0) {
                resultado.add(lote);
            }
        }
        resultado.sort(Comparator.comparing(LoteEstoque::getDataValidade));
        return resultado;
    }

    public List<SugestaoRetirada> sugerirLotesParaDistribuicao(Alimento alimento, double quantidadeNecessaria) {
        List<SugestaoRetirada> sugestoes = new ArrayList<>();
        double restante = quantidadeNecessaria;

        List<LoteEstoque> candidatos = new ArrayList<>();
        for (LoteEstoque lote : lotes) {
            if (lote.getAlimento().equals(alimento) && lote.getQuantidadeDisponivel() > 0) {
                candidatos.add(lote);
            }
        }
        candidatos.sort(Comparator.comparing(LoteEstoque::getDataValidade));
        
        for (LoteEstoque lote : candidatos) {
            if (restante <= 0) break;
            double retirarDesteLote = Math.min(restante, lote.getQuantidadeDisponivel());
            sugestoes.add(new SugestaoRetirada(lote, retirarDesteLote));
            restante -= retirarDesteLote;
        }
        if (restante > 0) { 
            throw new ValidacaoException("Estoque insuficiente de '" + alimento.getNome() + "'. Faltam " + restante + " " + alimento.getUnidadeMedida().getSigla() + " (REQ16).");
        }
        return sugestoes;
    }

        public static class SugestaoRetirada {
        private final LoteEstoque lote;
        private final double quantidade;

        public SugestaoRetirada(LoteEstoque lote, double quantidade) {
            this.lote = lote;
            this.quantidade = quantidade;
        }

        public LoteEstoque getLote() { return lote; }
        public double getQuantidade() { return quantidade; }
    }
}