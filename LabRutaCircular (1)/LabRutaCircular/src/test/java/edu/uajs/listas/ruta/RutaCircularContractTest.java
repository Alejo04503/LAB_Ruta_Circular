package edu.uajs.listas.ruta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas del CONTRATO. Se ejecutan contra las dos implementaciones
 * (ver RutaCircularManualTest y RutaCircularLibTest). No las modifiquen.
 *
 * Semáforo: cada prueba verde = un hito cumplido.
 */
abstract class RutaCircularContractTest {

    protected RutaCircular p;

    abstract RutaCircular crear();

    static Parada c(String nombre, int seg) {
        return new Parada(nombre, "Sector", seg);
    }

    /** RutaCircular A, B, C, D (60 s cada una = 240 s). */
    @BeforeEach
    void preparar() {
        p = crear();
    }

    private void cargarABCD() {
        p.agregar(c("A", 60));
        p.agregar(c("B", 60));
        p.agregar(c("C", 60));
        p.agregar(c("D", 60));
    }

    // ── Hito 1 ────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Hito 1: ruta vacía")
    void hito1_vacia() {
        assertEquals(0, p.tamanio());
        assertNull(p.actual());
    }

    @Test
    @DisplayName("Hito 1: la primera parada agregada empieza a quedar como parada actual")
    void hito1_primeraSuena() {
        p.agregar(c("A", 60));
        assertEquals("A", p.actual().nombre());
        assertEquals(1, p.tamanio());
    }

    @Test
    @DisplayName("Hito 1: agregar conserva el orden y no mueve la que es la parada actual")
    void hito1_ordenAgregar() {
        cargarABCD();
        assertEquals(4, p.tamanio());
        assertEquals("A", p.actual().nombre());
        assertEquals(List.of("A", "B", "C", "D"), p.nombresDesdeActual());
    }

    // ── Hito 2 ────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Hito 2: siguiente avanza")
    void hito2_siguiente() {
        cargarABCD();
        assertEquals("B", p.siguiente().nombre());
        assertEquals("C", p.siguiente().nombre());
    }

    @Test
    @DisplayName("Hito 2: CIRCULAR - después de la última es la parada actual la primera")
    void hito2_siguienteCircular() {
        cargarABCD();
        p.siguiente(); p.siguiente(); p.siguiente();   // D
        assertEquals("D", p.actual().nombre());
        assertEquals("A", p.siguiente().nombre());
    }

    @Test
    @DisplayName("Hito 2: anterior retrocede")
    void hito2_anterior() {
        cargarABCD();
        p.siguiente(); p.siguiente();                  // C
        assertEquals("B", p.anterior().nombre());
    }

    @Test
    @DisplayName("Hito 2: CIRCULAR - antes de la primera es la parada actual la última")
    void hito2_anteriorCircular() {
        cargarABCD();
        assertEquals("D", p.anterior().nombre());
    }

    @Test
    @DisplayName("Hito 2: siguiente/anterior con ruta vacía no fallan")
    void hito2_vacia() {
        assertNull(p.siguiente());
        assertNull(p.anterior());
    }

    @Test
    @DisplayName("Hito 2: con una sola parada siempre es la parada actual la misma")
    void hito2_unaSola() {
        p.agregar(c("A", 60));
        assertEquals("A", p.siguiente().nombre());
        assertEquals("A", p.anterior().nombre());
    }

    // ── Hito 3 ────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Hito 3: saltar adelante")
    void hito3_saltarAdelante() {
        cargarABCD();
        assertEquals("C", p.saltar(2).nombre());
    }

    @Test
    @DisplayName("Hito 3: saltar atrás (n negativo)")
    void hito3_saltarAtras() {
        cargarABCD();
        assertEquals("C", p.saltar(-2).nombre());
    }

    @Test
    @DisplayName("Hito 3: saltar más que el tamaño da vueltas completas")
    void hito3_saltarConVueltas() {
        cargarABCD();
        assertEquals("C", p.saltar(10).nombre());      // 10 % 4 = 2
        assertEquals("A", p.saltar(-10).nombre());     // -10 % 4 = -2
    }

    @Test
    @DisplayName("Hito 3: saltar(0) y saltar en vacía")
    void hito3_casosBorde() {
        assertNull(p.saltar(5));
        cargarABCD();
        assertEquals("A", p.saltar(0).nombre());
        assertEquals("A", p.saltar(4).nombre());       // vuelta completa
    }

    // ── Hito 4 ────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Hito 4: eliminar la actual (en medio) pasa a la siguiente")
    void hito4_eliminarEnMedio() {
        cargarABCD();
        p.siguiente();                                  // B
        assertEquals("C", p.eliminarActual().nombre());
        assertEquals(3, p.tamanio());
        assertEquals(List.of("C", "D", "A"), p.nombresDesdeActual());
    }

    @Test
    @DisplayName("Hito 4: eliminar la primera de la lista")
    void hito4_eliminarPrimera() {
        cargarABCD();
        assertEquals("B", p.eliminarActual().nombre());
        assertEquals(List.of("B", "C", "D"), p.nombresDesdeActual());
        // el círculo debe seguir cerrado: de D se vuelve a B
        p.saltar(2);
        assertEquals("D", p.actual().nombre());
        assertEquals("B", p.siguiente().nombre());
    }

    @Test
    @DisplayName("Hito 4: eliminar la última (la actual es la última)")
    void hito4_eliminarUltima() {
        cargarABCD();
        p.anterior();                                   // D
        assertEquals("A", p.eliminarActual().nombre());
        assertEquals(List.of("A", "B", "C"), p.nombresDesdeActual());
        assertEquals("C", p.anterior().nombre());       // el círculo sigue cerrado
    }

    @Test
    @DisplayName("Hito 4: eliminar hasta dejar la ruta vacía")
    void hito4_eliminarTodas() {
        p.agregar(c("A", 60));
        p.agregar(c("B", 60));
        assertEquals("B", p.eliminarActual().nombre());
        assertNull(p.eliminarActual());
        assertEquals(0, p.tamanio());
        assertNull(p.actual());
        assertNull(p.eliminarActual());                 // otra vez en vacía: sin error
    }

    @Test
    @DisplayName("Hito 4: se puede volver a agregar después de vaciar")
    void hito4_reutilizable() {
        p.agregar(c("A", 60));
        p.eliminarActual();
        p.agregar(c("X", 30));
        assertEquals("X", p.actual().nombre());
        assertEquals(1, p.tamanio());
    }

    // ── Hito 5 ────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Hito 5: tiempo total")
    void hito5_tiempoTotal() {
        assertEquals(0, p.tiempoTotal());
        p.agregar(c("A", 100));
        p.agregar(c("B", 50));
        p.agregar(c("C", 25));
        assertEquals(175, p.tiempoTotal());
        p.siguiente();
        assertEquals(175, p.tiempoTotal());           // no depende de cuál es la parada actual
        p.eliminarActual();
        assertEquals(125, p.tiempoTotal());
    }
}
