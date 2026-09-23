package com.alimentabem.model;

public class ItemDistribuicao {
    private final LoteEstoque lote;
    private final double quantidadeRetirada;

    public ItemDistribuicao(LoteEstoque lote, double quantidadeRetirada) {
        this.lote = lote;
        this.quantidadeRetirada = quantidadeRetirada;
    }
    
    public LoteEstoque getLote() { return lote; }
    public double getQuantidadeRetirada() { return quantidadeRetirada; }

    @Override
    public String toString() {
        return quantidadeRetirada + " " + lote.getAlimento().getUnidadeMedida().getSigla() + " de " + lote.getAlimento().getNome() + " (do lote com validade " + lote.getDataValidade() + ")";
    }
}