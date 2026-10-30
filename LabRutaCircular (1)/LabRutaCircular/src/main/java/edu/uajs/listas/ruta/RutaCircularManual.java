package edu.uajs.listas.ruta;

import java.util.ArrayList;
import java.util.List;

/**
 * PARTE 1 - RutaCircular circular SIN librerías (lista doblemente enlazada circular).
 *
 *            ┌────────────────────────────────────────────┐
 *            ▼                                            │
 *   primero → [A] ⇄ [B] ⇄ [C] ⇄ [D] ⇄ ... ⇄ [Z] ──────────┘
 *              ▲
 *            actual  (la parada que es la parada actual)
 *
 * Dos referencias:
 *   primero: la primera parada agregada. Como la lista es circular,
 *            el ÚLTIMO nodo siempre es  primero.anterior  (¡inserción al final en O(1)!)
 *   actual : la parada que está en servicio ahora
 *
 * Estudiante: busque los comentarios  "TODO"  y complete el código.
 * Después de cada hito ejecute:  mvn test -Dtest=RutaCircularManualTest
 */
public class RutaCircularManual implements RutaCircular {

    private Nodo primero;
    private Nodo actual;
    private int tamanio;

    // ══════════════════════ HITO 1: agregar (≈ 8 min) ══════════════════════
    @Override
    public void agregar(Parada c) {
        Nodo nuevo = new Nodo(c);

        if (primero == null) {
            // Lista vacía: el nodo se apunta a sí mismo en ambos sentidos (YA RESUELTO)
            nuevo.siguiente = nuevo;
            nuevo.anterior = nuevo;
            primero = nuevo;
            actual = nuevo;
        } else {
            Nodo ultimo = primero.anterior;   // el último está "detrás" del primero

            // TODO 1.1: enlace del nuevo hacia atrás  (el nuevo viene después de 'ultimo')
            //           nuevo.anterior = ...;
            nuevo.anterior = ultimo;

            // TODO 1.2: enlace del nuevo hacia adelante (después del último viene 'primero')
            //           nuevo.siguiente = ...;
            nuevo.siguiente = primero;

            // TODO 1.3: 'ultimo' ahora debe apuntar hacia adelante al nuevo
            //           ultimo.siguiente = ...;
            ultimo.siguiente = nuevo;

            // TODO 1.4: 'primero' ahora debe apuntar hacia atrás al nuevo (cierra el círculo)
            //           primero.anterior = ...;
            primero.anterior = nuevo;
        }
        tamanio++;
    }

    @Override
    public Parada actual() {
        return actual == null ? null : actual.dato;   // YA RESUELTO
    }

    // ══════════════════ HITO 2: siguiente / anterior (≈ 6 min) ═════════════
    @Override
    public Parada siguiente() {
        // TODO 2.1: si la lista está vacía (actual == null) devuelva null.
        // TODO 2.2: mueva 'actual' al nodo siguiente.
        //           (¿Hace falta preguntar si es el último? ¡NO! La lista es circular.)
        // TODO 2.3: devuelva la parada (dato) del nuevo 'actual'.
        if (actual == null) return null;
        actual = actual.siguiente;
        return actual.dato;
    }

    @Override
    public Parada anterior() {
        // TODO 2.4: lo mismo que siguiente(), pero hacia atrás.
        if (actual == null) return null;
        actual = actual.anterior;
        return actual.dato;
    }

    // ═════════════════ HITO 3: adelantar / retroceder N (≈ 6 min) ══════════
    @Override
    public Parada saltar(int n) {
        // TODO 3.1: si la lista está vacía devuelva null.
        //
        // TODO 3.2 (optimización): dar vueltas completas no cambia nada, así que
        //           reduzca n con el residuo:   n = n % tamanio;
        //           (en Java, -10 % 4 vale -2: el signo se conserva)
        //
        // TODO 3.3: si n es positivo, repita n veces:  actual = actual.siguiente
        //           si n es negativo, repita |n| veces: actual = actual.anterior
        //           Pista: dos ciclos for, uno con (i < n) y otro con (i > n) y i--.
        //
        // TODO 3.4: devuelva la parada del 'actual'.
        if (actual == null) return null;
        n = n % tamanio;
        for (int i = 0; i < n; i++) {
            actual = actual.siguiente;
        }
        for (int i = 0; i > n; i--) {
            actual = actual.anterior;
        }
        return actual.dato;
    }

    // ═══════════════ HITO 4: eliminar la que es la parada actual (≈ 10 min) ══════════════
    @Override
    public Parada eliminarActual() {
        if (actual == null) return null;               // YA RESUELTO: vacía

        if (tamanio == 1) {                            // YA RESUELTO: era la única
            primero = null;
            actual = null;
            tamanio = 0;
            return null;
        }

        // Caso general (hay al menos 2 paradas).
        Nodo previo = actual.anterior;                 // YA RESUELTO
        Nodo proximo = actual.siguiente;

        // TODO 4.1: "puentear" el nodo: que 'previo' apunte hacia adelante a 'proximo'
        //           previo.siguiente = ...;
        previo.siguiente = proximo;

        // TODO 4.2: y que 'proximo' apunte hacia atrás a 'previo'
        //           proximo.anterior = ...;
        proximo.anterior = previo;

        // TODO 4.3: CUIDADO. Si la que eliminamos era 'primero', la referencia
        //           'primero' quedaría apuntando a un nodo que ya no está en el círculo.
        //           Haga que 'primero' pase a ser 'proximo' en ese caso.
        if (actual == primero) {
            primero = proximo;
        }

        // TODO 4.4: la siguiente parada pasa a quedar como parada actual: actual = proximo;  y tamanio--;
        actual = proximo;
        tamanio--;

        // TODO 4.5: devuelva la parada que es la parada actual ahora.
        return actual.dato;
    }

    @Override
    public int tamanio() {
        return tamanio;                                // YA RESUELTO
    }

    // ═════════ HITO 5: recorrer una vuelta con do-while (≈ 5 min) ══════════
    @Override
    public int tiempoTotal() {
        // TODO 5.1: si la lista está vacía devuelva 0.
        // TODO 5.2: recorra UNA vuelta completa sumando aux.dato.minutos().
        //           Como NO hay null para detenerse, use el patrón de clase:
        //
        //               Nodo aux = primero;
        //               do {
        //                   ... usar aux ...
        //                   aux = aux.siguiente;
        //               } while (aux != primero);
        //
        if (primero == null) return 0;
        int total = 0;
        Nodo aux = primero;
        do {
            total += aux.dato.minutos();
            aux = aux.siguiente;
        } while (aux != primero);
        return total;
    }

    @Override
    public List<String> nombresDesdeActual() {
        // YA RESUELTO: observe el do-while, es el mismo patrón del Hito 5.
        List<String> nombres = new ArrayList<>();
        if (actual == null) return nombres;
        Nodo aux = actual;
        do {
            nombres.add(aux.dato.nombre());
            aux = aux.siguiente;
        } while (aux != actual);
        return nombres;
    }
}
