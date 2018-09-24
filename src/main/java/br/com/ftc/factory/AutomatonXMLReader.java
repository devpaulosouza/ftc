package br.com.ftc.factory;

import br.com.ftc.bean.jflap.Automaton;
import br.com.ftc.bean.jflap.Structure;
import br.com.ftc.exceptions.NotImplementedException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.*;

/**
 * Leitor de autômatos no formato XML para as saídas do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
public class AutomatonXMLReader implements AutomatonReader {

    private String filename;

    /**
     * Construtor
     * @param filename arquivo a ser operado
     */
    AutomatonXMLReader(String filename) {
        this.filename = filename;
    }

    @Override
    public Automaton readAutomaton() throws IOException, NotImplementedException {
        File file = new File(this.filename);
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));

        Structure structure = xmlMapper.readValue(xml, Structure.class);
        structure.getAutomaton().initialize();

        if (!structure.getAutomaton().isAFD()) {
            throw new NotImplementedException("Este programa não suporta AFNs.");
        }

        return structure.getAutomaton();
    }

    @Override
    public void writeAutomaton(Automaton automaton, String filename) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Structure structure = new Structure();
        structure.setAutomaton(automaton);
        structure.setType("fa");

        String xml = xmlMapper.writeValueAsString(structure);

        // FIXME: O JFlap me obrigou a fazer essa porqueira aqui
        xml = xml.replaceAll("<final>true</final>", "<final />")
                .replaceAll("<initial>true</initial>", "<initial />")
                .replaceAll("<final>false</final>\n","")
                .replaceAll("<initial>false</initial>\n", "");

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(xml);
        writer.close();
    }

    /**
     * Utiliza um inputStream e gera uma String
     * @param is inputStream para ser operado
     * @return String com os valores do arquivo
     * @throws IOException no caso de falha da leitura do arquivo
     */
    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    /**
     * Getter
     * @return o nome do arquivo a ser lido
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter
     * @param filename o nome do arquivo a ser lido
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}