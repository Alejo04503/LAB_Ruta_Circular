package edu.uajs.listas.ruta;

/**
 * Nodo de una lista DOBLEMENTE enlazada y CIRCULAR.
 *
 *        anterior            siguiente
 *   ... ◀────────── [ dato ] ──────────▶ ...
 *
 * En una lista circular NINGUNA referencia es null (salvo lista vacía):
 * el siguiente del último es el primero, y el anterior del primero es el último.
 */
public class Nodo {

    public Parada dato;
    public Nodo siguiente;
    public Nodo anterior;

    public Nodo(Parada dato) {
        this.dato = dato;
    }
}
