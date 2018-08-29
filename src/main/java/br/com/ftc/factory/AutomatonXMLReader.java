package br.com.ftc.factory;

import br.com.ftc.bean.Automaton;
import br.com.ftc.bean.Structure;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;

/**
 * Leitor de autômatos no formato XML para as saídas do JFlap
 * @author Paulo H Souza
 * @version 0.1.0
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
    public Automaton readAutomaton() throws IOException {
        File file = new File(this.filename);
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));

        Structure structure = xmlMapper.readValue(xml, Structure.class);

        return structure.getAutomaton();
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