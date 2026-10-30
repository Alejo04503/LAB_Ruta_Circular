# Reflexión — Laboratorio RutaSabana

## 1. ¿Por qué no se necesita `if (actual.siguiente == null)` para avanzar? ¿Qué pasaría con `while (aux != null)`?

Porque la lista es circular: ningún enlace es `null` (salvo la lista vacía). El `siguiente` del último nodo es `primero`, así que `actual = actual.siguiente` siempre lleva a un nodo válido y no existe un "final" que haya que detectar.

Con `while (aux != null)` el recorrido nunca terminaría: como ningún nodo apunta a `null`, `aux` daría vueltas por el círculo indefinidamente (ciclo infinito). Por eso se usa `do { ... } while (aux != primero);`, que se detiene al regresar al punto de partida.

## 2. En `eliminarActual`, ¿qué falla si olvidas actualizar `primero` al eliminar el primer nodo?

`primero` quedaría apuntando a un nodo que ya fue sacado del círculo (sus vecinos ya no lo apuntan, pero él sigue apuntándolos a ellos).

Escenario: ruta A, B, C, D con `actual` en A. Se llama `eliminarActual()`: `actual` pasa a B, pero `primero` sigue en A. Luego `agregar(E)` calcula `ultimo = primero.anterior`, que es D, y enlaza E a A (nodo eliminado). El círculo real (B, C, D) queda sin E, y `tiempoTotal()` y `nombresDesdeActual()` pueden dar resultados incorrectos o no terminar, porque el `while (aux != primero)` espera encontrar un nodo (A) que ya no está en el círculo.

## 3. Complejidad

| Operación | Manual (tu lista) | Librería (LinkedList) |
|---|---|---|
| agregar | O(1) | O(1) |
| siguiente / anterior | O(1) | O(1) |
| saltar | O(n) | O(n) |
| eliminarActual | O(1) | O(1) |

Justificación:
- **agregar:** en la lista manual el último es `primero.anterior`, así que insertar solo cambia 4 enlaces. En `LinkedList`, `addLast` es O(1).
- **siguiente / anterior:** manual, mover una referencia. Librería, `removeFirst` + `addLast` (o `removeLast` + `addFirst`) son O(1) cada una.
- **saltar:** manual, se avanzan como máximo `|n % tamanio|` nodos, es decir O(n) en el peor caso (acotado por el tamaño de la lista). Librería, `Collections.rotate` recorre la lista, también O(n).
- **eliminarActual:** manual, se puentea el nodo cambiando 2 enlaces, O(1). Librería, `removeFirst` es O(1).

## 4. Circularidad "lógica" en la Parte 2: ¿qué ventaja da la librería? ¿Qué control se pierde?

**Ventaja:** el código es mucho más corto y confiable (una o dos líneas por operación), ya está probado, y no hay que manejar enlaces a mano, que es donde más se cometen errores (por ejemplo, olvidar actualizar `primero`).

**Control que se pierde:** no se manejan los nodos ni los enlaces directamente; la circularidad depende de mover elementos (rotación) en vez de moverse una referencia, y `saltar` mueve elementos en lugar de solo cambiar `actual`. Tampoco se puede conservar una referencia a un nodo concreto ni decidir cómo se almacena la estructura internamente.

## 5. "Parada prioritaria" y "cola de paradas pendientes": ¿qué estructura de `java.util` agregarías?

- **Cola de paradas pendientes:** una `Queue<Parada>` (implementada con `ArrayDeque` o `LinkedList`), porque las paradas pendientes se atienden en orden de llegada (FIFO).
- **Parada prioritaria:** una `PriorityQueue<Parada>` con un `Comparator` que defina la prioridad, porque entrega siempre primero el elemento más prioritario sin importar el orden en que se agregó.
