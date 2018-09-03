package br.com.ftc.bean.jflap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Bean para leitura da transição de um estado para outro da saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
@JacksonXmlRootElement(localName = "transition")
public class Transition {
    /**
     * Estado de partida
     */
    private String from;

    /**
     * Estado de chegada
     */
    private String to;

    /**
     * Valor para transição
     */
    private String read;

    public Transition() {
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", read='" + read + '\'' +
                '}';
    }
}