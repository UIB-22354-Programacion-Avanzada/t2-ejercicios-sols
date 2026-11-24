package es.uib.prgava.tema2.ejercicios;

import java.util.HashSet;
import java.util.Set;

import es.uib.prgava.tema2.eficiencia.Recuento;

/**
 * Ejercicio 2.3.1. El mismo problema que {@code Conteos.hayRepetido}, con otro orden.
 *
 * <p>La versión del tema compara todos los pares y es cuadrática. Esta mete cada elemento en un
 * conjunto y pregunta si ya estaba, y es lineal: el cambio no está en el bucle, está en la
 * estructura.
 *
 * <p>Sigue el convenio de recuento de la página de ejercicios: anuncia con {@code Recuento} y deja
 * el número en {@link #ultimasOperaciones()}.
 */
public final class RepetidosEjercicio {

    private RepetidosEjercicio() { }

    private static long ultimas;

    /**
     * ¿Hay algún valor repetido en {@code datos}?
     *
     * @throws IllegalArgumentException si {@code datos} es {@code null}
     */
    public static boolean hayRepetido(int[] datos) {
        if (datos == null) {
            throw new IllegalArgumentException("El array no puede ser null");
        }
        Set<Integer> vistos = new HashSet<>();
        long operaciones = 0;
        for (int dato : datos) {
            operaciones++;
            // add devuelve false si el elemento ya estaba: una sola consulta a la tabla hace las
            // dos cosas, comprobar e insertar. contains seguido de add la consultaria dos veces.
            if (!vistos.add(dato)) {
                Recuento.anunciar("conConjunto", datos.length, operaciones, "n como maximo");
                ultimas = operaciones;
                return true;
            }
        }
        Recuento.anunciar("conConjunto", datos.length, operaciones, "n");
        ultimas = operaciones;
        return false;
    }

    /** Iteraciones que ejecutó la última llamada a {@link #hayRepetido(int[])}. */
    public static long ultimasOperaciones() {
        return ultimas;
    }

    // ----------------------------------------------------------------------------------
    // LOS SEIS NUMEROS, con los tres arrays de 2000 elementos de DemoConteos:
    //
    //   el array de 2000 elementos      Conteos.hayRepetido   RepetidosEjercicio.hayRepetido
    //   sin repetido                          1.999.000                 2.000
    //   con el repetido en medio              1.498.501                 1.001
    //   con el repetido al principio                  1                     2
    //
    // CONCLUSION. Cambiar la estructura ha cambiado el ORDEN, no la constante: de n(n-1)/2 a n,
    // casi mil veces menos trabajo con 2000 elementos, y la distancia crece con n. La tercera fila
    // no lo desmiente: es el mejor caso de la cuadratica, que encuentra el repetido en su primera
    // comparacion, y no dice nada sobre como crece el coste. El precio es memoria O(n) para el
    // conjunto, y el coste O(1) de cada add es esperado, no garantizado.
    //
    // RESPUESTA al «Para pensar»: ¿cuando preferirias la version lenta?
    //
    // Con arrays muy pequenos —unas decenas de elementos—, donde n^2 sigue siendo poco y la version
    // con conjunto paga por cada elemento el boxing a Integer, el calculo del hash y la reserva de
    // la tabla: la cuadratica puede ser mas rapida en la practica. Y cuando la memoria es lo
    // escaso, porque la cuadratica no gasta nada extra. Para arrays de miles de elementos no hay
    // discusion. Y si se puede ordenar el array, hay una tercera via intermedia: O(n log n) y
    // comparar vecinos, sin memoria adicional.
    // ----------------------------------------------------------------------------------
}
