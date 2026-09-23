package com.alimentabem.model;

public class Alimento {
    private final String nome;
    private final CategoriaAlimento categoria;
    private final UnidadeMedida unidadeMedida;
    private ValorNutricional valorNutricional;

    public Alimento(String nome, CategoriaAlimento categoria, UnidadeMedida unidadeMedida) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do alimento não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
    }

    public String getNome() { return nome; }
    public CategoriaAlimento getCategoria() { return categoria; }
    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public ValorNutricional getValorNutricional() { return valorNutricional; }
    public void setValorNutricional(ValorNutricional valorNutricional) { this.valorNutricional = valorNutricional; }

    @Override
    public String toString() {
        return nome + " (" + categoria + ", " + unidadeMedida.getSigla() + ")";
    }
}