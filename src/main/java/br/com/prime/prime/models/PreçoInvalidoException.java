package br.com.prime.prime.models;

public class PreçoInvalidoException extends Exception {

    public PreçoInvalidoException() {
        super("O preço deve estar entre 1 e 10,000");
    }

}
