package com.alimentabem.model;

import com.alimentabem.exception.ValidacaoException;
import java.time.LocalDate;
import java.util.UUID;

public class LoteEstoque {
    private final String id;
    private final Alimento alimento;
    private final LocalDate dataValidade;
    private double quantidadeDisponivel;

    public LoteEstoque(Alimento alimento, double quantidadeInicial, LocalDate dataValidade) {
        this.id = UUID.randomUUID().toString();
        this.alimento = alimento;
        this.quantidadeDisponivel = quantidadeInicial;
        this.dataValidade = dataValidade;
    }
    
    public String getId() { return id; }
    public Alimento getAlimento() { return alimento; }
    public LocalDate getDataValidade() { return dataValidade; }
    public double getQuantidadeDisponivel() { return quantidadeDisponivel; }
    
    public void retirar(double quantidade) {
        if (quantidade > quantidadeDisponivel) {
            throw new ValidacaoException(
                "Quantidade solicitada (" + quantidade + ") excede a quantidade disponível (" + quantidadeDisponivel + ") no lote de " + alimento.getNome() + " com validade em " + dataValidade + ".");
        }
        this.quantidadeDisponivel -= quantidade; 
    }

    @Override
    public String toString() {
        return String.format("Lote[%s] %.2f %s de %s (validade %s)", id.substring(0, 8), quantidadeDisponivel, alimento.getUnidadeMedida().getSigla(), alimento.getNome(), dataValidade);
    }
}