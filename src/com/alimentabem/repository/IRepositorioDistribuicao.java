package com.alimentabem.repository;

import com.alimentabem.model.Distribuicao;
import java.util.List;

public interface IRepositorioDistribuicao extends IRepositorio<Distribuicao> {
    List<Distribuicao> buscarPorInstituicao(String nomeInstituicao);
}