package br.com.ftc.factory;

import java.io.FileNotFoundException;

/**
 * Fábrica de leitor de arquivos do JFlap
 * @author Paulo H Souza
 * @version 0.1.0
 */
public class AutomatonReaderFactory {
    /**
     * Retorna um leitor de arquivos de autômatos com os padrões do JFlap
     * @param type tipo do arquivo. Sempre passe xml. Parâmetrizado para encher o saco.
     * @param fileName nome do arquivo a ser lido
     * @return leitor de arquivos e conversor para autômatos
     */
    public static AutomatonReader getReader(String type, String fileName) {
        AutomatonReader reader = null;

        if(type.equalsIgnoreCase("xml")) {
            reader = new AutomatonXMLReader(fileName);
        }

        return reader;
    }
}
