package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.uib.prgava.tema1.monitor.Resultado;

/**
 * Ejercicio 2.1.8. Agrupar los resultados por servicio sin perder el orden cronológico.
 *
 * <p>Los dos métodos tienen que devolver exactamente la misma lista: uno ordenando dos veces por
 * un criterio y el otro una sola vez por un criterio compuesto. Que coincidan es consecuencia de
 * que la ordenación de objetos en Java es estable.
 */
public final class OrdenEjercicio {

    private OrdenEjercicio() { }

    /**
     * Ordenado por servicio y, dentro de cada servicio, de más antiguo a más reciente, con dos
     * ordenaciones de un solo criterio.
     *
     * @param resultados no se modifica
     * @return una lista nueva e inmutable
     */
    public static List<Resultado> conDosPasadas(List<Resultado> resultados) {
        // Se ordena una copia: la lista que llega puede ser inmutable y, aunque no lo sea, un
        // metodo que reordena lo que le pasan sorprende a quien lo llama.
        var copia = new ArrayList<>(resultados);
        // Primero el criterio SECUNDARIO, despues el PRINCIPAL. La ultima ordenacion manda, y la
        // estabilidad conserva, dentro de cada grupo de empates, el orden que dejo la anterior.
        copia.sort(Comparator.comparing(Resultado::instante));
        copia.sort(Comparator.comparing(r -> r.servicio().nombre()));
        return List.copyOf(copia);
    }

    /**
     * El mismo resultado que {@link #conDosPasadas(List)} con un único {@code sort}.
     *
     * @param resultados no se modifica
     * @return una lista nueva e inmutable
     */
    public static List<Resultado> conUnComparador(List<Resultado> resultados) {
        var copia = new ArrayList<>(resultados);
        // Servicio es una interfaz sin orden natural: se compara por su nombre().
        copia.sort(Comparator.comparing((Resultado r) -> r.servicio().nombre())
                .thenComparing(Resultado::instante));
        return List.copyOf(copia);
    }

    // ----------------------------------------------------------------------------------
    // POR QUE COINCIDEN. List.sort sobre objetos es ESTABLE: dos elementos que el comparador
    // considera iguales salen en el mismo orden relativo en que entraron. En la segunda pasada,
    // todos los resultados de un mismo servicio empatan, y la estabilidad los deja en el orden
    // cronologico de la primera. Es exactamente lo que hace thenComparing en una sola pasada.
    //
    // Si la ordenacion no fuera estable, la segunda pasada podria desordenar los empates, y dentro
    // de cada servicio los instantes saldrian en un orden cualquiera: las dos pasadas dejarian de
    // ser equivalentes al comparador compuesto. Por eso Java garantiza estabilidad al ordenar
    // objetos (un mergesort adaptado, TimSort) y no la garantiza con primitivos, donde dos valores
    // iguales son indistinguibles y la estabilidad no significa nada.
    //
    // RESPUESTA al «Para pensar»: con cinco criterios, el comparador compuesto.
    //
    // Cinco pasadas son cinco ordenaciones completas, 5 * O(n log n), frente a una sola con un
    // comparador que en el peor caso mira los cinco campos: el mismo orden, pero unas cinco veces
    // menos trabajo. Y, sobre todo, las pasadas hay que escribirlas al reves —del criterio menos
    // importante al mas importante—, que es justo el orden en que nadie piensa: con cinco, el
    // error es casi seguro. El comparador se lee en el orden de importancia. Las pasadas solo
    // tienen sentido cuando los criterios se aplican en momentos distintos, por ejemplo, cuando el
    // usuario va pulsando columnas de una tabla.
    // ----------------------------------------------------------------------------------
}
