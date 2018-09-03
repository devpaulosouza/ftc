package br.com.ftc.factory;

import br.com.ftc.bean.jflap.Automaton;

import java.io.IOException;

/**
 * Leitor de autômatos para a saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
public interface AutomatonReader {
    /**
     * Lê um arquivo de saída do JFlap e transforma em um objeto para ser manipulado
     * @return Autômato a ser operado
     * @throws IOException no caso de falha na leitura do arquivo
     * TODO: retornar um objeto realmente manipulável. Já que o objeto atual é totalmente baseado no arquivo
     */
    Automaton readAutomaton() throws IOException;
}
