package com.alimentabem.repository;

import com.alimentabem.model.Doador;

public interface IRepositorioDoador extends Repositorio<Doador> {
    Doador buscarPorDocumento(String documento);
}