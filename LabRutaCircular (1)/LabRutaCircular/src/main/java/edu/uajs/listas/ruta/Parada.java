package edu.uajs.listas.ruta;

/**
 * Una parada de la ruta. Es un "record": una clase inmutable con
 * constructor, getters (nombre(), sector(), minutos()), equals y toString
 * ya generados por Java. No hay que modificarla.
 */
public record Parada(String nombre, String sector, int minutos) {

    /** Tiempo en formato m:ss, por ejemplo 3:05. */
    public String tiempo() {
        return minutos / 60 + ":" + String.format("%02d", minutos % 60);
    }

    @Override
    public String toString() {
        return "♪ " + nombre + " - " + sector + " (" + tiempo() + ")";
    }
}
