package es.uib.prgava.tema2.ejercicios;

/**
 * Ejercicio 2.1.3. Los testigos de una cota para f(n) = 3n² + 50n + 200.
 *
 * <p>Una cota asintótica no es una impresión: es una afirmación con testigos. Para la cota
 * superior hay que exhibir {@code c} y {@code n0} con f(n) ≤ c·n² para todo n ≥ n0, y para la
 * inferior, {@code c} y {@code n0} con f(n) ≥ c·n². Lo que se entrega aquí son esos cuatro
 * números, y las pruebas comprueban que cumplen la definición.
 */
public final class CotasEjercicio {

    private CotasEjercicio() { }

    /**
     * La función del enunciado, f(n) = 3n² + 50n + 200.
     *
     * @param n tamaño de la entrada, no negativo
     */
    public static long f(long n) {
        return 3 * n * n + 50 * n + 200;
    }

    /**
     * La constante c de la cota superior: f(n) ≤ c·n² para todo n ≥ n0Superior().
     *
     * <p>Con c = 4, la desigualdad 3n² + 50n + 200 ≤ 4n² equivale a n² − 50n − 200 ≥ 0, cuya raíz
     * positiva es 25 + √825 ≈ 53,72. A partir de 54 se cumple, y en 53 todavía no: f(53) = 11.277
     * y 4·53² = 11.236.
     */
    public static long constanteCSuperior() {
        return 4;
    }

    /** El n0 mínimo para c = 4. */
    public static long n0Superior() {
        return 54;
    }

    /**
     * La constante c de la cota inferior: f(n) ≥ c·n² para todo n ≥ n0Inferior().
     *
     * <p>Los tres sumandos son no negativos para n ≥ 0, así que f(n) ≥ 3n² sin más: basta quitar
     * los dos últimos. No hacía falta buscar nada fino.
     */
    public static long constanteCInferior() {
        return 3;
    }

    /** Con c = 3 la desigualdad se cumple desde n = 0. */
    public static long n0Inferior() {
        return 0;
    }

    // ----------------------------------------------------------------------------------
    // CONCLUSION. Con las dos cotas, f es O(n^2) y Omega(n^2), y por tanto Theta(n^2): crece
    // exactamente como n^2, ni mas deprisa ni mas despacio.
    //
    // No hacia falta buscar c1 y c2 a la vez porque Theta es, por definicion, la conjuncion de las
    // otras dos: f es Theta(g) si y solo si es O(g) y Omega(g). Cada una se demuestra por separado,
    // con su propia constante y su propio n0, y para la Theta basta tomar el mayor de los dos n0.
    //
    // RESPUESTA a la pregunta del paso 4: ¿por que c = 4 con n0 = 54 y c = 53 con n0 = 3 son las
    // dos correctas? Porque la definicion solo pide que EXISTAN una constante y un umbral; no pide
    // los menores. Las dos clausulas juegan papeles distintos: c absorbe los factores constantes y
    // n0 permite ignorar los tamanos pequenos, y una se puede cambiar por la otra. Lo que la cota
    // afirma es como crece f, y eso no depende de que pareja se elija.
    //
    // RESPUESTA al «Para pensar»: comprobar hasta cien mil no demuestra nada para n = cien mil y
    // uno. Para todo n hace falta una DEMOSTRACION: el razonamiento algebraico de arriba (las
    // raices de la inecuacion de segundo grado), escrito en papel o, si se quiere que lo compruebe
    // una maquina, en un asistente de demostracion como Lean, Coq o Isabelle. Eso no es una prueba
    // unitaria: una prueba unitaria comprueba ejemplos, y una demostracion cubre todos los casos.
    // Las pruebas de este ejercicio son una red de seguridad contra erratas, no la justificacion.
    // ----------------------------------------------------------------------------------
}
