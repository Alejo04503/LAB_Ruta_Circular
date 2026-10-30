package edu.uajs.listas.ruta;

import java.util.List;

/**
 * CONTRATO de la ruta. Las dos implementaciones del laboratorio
 * (RutaCircularManual y RutaCircularLib) deben comportarse EXACTAMENTE igual;
 * las mismas pruebas se ejecutan contra las dos.
 *
 * Regla de oro: la ruta es CIRCULAR. Al terminar la última parada
 * es la parada actual la primera, y si retrocedes desde la primera es la parada actual la última.
 */
public interface RutaCircular {

    /** Agrega una parada al final de la lista. Si es la primera, empieza a quedar como parada actual. */
    void agregar(Parada c);

    /** La parada que es la parada actual ahora, o null si la ruta está vacía. */
    Parada actual();

    /** ▶| Pasa a la siguiente (circular) y devuelve la que es la parada actual ahora. */
    Parada siguiente();

    /** |◀ Vuelve a la anterior (circular) y devuelve la que es la parada actual ahora. */
    Parada anterior();

    /** Salta n paradas: n > 0 adelanta, n < 0 retrocede, n = 0 no cambia nada. */
    Parada saltar(int n);

    /**
     * Elimina la parada que es la parada actual y pasa a la siguiente.
     * Devuelve la que es la parada actual ahora (null si la ruta quedó vacía).
     */
    Parada eliminarActual();

    /** Cantidad de paradas. */
    int tamanio();

    /** Suma de minutos de todas las paradas. */
    int tiempoTotal();

    /** Nombres en orden del recorrido: empieza en la actual y da UNA vuelta completa. */
    List<String> nombresDesdeActual();

    /** Muestra la secuencia de paradas; la que es la parada actual lleva la marca ▶. */
    default void mostrar() {
        if (tamanio() == 0) {
            System.out.println("(ruta vacía)");
            return;
        }
        boolean primera = true;
        for (String nombre : nombresDesdeActual()) {
            System.out.println((primera ? " ▶ " : "   ") + nombre);
            primera = false;
        }
        System.out.println("   ↺ (vuelve a empezar)");
    }
}
