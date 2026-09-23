package com.alimentabem.model;

public class InstituicaoBeneficiaria {
    private final String nome;
    private final String cep;
    private Endereco endereco;
    private final int capacidadeMensal;

    public InstituicaoBeneficiaria(String nome, String cep, Endereco endereco, int capacidadeMensal) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da instituição beneficiária não pode ser nulo ou vazio.");
        }
        if (capacidadeMensal <= 0) {
            throw new IllegalArgumentException("A capacidade mensal da instituição beneficiária deve ser maior que zero.");
        }
        this.nome = nome;
        this.cep = cep;
        this.endereco = endereco;
        this.capacidadeMensal = capacidadeMensal;
    }

    public String getNome() { return nome; }
    public String getCep() { return cep; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public int getCapacidadeMensal() { return capacidadeMensal; }

    @Override
    public String toString() {
        return nome + " (capacidade: " + capacidadeMensal + " famílias por mês) - " + (endereco != null ? endereco : "endereço ainda não consultado, CEP " + cep);
    }
}