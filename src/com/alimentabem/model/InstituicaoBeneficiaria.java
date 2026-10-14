package com.alimentabem.model;

import java.util.UUID;

public class InstituicaoBeneficiaria {
    private String id;
    private String nome;
    private final String cep;
    private Endereco endereco;
    private int capacidadeMensal;

    public InstituicaoBeneficiaria(String nome, String cep, Endereco endereco, int capacidadeMensal) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da instituição beneficiária não pode ser nulo ou vazio.");
        }
        if (capacidadeMensal <= 0) {
            throw new IllegalArgumentException("A capacidade mensal deve ser maior que zero.");
        }
        this.id = UUID.randomUUID().toString().substring(0, 8); // Gera ID único automático
        this.nome = nome.trim();
        this.cep = cep != null ? cep.replaceAll("\\D", "") : "";
        this.endereco = endereco;
        this.capacidadeMensal = capacidadeMensal;
    }

    public InstituicaoBeneficiaria(String nome, String cep, int capacidadeMensal) {
        this(nome, cep, null, capacidadeMensal);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCep() { return cep; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public int getCapacidadeMensal() { return capacidadeMensal; }
    public void setCapacidadeMensal(int capacidadeMensal) { this.capacidadeMensal = capacidadeMensal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstituicaoBeneficiaria)) return false;
        return nome.equalsIgnoreCase(((InstituicaoBeneficiaria) o).nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " (Capacidade: " + capacidadeMensal + " famílias/mês) - CEP: " + cep + 
               (endereco != null ? " - " + endereco.getCidade() + "/" + endereco.getEstado() : "");
    }
}