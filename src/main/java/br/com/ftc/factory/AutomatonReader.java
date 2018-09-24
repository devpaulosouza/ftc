package br.com.ftc.factory;

import br.com.ftc.bean.jflap.Automaton;
import br.com.ftc.exceptions.NotImplementedException;

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
     */
    Automaton readAutomaton() throws IOException, NotImplementedException;

    void writeAutomaton(Automaton automaton, String filename) throws IOException;
}
