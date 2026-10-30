package edu.uajs.listas.ruta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** PARTE 2: contrato contra la implementación con librerías + retos extra. */
class RutaCircularLibTest extends RutaCircularContractTest {

    @Override
    RutaCircular crear() {
        return new RutaCircularLib();
    }

    private RutaCircularLib lib() {
        return (RutaCircularLib) p;
    }

    private void cargar() {
        p.agregar(c("A", 60));
        p.agregar(c("B", 60));
        p.agregar(c("C", 60));
        p.agregar(c("D", 60));
    }

    @Test
    @DisplayName("Extra: irA salta directo a una parada por título")
    void extra_irA() {
        cargar();
        assertTrue(lib().irA("c"));                     // sin importar mayúsculas
        assertEquals("C", p.actual().nombre());
        assertEquals(List.of("C", "D", "A", "B"), p.nombresDesdeActual());
    }

    @Test
    @DisplayName("Extra: irA con un título inexistente devuelve false y no cambia nada")
    void extra_irANoExiste() {
        cargar();
        assertFalse(lib().irA("Z"));
        assertEquals("A", p.actual().nombre());
    }

    @Test
    @DisplayName("Extra: reordenar conserva las mismas paradas")
    void extra_reordenar() {
        cargar();
        lib().reordenar(42);
        List<String> reordenadas = new ArrayList<>(p.nombresDesdeActual());
        Collections.sort(reordenadas);
        assertEquals(List.of("A", "B", "C", "D"), reordenadas);
        assertEquals(4, p.tamanio());
    }
}
