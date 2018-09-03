package br.com.ftc.bean.jflap;

import br.com.ftc.bean.jflap.Automaton;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Bean para leitura do XML de saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
@JacksonXmlRootElement
public class Structure {

    /**
     * Tipo do autômato.
     * <ul>
     *     <li>afd</li>
     *     <li>afn</li>
     * </ul>
     */
    private String type;

    /**
     * Autômato
     */
    private Automaton automaton;

    public Structure() {
    }

    public Structure(String type, Automaton automaton) {
        this.type = type;
        this.automaton = automaton;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }
}