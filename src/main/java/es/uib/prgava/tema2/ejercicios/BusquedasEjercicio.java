package es.uib.prgava.tema2.ejercicios;

/**
 * Ejercicio 2.1.7. Búsqueda binaria con el contrato de la biblioteca.
 *
 * <p>La {@code binaria} del tema devuelve −1 cuando el elemento no está. Aquí se escribe la
 * versión de {@code java.util.Arrays.binarySearch}, que en ese caso devuelve el punto de inserción
 * codificado y es bastante más útil.
 */
public final class BusquedasEjercicio {

    private BusquedasEjercicio() { }

    /**
     * Posición de {@code buscado} en {@code datos}, o el punto de inserción codificado.
     *
     * <p>Si el elemento está, devuelve su posición. Si no está, devuelve
     * −(punto de inserción) − 1, donde el punto de inserción es el índice en el que habría que
     * insertarlo para mantener el orden. <strong>Con esta codificación, −1 significa «no está, y
     * va en la posición 0»</strong>, y ya no significa «no está» sin más.
     *
     * @param datos array <strong>ordenado</strong> de forma creciente; el método no lo comprueba,
     *        y con un array desordenado devuelve cualquier cosa sin avisar
     */
    public static int binariaConInsercion(int[] datos, int buscado) {
        int izquierda = 0;
        int derecha = datos.length - 1;
        while (izquierda <= derecha) {
            // Nunca (izquierda + derecha) / 2: con un array de mas de 2^30 elementos la suma
            // desborda el int, sale negativa, y el indice negativo revienta. El fallo estuvo nueve
            // anos en la biblioteca de Java porque hacian falta arrays enormes para verlo.
            int centro = izquierda + (derecha - izquierda) / 2;
            if (datos[centro] < buscado) {
                izquierda = centro + 1;
            } else if (datos[centro] > buscado) {
                derecha = centro - 1;
            } else {
                return centro;
            }
        }
        // Al salir, izquierda == derecha + 1, y todo lo que queda a la izquierda de 'izquierda' es
        // menor que buscado y todo lo que queda desde ella, mayor: es justo el punto de insercion.
        // El -1 de la formula existe para que el punto 0 no se confunda con «encontrado en 0».
        return -izquierda - 1;
    }

    // ----------------------------------------------------------------------------------
    // Por que no se comprueba que el array este ordenado: comprobarlo cuesta O(n), y la razon de
    // ser de la busqueda binaria es costar O(log n). Un metodo que valida su precondicion en cada
    // llamada deja de ser una busqueda binaria. La precondicion se documenta y la cumple quien llama.
    //
    // RESPUESTA al «Para pensar»: probar el contrato, no la coincidencia con la biblioteca.
    //
    // En lugar de comparar con Arrays.binarySearch, se comprueba lo que el contrato promete para
    // cada resultado r:
    //   - si r >= 0, datos[r] == buscado;
    //   - si r < 0, con p = -r - 1: 0 <= p <= datos.length, buscado no esta en el array, todos
    //     los elementos antes de p son menores que buscado y todos desde p son mayores.
    // Esa prueba admite cualquier posicion valida entre las iguales, que es lo que dice la
    // documentacion, y no falla si mañana la biblioteca cambia de implementacion. Es una prueba de
    // propiedades: se comprueba una propiedad para miles de entradas al azar, no un valor concreto.
    // ----------------------------------------------------------------------------------
}
