package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.InstituicaoBeneficiaria;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorInstituicoes {
    private final List<InstituicaoBeneficiaria> instituicoes = new ArrayList<>();
    private final DistribuicaoService distribuicaoService;

    public GerenciadorInstituicoes(DistribuicaoService distribuicaoService) {
        this.distribuicaoService = distribuicaoService;
    }

    public void cadastrar(InstituicaoBeneficiaria instituicao) { instituicoes.add(instituicao); }

    public void removerInstituicao(InstituicaoBeneficiaria instituicao) {
        boolean possuiHistorico = distribuicaoService.possuiDistribuicoesPara(instituicao);
        if (possuiHistorico) {
            throw new ValidacaoException(
                "Não é possível excluir '" + instituicao.getNome() + "', ela já possui distribuições registradas.");
        }
        instituicoes.remove(instituicao);
    }

    public List<InstituicaoBeneficiaria> listarTodas() { return Collections.unmodifiableList(instituicoes); }
}