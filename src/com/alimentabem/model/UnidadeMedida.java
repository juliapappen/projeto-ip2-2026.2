package com.alimentabem.model;

public enum UnidadeMedida {
    QUILOGRAMA("kg"),
    LITRO("l"),
    UNIDADE("un");

    private final String sigla;

    UnidadeMedida(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }
}