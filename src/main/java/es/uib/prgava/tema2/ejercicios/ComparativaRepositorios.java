package es.uib.prgava.tema2.ejercicios;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uib.prgava.tema1.monitor.Resultado;
import es.uib.prgava.tema1.monitor.Servicio;
import es.uib.prgava.tema1.monitor.ServicioTcp;

/**
 * Ejercicio 2.3.4. Las dos versiones del repositorio, comparadas contando.
 *
 * <p>Cada método reproduce una ejecución completa del monitor —en cada ronda y para cada servicio,
 * guardar un resultado y después pedir el historial de ese servicio— y devuelve cuántos resultados
 * se han examinado al responder los historiales. La misma unidad en los dos, que es lo que permite
 * dividir un número por el otro.
 *
 * <p>No hacen falta el {@code Monitor} ni las clases del repositorio: con una {@code List} en un
 * caso y un {@code HashMap} en el otro, dentro del propio método, es suficiente. Los servicios se
 * crean con {@code new ServicioTcp("s" + i, 9000 + i)}.
 */
public final class ComparativaRepositorios {

    private ComparativaRepositorios() { }

    private static List<Servicio> servicios(int rondas, int numServicios) {
        if (rondas < 1 || numServicios < 1) {
            throw new IllegalArgumentException("Hacen falta al menos una ronda y un servicio");
        }
        var lista = new ArrayList<Servicio>();
        for (int i = 0; i < numServicios; i++) {
            lista.add(new ServicioTcp("s" + i, 9000 + i));
        }
        return lista;
    }

    /**
     * Resultados examinados por la versión del Tema 1, que guarda todo en una lista y recorre
     * los {@code N} resultados en cada consulta.
     *
     * @throws IllegalArgumentException si {@code rondas} o {@code numServicios} son menores que 1
     */
    public static long examinadosConListaUnica(int rondas, int numServicios) {
        var servicios = servicios(rondas, numServicios);
        var datos = new ArrayList<Resultado>();
        long examinados = 0;
        for (int ronda = 0; ronda < rondas; ronda++) {
            for (Servicio servicio : servicios) {
                datos.add(Resultado.activo(servicio, Duration.ofMillis(10)));
                // El historial de la lista unica: un equals contra el servicio por cada resultado.
                var suyos = new ArrayList<Resultado>();
                for (Resultado resultado : datos) {
                    examinados++;
                    if (resultado.servicio().equals(servicio)) {
                        suyos.add(resultado);
                    }
                }
            }
        }
        return examinados;
    }

    /**
     * Resultados examinados por la versión con índice por servicio, que solo copia los del
     * servicio preguntado.
     *
     * @throws IllegalArgumentException si {@code rondas} o {@code numServicios} son menores que 1
     */
    public static long examinadosConIndice(int rondas, int numServicios) {
        var servicios = servicios(rondas, numServicios);
        Map<Servicio, List<Resultado>> porServicio = new HashMap<>();
        long examinados = 0;
        for (int ronda = 0; ronda < rondas; ronda++) {
            for (Servicio servicio : servicios) {
                porServicio.computeIfAbsent(servicio, s -> new ArrayList<>())
                        .add(Resultado.activo(servicio, Duration.ofMillis(10)));
                // El historial con indice: encontrar la lista es O(1) esperado y no examina nada;
                // lo que se examina es lo que se copia, que son solo los de este servicio.
                List<Resultado> suyos = porServicio.get(servicio);
                examinados += suyos.size();
                List.copyOf(suyos);
            }
        }
        return examinados;
    }

    // ----------------------------------------------------------------------------------
    // LAS FORMULAS, con r rondas, s servicios y N = r * s resultados al final.
    //   Lista unica: en la consulta k-esima hay k resultados y se recorren todos, asi que
    //                1 + 2 + ... + N = N(N + 1) / 2, que es O(r^2 s^2).
    //   Indice:      en la ronda j, cada servicio tiene j resultados y se copian los j; son s
    //                servicios por ronda, asi que s * (1 + 2 + ... + r) = s r(r + 1) / 2,
    //                que es O(r^2 s).
    //
    // CONCLUSION. La operacion dominante era historial: se llama una vez por comprobacion y, en
    // la lista unica, examina todo lo guardado. El indice elimina el factor s —el cociente tiende
    // a s, 20 en la tabla del enunciado—, porque cada consulta deja de mirar los resultados de los
    // demas servicios. Pero sigue siendo cuadratico en las rondas: historial devuelve el historial
    // ENTERO, que crece con cada ronda, y se pide en cada ronda. El problema de fondo no es como se
    // busca, sino que se pide y se copia todo cada vez. Se arregla cambiando la pregunta —pedir
    // solo los ultimos k resultados, que es lo que necesita una politica de alerta— y no la
    // estructura.
    //
    // RESPUESTA al «Para pensar»: ¿que se podia saber antes de programarlo?
    //
    // Todo lo que dice la tabla. Las dos formulas salen de contar en papel, como arriba, y de ellas
    // se deduce que el cociente tiende a s y que las dos columnas son cuadraticas en r. El tema lo
    // programo y lo midio igualmente por dos razones: porque una formula solo es tan buena como el
    // modelo del que sale —y medir es la forma de comprobar que el modelo no se ha dejado nada, por
    // ejemplo un coste oculto en el equals o en la copia—, y porque un numero medido convence a
    // quien no ha seguido la cuenta. La medicion no sustituye al analisis: lo confirma.
    // ----------------------------------------------------------------------------------
}
