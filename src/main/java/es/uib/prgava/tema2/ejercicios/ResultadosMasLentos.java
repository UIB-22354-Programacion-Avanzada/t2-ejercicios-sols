package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import es.uib.prgava.tema1.monitor.Resultado;

/**
 * Ejercicio 2.2.4. El montículo: los {@code k} resultados más lentos del monitor.
 *
 * <p>Una cola de prioridad saca siempre el mínimo según su comparador, así que para quedarse con
 * los mayores el comparador va invertido. Y los elementos hay que <strong>sacarlos</strong>:
 * recorrer la cola no los da en orden.
 */
public final class ResultadosMasLentos {

    private ResultadosMasLentos() { }

    /**
     * Los {@code k} resultados de mayor latencia, de mayor a menor.
     *
     * <p>Si hay menos de {@code k} resultados, devuelve todos los que haya.
     *
     * @return una lista nueva e inmutable
     * @throws IllegalArgumentException si {@code k} es menor que 1
     * @throws NullPointerException si {@code resultados} es {@code null}
     */
    public static List<Resultado> masLentos(List<Resultado> resultados, int k) {
        Objects.requireNonNull(resultados);
        if (k < 1) {
            throw new IllegalArgumentException("k tiene que ser al menos 1");
        }
        // Invertido: la cola saca el minimo segun su comparador, y aqui el «minimo» tiene que ser
        // la latencia mayor. Duration es Comparable, no hace falta pasar a milisegundos.
        var cola = new PriorityQueue<Resultado>(Comparator.comparing(Resultado::latencia).reversed());
        cola.addAll(resultados);
        var lentos = new ArrayList<Resultado>();
        // Sacar, no recorrer: el iterador de PriorityQueue recorre el array del monticulo, donde
        // solo la raiz esta en su sitio. Cada poll reorganiza el monticulo en O(log n) y devuelve
        // el siguiente de verdad. Con pocos elementos el recorrido sale ordenado por casualidad.
        while (lentos.size() < k && !cola.isEmpty()) {
            lentos.add(cola.poll());
        }
        return List.copyOf(lentos);
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: ¿cuando compensa el monticulo de tamano k?
    //
    // Cuando n es muy grande comparado con k, y sobre todo cuando los resultados no estan todos a
    // la vez en memoria. Con un monticulo de tamano k ordenado al reves (de minimo), cada resultado
    // nuevo se compara con la raiz —el peor de los k guardados— y solo entra si la supera: O(log k)
    // por elemento y memoria O(k). Para los diez peores de un millon, eso es un monticulo de diez
    // en lugar de uno de un millon.
    //
    // Y permite algo que esta version no permite: trabajar sobre un FLUJO. Los resultados del
    // monitor llegan uno a uno y para siempre; con un monticulo de tamano k se mantiene el ranking
    // al dia con memoria constante, sin guardar el historial entero. Esta version necesita la
    // lista completa antes de empezar. Si los datos ya estan en una lista y n es moderado, la
    // version sencilla basta y se lee mejor.
    // ----------------------------------------------------------------------------------
}
