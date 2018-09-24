package br.com.ftc.bean.jflap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Bean para leitura de autômato da saída do JFlap
 * @author Paulo H Souza
 * @version 0.1.1
 */
@SuppressWarnings("unused")
public class Automaton implements FtcAutomaton {

    /**
     * Lista de estados do autômato
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<State> state;

    /**
     * lista de transições do autômato
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Transition> transition;

    @JsonIgnore
    private Map<State, List<Transition>> transitions;

    @JsonIgnore
    private List<Character> alphabet;

    @JsonIgnore
    private State error;

    /**
     * Estado atual do autômato
     */
    @JsonIgnore
    private State currentState;

    public Automaton() {
    }

    @Override
    public String toString() {
        return "Automaton{" +
                "state=" + state +
                ", transition=" + transition +
                '}';
    }

    @Override
    public void transition(char c) {

        this.transitions.get(currentState).forEach(t->{
            if (((Character)c).equals(t.getRead())){
                this.currentState = this.getStateById(t.getTo());
            }
        });
    }

    @Override
    public void initialize() {
        this.transitions = new HashMap<>();

        this.error = new State();
        error.setId(-1);

        state.forEach(s -> transitions.put(s, new LinkedList<>()));

        // Nenhum medo de NullPointerException
        transition.forEach(t -> this.transitions.get(getStateById(t.getFrom())).add(t));

        state.forEach(s -> {
            if (s.isInitialState()) {
                this.currentState = s;
            }
        });

        this.alphabet = transition.stream()
                .map(Transition::getRead)
                .distinct()
                .collect(Collectors.toCollection(LinkedList::new));
        this.alphabet.remove((Character) '\0');

    }

    @Override
    @JsonIgnore
    public boolean isAccepted() {
        return currentState != null && currentState.isFinalState();
    }

    @Override
    @JsonIgnore
    public boolean isAFD() {
        return !this.isAFN();
    }

