package br.com.ftc;

import br.com.ftc.bean.jflap.Automaton;
import br.com.ftc.factory.AutomatonReader;
import br.com.ftc.factory.AutomatonReaderFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Scanner;

/**
 * <p>Classe principal com o controle dos métodos para operação com autômatos.</p>
 * <p>
 *     O objetivo é implementar os algorítmos aprendidos nas aulas de Fundamentos Teóricos da Computação
 * </p>
 * @author Paulo H Souza
 * @version 0.1.1
 */
public class App {
    private static Automaton automaton;
    private static Scanner scanner;
    private static AutomatonReader reader;

    /**
     * Controle da chamada dos métodos para leitura de autômatos
     */
    private static void readAutomaton() {

        System.out.print("File to read: ");
        String filename = scanner.nextLine();

        try {
            reader = AutomatonReaderFactory.getReader("xml", filename);
            automaton = reader.readAutomaton();

            printAutomaton();
        } catch (Exception e) {
            System.out.println("[ERROR]: ".concat(e.getMessage()));
        }
   }

    /**
     * Testa uma entrada e diz se está na linguagem definida pelo autômato
     */
    private static void testInput() {
        String sentence = scanner.nextLine();

        if (automaton != null && automaton.isAFD()) {
            while (sentence.length() > 0) {
                automaton.transition(sentence.charAt(0));
                sentence = sentence.substring(1);
            }
            System.out.println((automaton.isAccepted() ? "Accepted" : "Rejected"));
            automaton.initialize();
        } else {
            throw new NotImplementedException();
        }
    }

    private static void minimize() {
        if (automaton == null) {
            System.out.println("[ERROR]: Autômato não informado. Leia um autômato para utilizar este método");
            return;
        }

        automaton.minimize();

        printAutomaton();
    }

    private static void printAutomaton() {
        System.out.println(automaton);
    }

    private static void saveAutomaton() {
        if (automaton == null) {
            System.out.println("[ERROR]: Autômato não informado. Leia um autômato para utilizar este método");
            return;
        }
        if (reader == null) {
            System.out.println("[ERROR]: leitor não criado. Contate o suporte =P");
            return;
        }

        System.out.println("Informe o nome do arquivo a ser salvo. (Ou apenas ENTER para \"out.xml\")");
        String filename = scanner.nextLine();

        filename = (filename.isEmpty()) ? "out.xml" : filename;

        try {
            reader.writeAutomaton(automaton, filename);
        } catch (IOException e) {
            System.out.println("[ERROR]: ".concat(e.getMessage()));
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
        System.out.println("\t1 - test sentence.");
        System.out.println("\t2 - minimize automaton.");
        System.out.println("\t3 - save xml file.");
        System.out.println("\t9 - exit.");

        System.out.print("[Option]: ");
        String option = scanner.nextLine();

        switch (option) {
            case "0":
                readAutomaton();
                menu();
                break;
            case "1":
                testInput();
                menu();
                break;
            case "2":
                minimize();
                menu();
                break;
            case "3":
                printAutomaton();
                menu();
                break;
            case "4":
                saveAutomaton();
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
            scanner = new Scanner(System.in);
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
