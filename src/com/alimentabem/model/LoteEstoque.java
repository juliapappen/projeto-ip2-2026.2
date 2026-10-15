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
        if (alimento == null) {
            throw new IllegalArgumentException("O lote precisa de um alimento.");
        }
        if (quantidadeInicial <= 0) {
            throw new IllegalArgumentException("A quantidade inicial do lote deve ser maior que zero.");
        }
        if (dataValidade == null) {
            throw new IllegalArgumentException("A data de validade é obrigatória.");
        }
        this.id = UUID.randomUUID().toString();
        this.alimento = alimento;
        this.quantidadeDisponivel = quantidadeInicial;
        this.dataValidade = dataValidade;
    }

    public String getId() { return id; }
    public Alimento getAlimento() { return alimento; }
    public LocalDate getDataValidade() { return dataValidade; }
    public double getQuantidadeDisponivel() { return quantidadeDisponivel; }

    public boolean estaVencido() {
        return dataValidade.isBefore(LocalDate.now());
    }

    public void adicionar(double quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a adicionar deve ser maior que zero.");
        }
        this.quantidadeDisponivel += quantidade;
    }

    public void retirar(double quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a retirar deve ser maior que zero.");
        }

        // REQ13: impede rigorosamente a saída/distribuição de qualquer lote vencido
        if (estaVencido()) {
            throw new ValidacaoException(
                "O lote de " + alimento.getNome() + " venceu em " + dataValidade + " e não pode ser distribuído (REQ13).");
        }

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