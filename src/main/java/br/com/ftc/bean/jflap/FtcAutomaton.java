package br.com.ftc.bean.jflap;

public interface FtcAutomaton {
    /**
     * Aplica a função de transição no autômato.
     * @param c caractere lido da sentença
     */
    void transition(char c);

    /**
     * Inicia o automato apontando o estado atual para o inicial.
     */
    void initialize();

    /**
     * Testa se a sentença informada pertence à linguagem.
     * @return se o estado em que o autômato se encontra é de aceite.
     */
    boolean isAccepted();

    /**
     * Testa se o autômato é finito e determinista.
     * @return se o autômato é finito determinista.
     */
    boolean isAFD();

    /**
     * Remove estados inalvançáveis a partir do estado inicial do autômato
     */
    void minimize();
}
