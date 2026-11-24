package es.uib.prgava.tema2.ejercicios;

import java.util.function.LongUnaryOperator;

/**
 * Ejercicio 2.1.4. El punto de cruce de dos funciones de coste.
 *
 * <p>Decir que un algoritmo es más rápido que otro sin decir para qué tamaño es una frase
 * incompleta. Este método contesta la pregunta que sí tiene respuesta: a partir de qué tamaño uno
 * hace menos trabajo que el otro.
 */
public final class CruceEjercicio {

    private CruceEjercicio() { }

    /**
     * El menor n ≥ 1 para el que {@code coste1(n) < coste2(n)}.
     *
     * @param coste1 función de coste del primer algoritmo
     * @param coste2 función de coste del segundo
     * @param limite último tamaño que se examina, incluido
     * @return el primer n donde gana {@code coste1}, o −1 si no gana en ningún n ≤ {@code limite}
     * @throws IllegalArgumentException si {@code limite} es menor que 1 o si alguna función es
     *         {@code null}
     */
    public static int primerNDondeGana(LongUnaryOperator coste1, LongUnaryOperator coste2, int limite) {
        // IllegalArgumentException tambien para null, como dice el contrato: aqui un null es un
        // argumento sin sentido, igual que un limite negativo, y conviene tratarlos igual.
        if (coste1 == null || coste2 == null || limite < 1) {
            throw new IllegalArgumentException("Hacen falta dos funciones y un limite de al menos 1");
        }
        for (int n = 1; n <= limite; n++) {
            // < estricto: en un empate todavia no gana ninguno.
            if (coste1.applyAsLong(n) < coste2.applyAsLong(n)) {
                return n;
            }
        }
        return -1;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: con n*n frente a 2*n devuelve 1, y eso no dice nada util.
    //
    // El metodo encuentra el PRIMER tamano en el que gana coste1, y ese tamano puede no tener nada
    // que ver con los tamanos que el programa va a manejar: n*n gana en n = 1, empata en n = 2 y
    // pierde para siempre a partir de 3. Como criterio para elegir algoritmo, el primer cruce es
    // casi inutil; lo que importa es quien gana en el rango de tamanos real, y si hay un ultimo
    // cruce a partir del cual el resultado ya no cambia. Para decidir hay que mirar el
    // comportamiento a la derecha del ultimo cruce, que es justo lo que describe la notacion
    // asintotica, y comprobar si los tamanos reales caen a ese lado.
    // ----------------------------------------------------------------------------------
}
