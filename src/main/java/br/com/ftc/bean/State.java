package br.com.ftc.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Bean para leitura do State da saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.0
 */
@JacksonXmlRootElement(localName = "states")
public class State {

    /**
     * Id do estado
     */
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    /**
     * Rótulo do estado
     */
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    /**
     * Flag de estado inicial
     */
    private boolean initial;

    /**
     * Flag de estado de aceitação
     */
    @JacksonXmlProperty(localName = "final")
    private boolean finalState;

    private double x;

    private double y;

    public State() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initial=" + initial +
                ", finalState=" + finalState +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}