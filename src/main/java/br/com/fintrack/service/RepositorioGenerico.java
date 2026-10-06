package br.com.fintrack.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/** Repositório em memória reutilizável. Os curingas permitem consumir subtipos e produzir supertipos com segurança. */
public class RepositorioGenerico<T> {
    private final List<T> itens = new ArrayList<>();
    public void adicionar(T item) { itens.add(item); }
    public void adicionarTodos(Collection<? extends T> novosItens) { itens.addAll(novosItens); }
    public boolean remover(T item) { return itens.remove(item); }
    public boolean removerTodos(Collection<? super T> destino, Predicate<? super T> criterio) {
        List<T> removidos = itens.stream().filter(criterio).toList(); destino.addAll(removidos); return itens.removeAll(removidos);
    }
    public List<T> listar() { return List.copyOf(itens); }
    public <R extends T> List<R> listarComo(Class<R> tipo) { return itens.stream().filter(tipo::isInstance).map(tipo::cast).toList(); }
    public int tamanho() { return itens.size(); }
}
