package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Ejercicio 2.2.1. El conjunto: «¿lo he visto ya?» en tiempo constante esperado.
 *
 * <p>Las tres preguntas que contesta esta clase se podrían contestar con una lista y
 * {@code contains}, y costarían un recorrido cada vez. Con un conjunto cuestan una consulta.
 */
public final class PalabrasUnicas {

    private PalabrasUnicas() { }

    /**
     * Cuántas palabras distintas hay en la lista.
     *
     * <p>La comparación es exacta, sin normalizar: {@code "Sol"} y {@code "sol"} son dos palabras.
     *
     * @throws NullPointerException si {@code palabras} es {@code null}
     */
    public static int cuantasDistintas(List<String> palabras) {
        // El constructor de copia mete todos los elementos, y el conjunto descarta los repetidos:
        // n inserciones de coste O(1) esperado. Con una lista y contains serian O(n^2).
        return new HashSet<>(Objects.requireNonNull(palabras)).size();
    }

    /**
     * Las palabras distintas, en el orden en que aparecen por primera vez.
     *
     * @return una lista nueva e inmutable
     * @throws NullPointerException si {@code palabras} es {@code null}
     */
    public static List<String> distintasEnOrdenDeAparicion(List<String> palabras) {
        // LinkedHashSet recuerda el orden de insercion, y como un repetido no se vuelve a
        // insertar, la primera aparicion es la que fija su sitio. HashSet no promete orden alguno.
        return List.copyOf(new LinkedHashSet<>(Objects.requireNonNull(palabras)));
    }

    /**
     * Las palabras de {@code minimo} letras o más, en el mismo orden y con las repeticiones.
     *
     * <p>Es un filtro, no una deduplicación: si una palabra larga aparece tres veces, sale tres
     * veces.
     *
     * @return una lista nueva e inmutable
     * @throws IllegalArgumentException si {@code minimo} es menor que 1
     * @throws NullPointerException si {@code palabras} es {@code null}
     */
    public static List<String> sinLasCortas(List<String> palabras, int minimo) {
        Objects.requireNonNull(palabras);
        if (minimo < 1) {
            throw new IllegalArgumentException("El minimo tiene que ser al menos 1");
        }
        var copia = new ArrayList<>(palabras);
        // removeIf recorre la lista con su propio iterador y quita lo que cumple la condicion
        // sin romperlo, y ademas lo hace en una sola pasada: O(n).
        copia.removeIf(palabra -> palabra.length() < minimo);
        return List.copyOf(copia);
    }

    /**
     * La versión que no funciona, para ejecutarla antes de arreglar nada.
     *
     * <p>No la modifiques: es parte del enunciado. Quita elementos de la lista mientras la
     * recorre, que es exactamente lo que el iterador no permite.
     */
    public static List<String> sinLasCortasRoto(List<String> palabras, int minimo) {
        var copia = new ArrayList<>(palabras);
        for (String palabra : copia) {
            if (palabra.length() < minimo) {
                copia.remove(palabra);
            }
        }
        return copia;
    }

    // ----------------------------------------------------------------------------------
    // QUE PASA CON sinLasCortasRoto. El for mejorado usa un iterador, y el iterador de ArrayList
    // recuerda cuantas modificaciones tenia la lista cuando se creo. copia.remove la modifica por
    // detras del iterador, y en la siguiente llamada a next() el iterador lo detecta y lanza
    // ConcurrentModificationException. Con la lista del ejemplo falla en cuanto quita "sol".
    //
    // Y hay un caso peor: si lo que se quita es el penultimo elemento, hasNext() ya devuelve
    // false, el bucle termina sin excepcion y el ultimo elemento no se examina nunca. El metodo
    // devuelve un resultado incorrecto en silencio. Ademas, remove(Object) quita la PRIMERA
    // aparicion, no la que se esta mirando. Por eso no se arregla con cuidado, se arregla con
    // removeIf, o con el iterador explicito y su propio remove().
    //
    // RESPUESTA al «Para pensar»: ¿por que no LinkedHashSet por omision?
    //
    // Porque cuesta mas y casi nunca hace falta. Cada entrada lleva dos referencias mas —la lista
    // doblemente enlazada que recuerda el orden— y cada insercion y borrado las mantiene: mas
    // memoria y algo mas de tiempo, con el mismo orden de coste. La regla es la de siempre: pedir
    // la promesa mas debil que baste. Si el orden importa, LinkedHashSet; si no, HashSet; y si lo
    // que importa es el orden de los valores, no el de llegada, TreeSet.
    // ----------------------------------------------------------------------------------
}
