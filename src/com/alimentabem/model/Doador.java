package com.alimentabem.model;

public abstract class Doador {
    private final String nome;
    private final String telefone;
    private final String email;
    
    protected Doador(String nome, String telefone, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do doador não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    public abstract String getDocumento();
}