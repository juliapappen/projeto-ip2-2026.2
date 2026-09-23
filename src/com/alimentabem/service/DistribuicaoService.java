package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DistribuicaoService {
    private final EstoqueService estoqueService;
    private final List<Distribuicao> historico = new ArrayList<>();
    private final Map<InstituicaoBeneficiaria, Map<YearMonth, Integer>> familiasAtendidasPorMes = new HashMap<>();

    public DistribuicaoService(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    public Distribuicao registrarDistribuicao(InstituicaoBeneficiaria instituicao,
                                               Map<Alimento, Double> pedido,
                                               int familiasAtendidas) {
        validarCapacidadeMensal(instituicao, familiasAtendidas);

        Distribuicao distribuicao = new Distribuicao(instituicao, LocalDate.now(), familiasAtendidas);

        Map<Alimento, List<EstoqueService.SugestaoRetirada>> planoDeRetirada = new LinkedHashMap<>();
        for (Map.Entry<Alimento, Double> pedidoItem : pedido.entrySet()) {
            List<EstoqueService.SugestaoRetirada> sugestoes =
                    estoqueService.sugerirLotesParaDistribuicao(pedidoItem.getKey(), pedidoItem.getValue());
            planoDeRetirada.put(pedidoItem.getKey(), sugestoes);
        }

        for (List<EstoqueService.SugestaoRetirada> sugestoes : planoDeRetirada.values()) {
            for (EstoqueService.SugestaoRetirada sugestao : sugestoes) {
                sugestao.getLote().retirar(sugestao.getQuantidade());
                distribuicao.adicionarItem(new ItemDistribuicao(sugestao.getLote(), sugestao.getQuantidade()));
            }
        }

        historico.add(distribuicao);
        registrarFamiliasAtendidas(instituicao, familiasAtendidas);
        return distribuicao;
    }

    private void validarCapacidadeMensal(InstituicaoBeneficiaria instituicao, int familiasNestaEntrega) {
        YearMonth mesAtual = YearMonth.now();
        int jaAtendidas = familiasAtendidasPorMes
            .getOrDefault(instituicao, Map.of())
            .getOrDefault(mesAtual, 0);
    
        int totalAposEssaEntrega = jaAtendidas + familiasNestaEntrega;
        if (totalAposEssaEntrega > instituicao.getCapacidadeMensal()) {
            throw new ValidacaoException(
                "'" + instituicao.getNome() + "' já atingiu (ou vai ultrapassar) sua capacidade mensal de " + instituicao.getCapacidadeMensal() + " famílias. Já atendidas neste mês: " + jaAtendidas + " (REQ17).");
        }
    }

    private void registrarFamiliasAtendidas(InstituicaoBeneficiaria instituicao, int quantidade) {
        YearMonth mesAtual = YearMonth.now();
        Map<YearMonth, Integer> mapaDaInstituicao = 
                familiasAtendidasPorMes.computeIfAbsent(instituicao, k -> new HashMap<>());
        int atual = mapaDaInstituicao.getOrDefault(mesAtual, 0);
        mapaDaInstituicao.put(mesAtual, atual + quantidade);
    }

    public boolean possuiDistribuicoesPara(InstituicaoBeneficiaria instituicao) {
        return historico.stream().anyMatch(d -> d.getInstituicao() == instituicao);
    }

    public List<Distribuicao> getHistorico() { return List.copyOf(historico); }
}