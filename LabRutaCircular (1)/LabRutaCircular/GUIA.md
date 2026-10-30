# Laboratorio guiado: «RutaSabana», la ruta circular

**Asignatura:** Estructura de Datos I · **Unidad 2:** Listas enlazadas
**Tiempo:** 60 minutos · **Entorno:** Java 17 + Maven (Apache NetBeans)
**Modalidad:** individual o en parejas · **Entrega:** al final de la sesión

---

## 1. El reto

Una startup te contrata para el recorrido de ruta **RutaSabana**. El cliente exige:

| Requisito del cliente | Operación |
|---|---|
| «Quiero recorrer las paradas en orden» | `agregar`, `siguiente` |
| «Al llegar a la última parada, quiero volver a la primera» | **circularidad** |
| «Quiero devolverme a la parada anterior, incluso desde la primera» | `anterior` |
| «Quiero adelantar (o devolver) varias paradas de una vez» | `saltar(n)` |
| «Si una parada sale de servicio, la elimino y continúo con la siguiente» | `eliminarActual` |
| «¿Cuánto tarda una vuelta completa?» | `tiempoTotal` |

Resolverás el mismo reto **dos veces**:

* **Parte 1 — Sin librerías:** construyes tú la lista doblemente enlazada circular con `Nodo`.
* **Parte 2 — Con librerías:** lo resuelves con `java.util.LinkedList`, `ListIterator` y `Collections`, como vimos en clase.

Las **mismas 19 pruebas automáticas** validan las dos partes.

## 2. Plan de la hora

| Minutos | Actividad |
|---|---|
| 0 – 5 | Abrir el proyecto, ejecutar `Main` y las pruebas (todo en rojo) |
| 5 – 13 | **Hito 1** · `agregar` |
| 13 – 19 | **Hito 2** · `siguiente` / `anterior` |
| 19 – 25 | **Hito 3** · `saltar(n)` |
| 25 – 35 | **Hito 4** · `eliminarActual` (el más difícil) |
| 35 – 40 | **Hito 5** · `tiempoTotal` |
| 40 – 53 | **Parte 2** · `RutaCircularLib` |
| 53 – 60 | Reflexión, captura de pruebas y entrega |

> Si te atascas más de 5 minutos en un hito, **pide ayuda al docente** y sigue. Un hito sin resolver no debe bloquear a los demás.

## 3. Cómo abrir y ejecutar

1. NetBeans → **File → Open Project…** → elige la carpeta `LabRutaCircular` (la que contiene `pom.xml`).
2. Ejecutar el recorrido: **Run Project** (F6), o en terminal: `mvn compile exec:java`
3. Ejecutar las pruebas: **Test Project** (Alt + F6), o en terminal:

```bash
mvn test -Dtest=RutaCircularManualTest     # Parte 1
mvn test -Dtest=RutaCircularLibTest        # Parte 2
```

> Si en la consola ves `?` en lugar de `▶` o `♪`, es solo un tema de codificación del terminal; no es un error de tu código.

### El semáforo

Cada prueba que pasa a verde es un hito cumplido. Al comenzar verás **3 verdes y 38 rojas**; ese es el punto de partida correcto.

| Hito | Pruebas | Qué demuestra |
|---|---|---|
| 1 | `hito1_*` (3) | La lista se construye y conserva el orden |
| 2 | `hito2_*` (6) | La lista es **circular** en ambos sentidos |
| 3 | `hito3_*` (4) | Saltos hacia adelante y atrás, con vueltas |
| 4 | `hito4_*` (5) | Eliminar sin romper el círculo |
| 5 | `hito5_*` (1) | Recorrer una vuelta completa |

## 4. Archivos del proyecto

```
src/main/java/edu/uajs/listas/ruta/
 ├─ Parada.java         ← YA ESTÁ (record: nombre, sector, minutos)
 ├─ Nodo.java            ← YA ESTÁ (dato, siguiente, anterior)
 ├─ RutaCircular.java        ← YA ESTÁ (el contrato: qué debe hacer la ruta)
 ├─ Main.java            ← YA ESTÁ (el recorrido de consola para probar)
 ├─ RutaCircularManual.java  ← ✏️ PARTE 1: completa los TODO
 └─ RutaCircularLib.java     ← ✏️ PARTE 2: completa los TODO
src/test/java/...         ← NO MODIFICAR (las pruebas)
```

---

## 5. PARTE 1 — Sin librerías (35 min)

Abre `RutaCircularManual.java`. La estructura es una **lista doblemente enlazada circular** con dos referencias:

```
          ┌────────────────────────────────────────────┐
          ▼                                            │
 primero → [A] ⇄ [B] ⇄ [C] ⇄ [D] ─────────────────────┘
            ▲
          actual   (la parada en la que se encuentra el bus)
```

* No hay `null` en ningún enlace (salvo la lista vacía).
* El **último** nodo siempre es `primero.anterior`.

### Hito 1 · `agregar` (8 min)

El caso «lista vacía» ya está resuelto: **léelo primero**, el nodo se apunta a sí mismo. Tú resuelves el caso general: insertar `nuevo` entre `ultimo` y `primero`.

```
ANTES:    ultimo ⇄ primero
DESPUÉS:  ultimo ⇄ nuevo ⇄ primero
```

Son **4 enlaces** (TODO 1.1 a 1.4). Dibuja el diagrama en papel antes de escribir.
✅ Debes ver en verde: `hito1_*`

