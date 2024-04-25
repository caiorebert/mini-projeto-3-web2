package com.jeanlima.springrestapiapp.exception;

public class EstoqueEsgotadoException extends RuntimeException {
    public EstoqueEsgotadoException() {
        super("O estoque do produto já esgotou");
    }
}
