package br.com.ftc;

import br.com.ftc.bean.Automaton;
import br.com.ftc.factory.AutomatonReader;
import br.com.ftc.factory.AutomatonReaderFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * <p>Classe principal com o controle dos métodos para operação com autômatos.</p>
 * <p>
 *     O objetivo é implementar os algorítmos aprendidos nas aulas de Fundamentos Teóricos da Computação
 * </p>
 * @author Paulo H Souza
 * @version 0.1.0
 */
public class App {

    /**
     * Controle da chamada dos métodos para leitura de autômatos
     */
    private static void readAutomaton() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("File to read: ");
        String filename = scanner.nextLine();

        AutomatonReader reader;
        try {
            reader = AutomatonReaderFactory.getReader("xml", filename);

            Automaton automaton = reader.readAutomaton();

            System.out.println(automaton);
        } catch (IOException e) {
            System.out.println("[ERROR] IOException");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[ERROR] unknown");
            e.printStackTrace();
        }
    }

    /**
     * Exibe um menu para interação com o usuário
     */
    private static void menu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("Ultra mega blaster automaton simulator\n");
        System.out.println("Choose a option:");
        System.out.println("\t0 - read automaton.");
        System.out.println("\t9 - exit.");

        System.out.print("[Option]: ");
        String option = scanner.nextLine();

        switch (option) {
            case "0":
                readAutomaton();
                menu();
                break;
            case "9":
                break;
            default:
                menu();
                break;
        }
    }

    /**
     * Método principal do controle
     * @param args argumentos do CLI
     */
    public static void main(String... args) {
        try {
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
