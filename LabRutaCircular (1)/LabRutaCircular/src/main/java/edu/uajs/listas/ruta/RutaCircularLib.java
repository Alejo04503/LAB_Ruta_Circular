package edu.uajs.listas.ruta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * PARTE 2 - La MISMA ruta, ahora CON librerías (java.util).
 *
 * java.util no trae lista circular. Igual que en clase, le damos comportamiento
 * circular a un LinkedList mediante ROTACIÓN. La regla del juego:
 *
 *     cola.getFirst()  →  la parada que es la parada actual ahora
 *
 *     siguiente  = el primero pasa al final   (removeFirst + addLast)
 *     anterior   = el último pasa al inicio   (removeLast  + addFirst)
 *     saltar(n)  = Collections.rotate(cola, -n)
 *
 * El "círculo" es lógico: internamente el último nodo sigue apuntando a null.
 *
 * Estudiante: complete los TODO y ejecute:  mvn test -Dtest=RutaCircularLibTest
 * Observe: son las MISMAS pruebas de la Parte 1. Si pasan, su librería se
 * comporta igual que su lista manual.
 */
public class RutaCircularLib implements RutaCircular {

    private final LinkedList<Parada> cola = new LinkedList<>();

    @Override
    public void agregar(Parada c) {
        // TODO L1: agregue la parada AL FINAL de 'cola' (addLast).
        cola.addLast(c);
    }

    @Override
    public Parada actual() {
        // TODO L2: devuelva el primero SIN sacarlo. peekFirst() devuelve null si está vacía
        //          (getFirst() lanzaría excepción: ¿cuál conviene aquí y por qué?)
        return cola.peekFirst();
    }

    @Override
    public Parada siguiente() {
        // TODO L3: si 'cola' está vacía devuelva null.
        //          Si no: saque el primero (removeFirst) y agréguelo al final (addLast),
        //          luego devuelva el nuevo primero.
        if (cola.isEmpty()) return null;
        cola.addLast(cola.removeFirst());
        return cola.peekFirst();
    }

    @Override
    public Parada anterior() {
        // TODO L4: lo contrario de siguiente(): removeLast + addFirst.
        if (cola.isEmpty()) return null;
        cola.addFirst(cola.removeLast());
        return cola.peekFirst();
    }

    @Override
    public Parada saltar(int n) {
        // TODO L5: si está vacía devuelva null.
        //          Use Collections.rotate(cola, distancia).
        //          Pregunta: para ADELANTAR n paradas, ¿la distancia es n o -n?
        //          Pruebe con una lista [A,B,C,D] en papel.
        //          Devuelva el nuevo primero.
        if (cola.isEmpty()) return null;
        Collections.rotate(cola, -n);
        return cola.peekFirst();
    }

    @Override
    public Parada eliminarActual() {
        // TODO L6: si está vacía devuelva null. Si no, elimine el primero (removeFirst)
        //          y devuelva el que quedó de primero. ¡Compare con las 15 líneas
        //          del Hito 4 de la Parte 1!
        if (cola.isEmpty()) return null;
        cola.removeFirst();
        return cola.peekFirst();
    }

    @Override
    public int tamanio() {
        return cola.size();                              // YA RESUELTO
    }

    @Override
    public int tiempoTotal() {
        // TODO L7: sume los minutos con un for-each sobre 'cola'.
        //          (Opcional: hágalo con streams:  cola.stream().mapToInt(Parada::minutos).sum() )
        int total = 0;
        for (Parada parada : cola) {
            total += parada.minutos();
        }
        return total;
    }

    @Override
    public List<String> nombresDesdeActual() {
        // YA RESUELTO: observe el ListIterator, es el iterador "con memoria de posición".
        List<String> nombres = new ArrayList<>();
        ListIterator<Parada> it = cola.listIterator();
        while (it.hasNext()) {
            nombres.add(it.next().nombre());
        }
        return nombres;
    }

    // ═════════ RETOS EXTRA (solo si terminó antes de tiempo) ═════════

    /**
     * RETO EXTRA 1: salta directamente a una parada por título (ignore mayúsculas).
     * Devuelve false si no existe y no cambia nada.
     * Pista: ListIterator tiene nextIndex(); luego reutilice saltar(indice).
     */
    public boolean irA(String nombre) {
        ListIterator<Parada> it = cola.listIterator();
        while (it.hasNext()) {
            int indice = it.nextIndex();
            if (it.next().nombre().equalsIgnoreCase(nombre)) {
                saltar(indice);
                return true;
            }
        }
        return false;
    }

    /**
     * RETO EXTRA 2: recorrido alternativo. Reordene la cola con Collections.shuffle,
     * pasando  new Random(semilla)  para que sea repetible.
     */
    public void reordenar(long semilla) {
        Collections.shuffle(cola, new Random(semilla));
    }
}
