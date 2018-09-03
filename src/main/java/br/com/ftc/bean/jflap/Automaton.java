package br.com.ftc.bean.jflap;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

/**
 * Bean para leitura de autômato da saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
public class Automaton {

    /**
     * Lista de estados do autômato
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<State> state;

    /**
     * lista de transições do autômato
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Transition> transition;

    public Automaton() {
    }

    public List<State> getState() {
        return this.state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<Transition> getTransition() {
        return this.transition;
    }

    public void setTransition(List<Transition> transition) {
        this.transition = transition;
    }

    @Override
    public String toString() {
        return "Automaton{" +
                "state=" + state +
                ", transition=" + transition +
                '}';
    }
}