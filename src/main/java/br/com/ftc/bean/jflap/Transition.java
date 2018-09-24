package br.com.ftc.bean.jflap;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
    @JacksonXmlProperty(localName = "from")
    private Integer from;

    /**
     * Estado de chegada
     */
    @JacksonXmlProperty(localName = "to")
    private Integer to;

    /**
     * Valor para transição
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JacksonXmlProperty(localName = "read")
    private Character read;

    public Transition() {
    }

    Integer getFrom() {
        return this.from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    Integer getTo() {
        return this.to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    Character getRead() {
        return this.read;
    }

    public void setRead(Character read) {
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