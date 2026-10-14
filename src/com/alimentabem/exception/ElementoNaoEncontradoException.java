package com.alimentabem.exception;

public class ElementoNaoEncontradoException extends RuntimeException {
    public ElementoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}