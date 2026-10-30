package edu.uajs.listas.ruta;

import java.util.Scanner;

/**
 * Reproductor de consola "RutaSabana". YA ESTÁ COMPLETO: no hay que modificarlo.
 * Sirve para PROBAR visualmente lo que van implementando.
 *
 *   mvn compile exec:java            (o Run Project en NetBeans)
 *   Argumento opcional: "manual" o "lib" para elegir la implementación.
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String modo = args.length > 0 ? args[0] : null;
        if (modo == null) {
            System.out.print("Implementación (1 = manual, 2 = librería): ");
            modo = in.nextLine().trim().equals("2") ? "lib" : "manual";
        }
        RutaCircular ruta = modo.equals("lib") ? new RutaCircularLib() : new RutaCircularManual();
        System.out.println("Usando: " + ruta.getClass().getSimpleName());

        // Catálogo inicial para el cliente
        try {
            ruta.agregar(new Parada("Campus Universitario", "Zona Universitaria", 185));
            ruta.agregar(new Parada("Plaza Central", "Centro Histórico", 212));
            ruta.agregar(new Parada("Terminal Circular", "Terminal Norte", 240));
            ruta.agregar(new Parada("Avenida de la Pila", "Mercado Central", 198));
            ruta.agregar(new Parada("Glorieta del Nodo", "Barrio Los Nodos", 176));
            ruta.agregar(new Parada("Estación Memoria", "Parque Tecnológico", 224));
        } catch (UnsupportedOperationException ex) {
            System.out.println("⚠ Aún no implementas agregar(): " + ex.getMessage());
            return;
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\n══════ RutaSabana ══════");
            if (ruta.tamanio() > 0) {
                System.out.println("Parada actual: " + ruta.actual());
                String tiempo;
                try {
                    tiempo = ruta.tiempoTotal() + " s";
                } catch (UnsupportedOperationException ex) {
                    tiempo = "(pendiente: Hito 5)";
                }
                System.out.println("Paradaes: " + ruta.tamanio() + " | Tiempo total: " + tiempo);
            }
            System.out.println("[n] siguiente  [p] anterior  [f] adelantar N  [b] retroceder N");
            System.out.println("[e] eliminar la actual  [a] agregar  [l] ver cola  [q] salir");
            System.out.print("> ");
            String linea = in.nextLine().trim().toLowerCase();

            try {
                switch (linea) {
                    case "n" -> ruta.siguiente();
                    case "p" -> ruta.anterior();
                    case "f" -> {
                        System.out.print("¿Cuántas paradas? ");
                        ruta.saltar(Integer.parseInt(in.nextLine().trim()));
                    }
                    case "b" -> {
                        System.out.print("¿Cuántas paradas? ");
                        ruta.saltar(-Integer.parseInt(in.nextLine().trim()));
                    }
                    case "e" -> ruta.eliminarActual();
                    case "a" -> {
                        System.out.print("Nombre: ");
                        String t = in.nextLine().trim();
                        System.out.print("Sector: ");
                        String ar = in.nextLine().trim();
                        System.out.print("Minutos: ");
                        int s = Integer.parseInt(in.nextLine().trim());
                        ruta.agregar(new Parada(t, ar, s));
                    }
                    case "l" -> ruta.mostrar();
                    case "q" -> salir = true;
                    default -> System.out.println("Comando no reconocido");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debes escribir un número entero.");
            } catch (UnsupportedOperationException ex) {
                System.out.println("⚠ Aún no implementas esto: " + ex.getMessage());
            }
        }
        System.out.println("¡Hasta la próxima!");
    }
}