### Hito 2 · `siguiente` y `anterior` (6 min)

Es muy corto: mover `actual` un nodo y devolver su parada. La pregunta clave es **¿por qué NO necesitas preguntar si llegaste al final?** (Anótala en tu reflexión.)
✅ Debes ver en verde: `hito2_*`. Prueba en `Main` con `n` y `p`: ¡retrocede desde la primera!

### Hito 3 · `saltar(n)` (6 min)

* `n > 0` adelanta, `n < 0` retrocede.
* Si `n` es mayor que el tamaño, das vueltas completas inútiles: reduce con `n = n % tamanio`.

✅ Debes ver en verde: `hito3_*`. Prueba en `Main` con `f` y `b`.

### Hito 4 · `eliminarActual` (10 min) ⚠️

Es el hito donde más se rompen los círculos. Sigue los TODO en orden y piensa en tres casos:

| Caso | ¿Qué cuidar? |
|---|---|
| Eliminar un nodo «del medio» | Puentear `previo ⇄ proximo` |
| Eliminar `primero` | `primero` no puede quedar apuntando a un nodo eliminado |
| Eliminar el único nodo | Ya resuelto: lista vacía |

```
ANTES:    previo ⇄ [actual] ⇄ proximo
DESPUÉS:  previo ⇄ proximo         y la parada actual pasa a ser 'proximo'
```

✅ Debes ver en verde: `hito4_*`

### Hito 5 · `tiempoTotal` (5 min)

Recorre **una sola vuelta** con el patrón `do { ... } while (aux != primero);`. Observa que `nombresDesdeActual()` (ya resuelto) usa el mismo patrón.
✅ Debes ver en verde: `hito5_*` → **¡los 19 en verde con `RutaCircularManualTest`!**

---

## 6. PARTE 2 — Con librerías (13 min)

Abre `RutaCircularLib.java`. Java **no trae** una lista circular, así que, como en clase, damos comportamiento circular a un `LinkedList` mediante **rotación**:

| Operación | Idea | Método |
|---|---|---|
| Parada actual | el primero de la cola | `peekFirst()` |
| `siguiente` | el primero pasa al final | `removeFirst()` + `addLast()` |
| `anterior` | el último pasa al inicio | `removeLast()` + `addFirst()` |
| `saltar(n)` | rotar la cola | `Collections.rotate(cola, ...)` |
| `eliminarActual` | quitar el primero | `removeFirst()` |

Completa los TODO `L1` a `L7`. Notarás que lo que antes eran decenas de líneas ahora son una o dos.

✅ Ejecuta `mvn test -Dtest=RutaCircularLibTest`: las mismas 19 pruebas deben pasar.

### Retos extra (solo si terminaste antes)

* `irA(String nombre)`: saltar directo a una parada por su nombre (usa `ListIterator`).
* `reordenar(long semilla)`: recorrido alternativo con `Collections.shuffle`.

(Hay 3 pruebas extra en `RutaCircularLibTest` que se ponen en verde al resolverlos.)

---

## 7. Reflexión (responde en un archivo `REFLEXION.md` en la raíz del proyecto)

1. En la Parte 1, ¿por qué no se necesita `if (actual.siguiente == null)` para avanzar? ¿Qué pasaría con un `while (aux != null)` al recorrer esta lista?
2. En `eliminarActual`, ¿qué falla si olvidas actualizar `primero` cuando eliminas el primer nodo? Describe un escenario concreto.
3. Completa la tabla (justifica con lo visto en clase):

| Operación | Manual (tu lista) | Librería (`LinkedList`) |
|---|---|---|
| `agregar` | O(?) | O(?) |
| `siguiente` / `anterior` | O(?) | O(?) |
| `saltar(n)` | O(?) | O(?) |
| `eliminarActual` | O(?) | O(?) |

4. En la Parte 2 la circularidad es «lógica»: el último nodo interno sigue apuntando a `null`. ¿Qué ventaja da usar la librería? ¿Qué control pierdes frente a tu lista manual?
5. El cliente ahora pide gestionar una «parada prioritaria» y una «cola de paradas pendientes». ¿Qué estructura de `java.util` agregarías y por qué?

## 8. Entrega

Sube al aula virtual (Moodle) **un solo archivo `.zip`** que contenga:

* La carpeta `LabRutaCircular` **sin** la carpeta `target/`.
* Una **captura de pantalla** de las pruebas: `RutaCircularManualTest` y `RutaCircularLibTest` (con el conteo de verdes).
* El archivo `REFLEXION.md`.

## 9. Rúbrica (sobre 5.0)

| Criterio | Peso |
|---|---|
| Parte 1: hitos 1–5 (proporción de pruebas en verde de `RutaCircularManualTest`) | 2.0 |
| Parte 2: `RutaCircularLib` (proporción de pruebas en verde de `RutaCircularLibTest`) | 1.5 |
| Calidad del código: legible, sin código muerto, nombres claros, comentarios útiles | 0.5 |
| Reflexión: respuestas correctas y argumentadas (tabla de complejidad incluida) | 1.0 |
| **Bonus** retos extra resueltos (`irA`, `reordenar`) | +0.3 |

> **Integridad académica:** se espera que cada pareja entienda y pueda explicar su código. El docente puede pedir que expliques cualquier hito en vivo.
