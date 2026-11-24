package es.uib.prgava.tema2.ejercicios;

/**
 * Ejercicio 2.1.5. Peor caso, mejor caso y caso medio de la búsqueda lineal, medidos.
 *
 * <p>Los tres casos son tres resúmenes distintos del conjunto de costes de las entradas de un
 * tamaño dado, y aquí se calculan recorriendo las entradas de verdad en lugar de citar la fórmula.
 * La hipótesis del caso medio —el elemento está y todas las posiciones son igual de probables— es
 * parte del resultado, y se traduce en promediar las n búsquedas posibles.
 *
 * <p>Para un tamaño n se usa siempre el array {0, 1, 2, …, n−1}, donde el elemento de valor i está
 * en la posición i.
 */
public final class CasosEjercicio {

    private CasosEjercicio() { }

    /**
     * Comparaciones que hace una búsqueda lineal de {@code buscado} en {@code datos}.
     *
     * <p>A diferencia de {@code Busquedas.lineal}, que imprime su recuento, este método lo
     * devuelve: es lo que permite que los tres resúmenes se calculen con él. Es la misma
     * separación entre calcular e informar que el Tema 1 pide para cualquier clase.
     *
     * @return cuántas comparaciones se han hecho hasta encontrar el elemento o agotar el array
     */
    private static long comparaciones(int[] datos, int buscado) {
        long cuenta = 0;
        for (int dato : datos) {
            cuenta++;
            if (dato == buscado) {
                return cuenta;
            }
        }
        return cuenta;
    }

    /** El array {0, 1, …, n−1}. */
    private static int[] crecientes(int n) {
        int[] datos = new int[n];
        for (int i = 0; i < n; i++) {
            datos[i] = i;
        }
        return datos;
    }

    private static void validar(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El tamano tiene que ser al menos 1");
        }
    }

    /**
     * El máximo de los costes: buscar un elemento que no está.
     *
     * @throws IllegalArgumentException si {@code n} es menor que 1
     */
    public static long peorCaso(int n) {
        validar(n);
        return comparaciones(crecientes(n), -1);
    }

    /**
     * El mínimo de los costes: buscar el elemento de la primera posición.
     *
     * @throws IllegalArgumentException si {@code n} es menor que 1
     */
    public static long mejorCaso(int n) {
        validar(n);
        return comparaciones(crecientes(n), 0);
    }

    /**
     * La media de los costes de buscar cada uno de los n elementos presentes.
     *
     * @throws IllegalArgumentException si {@code n} es menor que 1
     */
    public static double casoMedio(int n) {
        validar(n);
        int[] datos = crecientes(n);
        long total = 0;
        // Se buscan los n elementos PRESENTES, uno por uno: esa es la hipotesis del caso medio.
        // Buscar el i cuesta i + 1 comparaciones, asi que la suma es n(n+1)/2 y la media (n+1)/2.
        // Quien obtiene n/2 suele estar contando desde 0, o promediando tambien la busqueda fallida.
        for (int buscado = 0; buscado < n; buscado++) {
            total += comparaciones(datos, buscado);
        }
        return (double) total / n;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: si una de cada diez busquedas es de un elemento ausente.
    //
    // El caso medio es la media ponderada de los dos tipos de busqueda: con probabilidad 9/10 el
    // elemento esta y cuesta, de media, (n+1)/2; con probabilidad 1/10 no esta y cuesta n. En
    // total, 0,9 * (n+1)/2 + 0,1 * n = 0,55n + 0,45. Sigue siendo lineal, pero el numero cambia:
    // para n = 1000, 550,45 en lugar de 500,5. El caso medio no es una propiedad del algoritmo,
    // sino del algoritmo MAS la distribucion de las entradas, y cambiar la distribucion lo cambia.
    // ----------------------------------------------------------------------------------
}
