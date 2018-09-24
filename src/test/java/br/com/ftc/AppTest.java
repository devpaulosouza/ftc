package br.com.ftc;

import br.com.ftc.bean.jflap.Automaton;
import br.com.ftc.factory.AutomatonReader;
import br.com.ftc.factory.AutomatonReaderFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private Automaton automaton;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void readAutomaton()
    {
        AutomatonReader reader = AutomatonReaderFactory.getReader("xml", "teste.xml");
        try {
            automaton = reader.readAutomaton();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(automaton);

        automaton.initialize();

        assertNotNull(automaton.getCurrentState());

    }
}
