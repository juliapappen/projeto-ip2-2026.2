package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.*;
import com.alimentabem.repository.IRepositorioDistribuicao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DistribuicaoServiceImpl implements DistribuicaoService {
    private final IRepositorioDistribuicao repoDistribuicao;
    private final EstoqueService estoqueService;

    public DistribuicaoServiceImpl(IRepositorioDistribuicao repoDistribuicao, EstoqueService estoqueService) {
        this.repoDistribuicao = repoDistribuicao;
        this.estoqueService = estoqueService;
    }

    @Override
    public Distribuicao registrarDistribuicao(InstituicaoBeneficiaria instituicao, 
                                                Map<Alimento, Double> itensSolicitados, 
                                                int familiasAtendidas, 
                                                LocalDate data) {
        if (instituicao == null) {
            throw new ValidacaoException("A instituição é obrigatória.");
        }
        if (itensSolicitados == null || itensSolicitados.isEmpty()) {
            throw new ValidacaoException("Selecione ao menos um item de alimento para distribuir.");
        }

        int familiasAtendidasNoMes = calcularFamiliasAtendidasNoMes(instituicao, data);
        if (familiasAtendidasNoMes + familiasAtendidas > instituicao.getCapacidadeMensal()) {
            throw new ValidacaoException("Distribuição bloqueada: a instituição '" + instituicao.getNome() + 
                "' tem capacidade para " + instituicao.getCapacidadeMensal() + " famílias/mês. Já atendeu " + 
                familiasAtendidasNoMes + " este mês e tentou adicionar mais " + familiasAtendidas + ".");
        }

        Distribuicao distribuicao = new Distribuicao(instituicao, data, familiasAtendidas);

        for (Map.Entry<Alimento, Double> entry : itensSolicitados.entrySet()) {
            Alimento alimento = entry.getKey();
            double quantidadeNecessaria = entry.getValue();

            double saldoDisponivel = estoqueService.saldoDisponivel(alimento);
            if (quantidadeNecessaria > saldoDisponivel) {
                throw new ValidacaoException("Estoque insuficiente de '" + alimento.getNome() + 
                    "'. Solicitado: " + quantidadeNecessaria + " " + alimento.getUnidadeMedida().getSigla() + 
                    ", Disponível: " + saldoDisponivel);
            }

            List<LoteEstoque> lotesFIFO = estoqueService.listarLotesDisponiveisFIFO(alimento);

            double restanteParaRetirar = quantidadeNecessaria;
            for (LoteEstoque lote : lotesFIFO) {
                if (restanteParaRetirar <= 0) break;

                if (lote.estaVencido()) continue;

                double qtdDoLote = Math.min(lote.getQuantidadeDisponivel(), restanteParaRetirar);
                
                estoqueService.retirar(lote.getId(), qtdDoLote);

                distribuicao.adicionarItem(new ItemDistribuicao(lote, qtdDoLote));
                restanteParaRetirar -= qtdDoLote;
            }
        }

        distribuicao.validarQueTemItens();
        repoDistribuicao.inserir(distribuicao);
        return distribuicao;
    }

    private int calcularFamiliasAtendidasNoMes(InstituicaoBeneficiaria inst, LocalDate data) {
        return repoDistribuicao.buscarPorInstituicao(inst.getNome()).stream()
                .filter(d -> d.getDataDistribuicao().getMonth() == data.getMonth() && 
                             d.getDataDistribuicao().getYear() == data.getYear())
                .mapToInt(Distribuicao::getFamiliasAtendidas)
                .sum();
    }

    @Override
    public List<Distribuicao> listarTodas() {
        return repoDistribuicao.listarTodos();
    }

    @Override
    public List<Distribuicao> buscarPorInstituicao(String nomeInstituicao) {
        return repoDistribuicao.buscarPorInstituicao(nomeInstituicao);
    }
}