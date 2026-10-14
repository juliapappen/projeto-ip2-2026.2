package com.alimentabem.repository;

import com.alimentabem.model.Alimento;
import com.alimentabem.model.LoteEstoque;
import java.util.List;

public interface IRepositorioEstoque extends Repositorio<LoteEstoque> {
    void adicionarLote(LoteEstoque lote);
    LoteEstoque buscarPorId(String id);
    List<LoteEstoque> buscarPorAlimento(Alimento alimento);
    List<LoteEstoque> buscarPorAlimento(String nomeAlimento);
}