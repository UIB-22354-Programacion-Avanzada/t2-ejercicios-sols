package es.uib.prgava.tema2.ejercicios;

import es.uib.prgava.tema2.eficiencia.Recuento;

/**
 * Ejercicio 2.1.1. El mayor y el segundo mayor de un array, en un solo recorrido.
 *
 * <p>La solución evidente —buscar el máximo y después recorrer otra vez buscando el mayor de los
 * restantes— hace 2n iteraciones. Esta hace n llevando dos variables a la vez. Las dos son O(n): lo
 * que se gana es la constante, y el ejercicio existe para que esa diferencia se elija a conciencia.
 */
public final class RecorridosEjercicio {

    private RecorridosEjercicio() { }

    /** Iteraciones de la última llamada. Un atributo de clase porque el método es de clase. */
    private static long ultimas;

    /**
     * El mayor y el segundo mayor elemento de {@code datos}, en ese orden.
     *
     * <p>El segundo mayor cuenta las repeticiones: en {@code {3, 17, 9, 17, 2}} el mayor es 17 y
     * el segundo mayor también.
     *
     * @param datos array de al menos dos elementos
     * @return un array de dos posiciones, {@code {maximo, segundo}}
     * @throws IllegalArgumentException si {@code datos} es {@code null} o tiene menos de dos
     *         elementos
     */
    public static int[] maximoYSegundo(int[] datos) {
        if (datos == null || datos.length < 2) {
            throw new IllegalArgumentException("Hacen falta al menos dos elementos");
        }
        // MIN_VALUE como punto de partida: cualquier elemento del array es >= que el, asi que el
        // primero ocupa el maximo y el segundo cae en uno de los dos casos sin tratamiento especial.
        int maximo = Integer.MIN_VALUE;
        int segundo = Integer.MIN_VALUE;
        long operaciones = 0;
        for (int dato : datos) {
            operaciones++;
            // El orden de las dos comprobaciones importa: primero se mira si supera al maximo,
            // porque en ese caso el maximo viejo baja a segundo. Al reves, un elemento mayor que
            // los dos acabaria como segundo y se perderia el maximo.
            if (dato > maximo) {
                segundo = maximo;
                maximo = dato;
            } else if (dato > segundo) {
                // Llega aqui tambien un dato IGUAL al maximo (no lo supera), y por eso un 17
                // repetido ocupa el segundo puesto: el segundo mayor cuenta las repeticiones.
                segundo = dato;
            }
        }
        Recuento.anunciar("maximoYSegundo", datos.length, operaciones, "n");
        ultimas = operaciones;
        return new int[] {maximo, segundo};
    }

    /** Iteraciones que ejecutó la última llamada a {@link #maximoYSegundo(int[])}. */
    public static long ultimasOperaciones() {
        return ultimas;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: ¿cuando entregarias la version de dos recorridos?
    //
    // Cuando la claridad valga mas que un factor 2 que nadie va a notar. Las dos versiones son
    // O(n), y para arrays de miles de elementos la diferencia son microsegundos. La de dos
    // recorridos se convence uno de que es correcta en diez segundos; la de uno exige pensar el
    // orden de los dos if y el caso de los repetidos, que es donde estan los errores. Si el metodo
    // no esta en un camino caliente medido, la version facil de leer es la mejor. Si lo esta
    // —millones de llamadas por segundo—, la de un recorrido, con un comentario como los de arriba.
    // ----------------------------------------------------------------------------------
}