    @Override
    public void minimize() {
        this.initialize();
        this.removeUnreachableStates();

        // FIXME: só funciona quando a enumeração dos estados é de 0..n
        boolean[][] equivalents = new boolean[this.state.size()][this.state.size()];
        List<State> reducedStates = new LinkedList<>();

        for (int i = 0; i < equivalents.length; ++i) {
            for (int j = 0; j < equivalents.length; j++) {
                if (i != j && i > j) {
                    equivalents[i][j] = this.getStateById(i).isFinalState() == this.getStateById(j).isFinalState();
                    final int finalI = i, finalJ = j;
                    this.alphabet.forEach(c -> equivalents[finalI][finalJ] &= transition(finalI, c).isFinalState() == transition(finalJ, c).isFinalState());

                    this.alphabet.forEach(c -> this.alphabet.forEach(d->
                            equivalents[finalI][finalJ] &=
                            transition(transition(finalI, c).getId(), d).isFinalState() ==
                            transition(transition(finalJ, c).getId(), d).isFinalState()
                    ));

                    if (equivalents[i][j]) {
                        State newState = new State();
                        State si = getStateById(i);
                        State sj = getStateById(j);
                        newState.setFinalState(si.isFinalState());
                        newState.setInitial(si.isInitialState() || sj.isInitialState());
                        newState.setName(si.getName().concat(sj.getName()));
                        newState.setX(si.getX());
                        newState.setY(si.getY());

                        AtomicInteger maxIdOld = new AtomicInteger();
                        AtomicInteger maxIdGrouped = new AtomicInteger();

                        this.state.stream().max(Comparator.comparing(State::getId)).ifPresent(s -> maxIdOld.set(s.getId() + 1));
                        reducedStates.stream().max(Comparator.comparing(State::getId)).ifPresent(s -> maxIdGrouped.set(s.getId() + 1));

                        newState.setId(Math.max(maxIdOld.get(), maxIdGrouped.get()));

                        reducedStates.add(newState);
                    }
                }
            }
        }


        // Adiciona todos estados que não foram agrupados na lista
        boolean notGrouped = true;

        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.size(); j++) {
                notGrouped &= !equivalents[i][j] && !equivalents[j][i];
            }
            if (notGrouped) {
                reducedStates.add(this.getStateById(i));
            }
        }

        Map<State, List<Transition>> newTransitions = new HashMap<>();

        // gera novas transições
        reducedStates.forEach(s -> alphabet.forEach(a -> {
            Transition t = new Transition();

            t.setFrom(s.getId());
            t.setRead(a);
            int oldState = Integer.parseInt(s.getName().split("q")[1]);
            t.setTo(this.transition(oldState, a).getId());

            // se o próximo estado for um dos reduzidos
            reducedStates.forEach(rs -> {
                if (rs.getName().contains(t.getTo().toString())) {
                    t.setTo(rs.getId());
                }
            });

            newTransitions.computeIfAbsent(s, k -> new LinkedList<>());

            if (!newTransitions.get(s).contains(t)){
                newTransitions.get(s).add(t);
            }
        }));


        // renomeia ids para uma possível próximo leitura
        for (int i = 0; i < reducedStates.size(); i++) {
            final int oldId = reducedStates.get(i).getId();
            reducedStates.get(i).setId(i);

            for (Entry<State, List<Transition>> e : newTransitions.entrySet()) {
                int finalI = i;
                List<Transition> transitions = e.setValue(e.getValue()
                        .stream()
                        .peek(t -> {
                            t.setTo((t.getTo() == oldId) ? finalI : t.getTo());
                            t.setFrom(t.getFrom() == oldId ? finalI : t.getFrom());
                        })
                        .collect(Collectors.toCollection(LinkedList::new)));
                e.setValue(transitions);


            }


        }

        this.state = reducedStates;
        this.transitions = newTransitions;

        this.generateNewTransitions();

    }

    private State transition(int from, char read) {
        return this.transitions
                .get(this.getStateById(from))
                .stream()
                .filter(t -> t.getRead() == read)
                .findFirst()
                .map(t -> this.getStateById(t.getTo()))
                .orElse(new State());
    }

    /**
     * Remove todos os estados inalcançáveis.
     */
    private void removeUnreachableStates() {
        List<State> reachable = new LinkedList<>();
        this.listUnreachableStates(this.currentState, reachable);

        // remove das imagens das funções de transição
        for (Entry transition : this.transitions.entrySet()) {
            //noinspection unchecked
            this.transitions.replace(
                    (State) transition.getKey(),
                    ((LinkedList<Transition>)transition.getValue())
                            .stream()
                            .filter(t-> reachable.contains(this.getStateById(t.getTo())))
                            .collect(Collectors.toCollection(LinkedList::new)));
        }

        // remove do domínio do conjunto de transições (Map de operações)
        this.transitions = this.transitions
                .entrySet()
                .stream()
                .filter(t -> reachable.contains(t.getKey()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        // Se o estado é inalcançável, remove ele da lista de estados
        this.state = this.state
                .stream()
                .filter(reachable::contains)
                .collect(Collectors.toList());

    }

    /**
     * Lista todos estados inalcançáveis a partir do estado inicial de forma recursiva
     * @param current estado atual
     * @param visited lista de estados já visitados
     */
    private void listUnreachableStates(State current, List<State> visited) {
        if (!visited.contains(current)) {
            visited.add(current);
            this.transitions.get(current).forEach(t -> listUnreachableStates(this.getStateById(t.getTo()),visited));
        }
    }

    /**
     * Verifica se é autômato não determinista
     * @return se é um AFN
     */
    private boolean isAFN() {
        Map<Transition, Character> memory = new HashMap<>();
        AtomicBoolean afn = new AtomicBoolean(false);
        this.transition.forEach(t -> {
            afn.set(
                    afn.get() ||  // valor anterior ou
                    memory.get(t) != null || // já possuia uma transição com o mesmo valor ou
                    ((!t.getFrom().equals(t.getTo()) && t.getRead() == null))); // aceita lambda
            memory.put(t, t.getRead());
        });

        return afn.get();
    }

    /**
     * Gera novas transições para o xml do JFlap
     */
    private void generateNewTransitions() {
        List<Transition> newList = new LinkedList<>();
        for (Entry e: this.transitions.entrySet()) {
            //noinspection unchecked
            newList.addAll(((LinkedList<Transition>) e.getValue()));
        }
        this.transition = newList;

    }

    private State getStateById(int id) {
        return this.state
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<Transition> getTransition() {
        return transition;
    }

    public void setTransition(List<Transition> transition) {
        this.transition = transition;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}