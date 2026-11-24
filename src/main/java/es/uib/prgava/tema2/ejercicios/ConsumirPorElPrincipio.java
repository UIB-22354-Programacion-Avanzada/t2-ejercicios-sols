package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Ejercicio 2.3.2. Consumir por el principio: el extremo equivocado de un {@code ArrayList}.
 *
 * <p>Los dos métodos hacen lo mismo y devuelven lo mismo. Lo que cambia es el trabajo: con
 * {@code remove(0)} cada extracción desplaza todo lo que queda, y con {@code pollFirst} sobre un
 * array circular no se desplaza nada.
 */
public final class ConsumirPorElPrincipio {

    private ConsumirPorElPrincipio() { }

    private static long ultimos;

    private static void validar(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n tiene que ser al menos 1");
        }
    }

    /**
     * Construye los números de 1 a {@code n} en un {@code ArrayList} y los consume con
     * {@code remove(0)}, contando los desplazamientos.
     *
     * @return lo consumido, en el orden en que salió; lista nueva e inmutable
     * @throws IllegalArgumentException si {@code n} es menor que 1
     */
    public static List<Integer> conLista(int n) {
        validar(n);
        List<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            lista.add(i);
        }
        List<Integer> consumidos = new ArrayList<>();
        long desplazamientos = 0;
        while (!lista.isEmpty()) {
            // remove(0) mueve una posicion a la izquierda todos los que quedan detras del primero:
            // size() - 1 elementos. Sumados para todas las extracciones dan n(n-1)/2.
            desplazamientos += lista.size() - 1;
            consumidos.add(lista.remove(0));
        }
        ultimos = desplazamientos;
        return List.copyOf(consumidos);
    }

    /**
     * Lo mismo con un {@code ArrayDeque} declarado como {@code Deque} y consumido con
     * {@code pollFirst}.
     *
     * @return lo consumido, en el orden en que salió; lista nueva e inmutable
     * @throws IllegalArgumentException si {@code n} es menor que 1
     */
    public static List<Integer> conDeque(int n) {
        validar(n);
        Deque<Integer> cola = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            cola.addLast(i);
        }
        List<Integer> consumidos = new ArrayList<>();
        Integer siguiente;
        // pollFirst devuelve null al vaciarse, y ese null es la condicion de salida: vaciarse es
        // lo normal aqui, asi que se usa la pareja que devuelve un valor especial y no la que lanza.
        // Un array circular solo mueve el indice de la cabeza: nada que desplazar, O(1) por elemento.
        while ((siguiente = cola.pollFirst()) != null) {
            consumidos.add(siguiente);
        }
        return List.copyOf(consumidos);
    }

    /** Desplazamientos que costó la última llamada a {@link #conLista(int)}. */
    public static long ultimosDesplazamientos() {
        return ultimos;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: ¿por que ArrayDeque y no LinkedList, si las dos son O(1)?
    //
    // Porque la notacion O esconde la constante, y aqui la constante es muy distinta. LinkedList
    // crea un nodo por elemento —un objeto con el valor y dos referencias, unas decenas de bytes
    // de mas—, cada insercion es una reserva de memoria y cada extraccion deja basura para el
    // recolector, y los nodos quedan dispersos por la memoria, de modo que recorrerlos falla en la
    // cache del procesador. ArrayDeque guarda los elementos en un array contiguo: menos memoria,
    // casi ninguna reserva y accesos que la cache aprovecha. La propia documentacion de Java dice
    // que ArrayDeque probablemente sera mas rapida que LinkedList usada como cola.
    // ----------------------------------------------------------------------------------
}
