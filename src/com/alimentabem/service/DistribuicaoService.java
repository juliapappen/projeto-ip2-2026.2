package com.alimentabem.service;

import com.alimentabem.model.Alimento;
import com.alimentabem.model.Distribuicao;
import com.alimentabem.model.InstituicaoBeneficiaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DistribuicaoService {
    Distribuicao registrarDistribuicao(InstituicaoBeneficiaria instituicao, 
                                        Map<Alimento, Double> itensSolicitados, 
                                        int familiasAtendidas, 
                                        LocalDate data);
    List<Distribuicao> listarTodas();
    List<Distribuicao> buscarPorInstituicao(String nomeInstituicao);
}