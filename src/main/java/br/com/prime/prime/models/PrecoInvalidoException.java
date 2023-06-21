package br.com.prime.prime.models;

public class PrecoInvalidoException extends Exception {

    public PrecoInvalidoException() {
        super("O preço deve estar entre 1 e 10.000,00");
    }

}
