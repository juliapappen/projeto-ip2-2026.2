package com.alimentabem.model;

public class Empresa extends Doador {
    private final String cnpj;
    private final String razaoSocial;

    public Empresa(String nome, String telefone, String email, String cnpj, String razaoSocial) {
        super(nome, telefone, email);
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("O CNPJ da empresa não pode ser nulo ou vazio.");
        }
        if (razaoSocial == null || razaoSocial.isBlank()) {
            throw new IllegalArgumentException("A razão social da empresa não pode ser nula ou vazia.");
        }
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() { return cnpj; }
    public String getRazaoSocial() { return razaoSocial; }

    @Override
    public String getDocumento() {
        return "CNPJ: " + cnpj; 
    }

    @Override
    public String toString() {
        return getNome() + " - " + razaoSocial + " (" + getDocumento() + ")"; 
    }
}