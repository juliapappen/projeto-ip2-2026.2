package com.alimentabem.repository;

import com.alimentabem.model.Alimento;
import com.alimentabem.model.LoteEstoque;
import java.util.List;

public interface IRepositorioEstoque {
    void adicionarLote(LoteEstoque lote);
    LoteEstoque buscarPorId(String id);
    List<LoteEstoque> buscarPorAlimento(Alimento alimento);
    List<LoteEstoque> listarTodos();
    void removerLote(String id);
}