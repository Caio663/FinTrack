package br.com.fintrack.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RepositorioGenericoTest {
    @Test void deveAdicionarListarERemoverUsandoGenerics() {
        RepositorioGenerico<Number> repositorio = new RepositorioGenerico<>();
        repositorio.adicionarTodos(List.of(1, 2L, 3.0)); // ? extends Number
        List<Object> removidos = new ArrayList<>();
        assertTrue(repositorio.removerTodos(removidos, n -> n.intValue() == 2)); // ? super Number
        assertEquals(List.of(1, 3.0), repositorio.listar());
        assertEquals(List.of(2L), removidos);
    }
}
