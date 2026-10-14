package com.alimentabem.repository;

import com.alimentabem.model.InstituicaoBeneficiaria;

public interface IRepositorioInstituicao extends IRepositorio<InstituicaoBeneficiaria> {
    InstituicaoBeneficiaria buscarPorNome(String nome);
    void atualizar(InstituicaoBeneficiaria instituicao);
}