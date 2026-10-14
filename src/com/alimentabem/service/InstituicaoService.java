package com.alimentabem.service;

import com.alimentabem.model.InstituicaoBeneficiaria;
import java.util.List;

public interface InstituicaoService {
    InstituicaoBeneficiaria cadastrar(String nome, String cep, int capacidadeMensal);
    InstituicaoBeneficiaria buscarPorNome(String nome);
    List<InstituicaoBeneficiaria> listarTodas();
    void atualizar(String nomeAntigo, String novoNome, String novoCep, Integer novaCapacidade);
    void remover(String nome);
}