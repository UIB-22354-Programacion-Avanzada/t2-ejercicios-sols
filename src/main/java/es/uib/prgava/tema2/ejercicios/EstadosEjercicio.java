package es.uib.prgava.tema2.ejercicios;

import java.util.List;

import es.uib.prgava.tema1.monitor.Estado;
import es.uib.prgava.tema1.monitor.Resultado;
import es.uib.prgava.tema2.eficiencia.Recuento;

/**
 * Ejercicio 2.1.2. Cuántos resultados hay de cada {@code Estado}, con dos bucles anidados que no
 * son cuadráticos.
 *
 * <p>El bucle interior recorre los valores del enum, que son una constante del programa y no el
 * tamaño de la entrada: de ahí que el recuento sea {@code 3n} y la clase de crecimiento, lineal.
 */
public final class EstadosEjercicio {

    private EstadosEjercicio() { }

    private static long ultimas;

    /**
     * Cuántos resultados hay de cada estado, en un array indexado por {@code Estado.ordinal()}.
     *
     * @param historial resultados que hay que clasificar; puede estar vacío
     * @return un array de {@code Estado.values().length} posiciones
     */
    public static long[] porEstado(List<Resultado> historial) {
        // Estado.values().length y no 3: si manana el enum crece, el metodo sigue siendo correcto.
        long[] cuentas = new long[Estado.values().length];
        long operaciones = 0;
        for (Resultado resultado : historial) {
            for (Estado estado : Estado.values()) {
                operaciones++;
                if (resultado.estado() == estado) {
                    cuentas[estado.ordinal()]++;
                }
                // Sin break, como pide el enunciado: asi el recuento es exactamente 3n y no
                // depende de en que estado este cada resultado.
            }
        }
        Recuento.anunciar("porEstado", historial.size(), operaciones, "3n");
        ultimas = operaciones;
        return cuentas;
    }

    // CONCLUSION: porEstado es O(n). Hace exactamente 3n iteraciones, y 3 es el numero de valores
    // del enum, una constante del programa; las constantes multiplicativas no cambian la clase.

    /** Iteraciones que ejecutó la última llamada a {@link #porEstado(List)}. */
    public static long ultimasOperaciones() {
        return ultimas;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: ¿merece la pena quitar el bucle interior?
    //
    // Si. La version sin bucle interior es cuentas[resultado.estado().ordinal()]++, una linea por
    // resultado: n iteraciones en lugar de 3n. No cambia el orden, pero es a la vez mas rapida y
    // mas corta, y no hay nada que ganar con la anidada salvo el ejercicio. Con cincuenta valores
    // la diferencia seria de 50n frente a n: sigue siendo lineal, pero cincuenta veces mas trabajo,
    // y ahi la constante ya se nota. La leccion de fondo es que «es O(n)» no cierra la discusion:
    // dice como crece, no cuanto cuesta.
    // ----------------------------------------------------------------------------------
}
