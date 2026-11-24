package es.uib.prgava.tema2.monitor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import es.uib.prgava.tema1.monitor.RepositorioResultados;
import es.uib.prgava.tema1.monitor.Resultado;
import es.uib.prgava.tema1.monitor.Servicio;
import es.uib.prgava.tema1.monitor.ServicioTcp;

/**
 * Ejercicio 2.1.9. El repositorio del Tema 1, contando las comparaciones de {@code historial}.
 *
 * <p>Hace exactamente lo mismo que {@code RepositorioEnMemoria}: guarda los resultados en una
 * lista y, para responder el historial de un servicio, recorre todos los resultados comparando el
 * servicio de cada uno. Lo único que añade es el recuento, y con él se reproduce la columna de
 * comparaciones de la tabla de 2.1.6 sin depender de la máquina.
 */
public final class RepositorioContador implements RepositorioResultados {

    private final List<Resultado> datos = new ArrayList<>();
    private long comparaciones;

    @Override
    public void guardar(Resultado resultado) {
        datos.add(resultado);
    }

    /**
     * Historial de un servicio, del más antiguo al más reciente.
     *
     * <p>Suma al contador una comparación por cada resultado examinado, es decir una por cada
     * vuelta del bucle, acumuladas entre llamadas.
     */
    @Override
    public List<Resultado> historial(Servicio servicio) {
        var suyos = new ArrayList<Resultado>();
        for (var resultado : datos) {
            comparaciones++;
            if (resultado.servicio().equals(servicio)) {
                suyos.add(resultado);
            }
        }
        return List.copyOf(suyos);
    }

    @Override
    public Set<Servicio> servicios() {
        var vistos = new LinkedHashSet<Servicio>();
        for (var resultado : datos) {
            vistos.add(resultado.servicio());
        }
        return Set.copyOf(vistos);
    }

    /** Comparaciones acumuladas por todas las llamadas a {@link #historial(Servicio)}. */
    public long comparaciones() {
        return comparaciones;
    }

    /**
     * Reproduce una ejecución del monitor y devuelve las comparaciones que ha costado.
     *
     * <p>En cada ronda, y para cada servicio, se guarda un resultado y después se consulta su
     * historial, que es el orden del bucle de {@code Monitor.comprobar}. No hacen falta ni la
     * sonda ni la política: lo que se mide es el repositorio.
     *
     * @param rondas número de rondas, al menos 1
     * @param servicios número de servicios distintos, al menos 1
     * @throws IllegalArgumentException si alguno es menor que 1
     */
    public static long comparacionesDeUnaEjecucion(int rondas, int servicios) {
        if (rondas < 1 || servicios < 1) {
            throw new IllegalArgumentException("Hacen falta al menos una ronda y un servicio");
        }
        var repositorio = new RepositorioContador();
        var lista = new ArrayList<Servicio>();
        for (int i = 0; i < servicios; i++) {
            lista.add(new ServicioTcp("s" + i, 9000 + i));
        }
        for (int ronda = 0; ronda < rondas; ronda++) {
            for (Servicio servicio : lista) {
                // Primero guardar y despues consultar, como el monitor: por eso en la k-esima
                // consulta hay k resultados, y no k - 1.
                repositorio.guardar(Resultado.activo(servicio, Duration.ofMillis(10)));
                repositorio.historial(servicio);
            }
        }
        return repositorio.comparaciones();
    }

    // ----------------------------------------------------------------------------------
    // LA FORMULA. Con r rondas y s servicios se guardan N = r * s resultados, y justo despues de
    // cada uno se pide un historial. En la consulta k-esima el repositorio tiene k resultados y los
    // recorre todos, asi que el total es 1 + 2 + ... + N = N(N + 1) / 2. Con N = r * s, eso es
    // (r^2 s^2 + r s) / 2, y el termino que domina es r^2 s^2 / 2: el O(r^2 s^2) del tema.
    // Duplicar las rondas duplica N y multiplica el total por cuatro, que es lo que muestra la
    // tabla: 2.001.000 con 100 rondas y 8.002.000 con 200.
    //
    // RESPUESTA al «Para pensar»: el contador no deberia vivir en el repositorio.
    //
    // Medir es una responsabilidad distinta de guardar, y mezclarlas va contra el principio de
    // responsabilidad unica del Tema 1: el codigo de produccion cambia si cambia lo que se quiere
    // medir. La alternativa es un DECORADOR: una clase que implementa RepositorioResultados,
    // envuelve al repositorio de verdad y le reenvia cada llamada. Para contar las comparaciones
    // no necesita mirar dentro: sabe cuantos resultados se han guardado, porque todos pasan por su
    // guardar, y en cada historial suma ese numero, que es lo que va a recorrer la lista unica. El
    // repositorio queda intacto y el instrumento se anade o se quita sin tocarlo, que es tambien el
    // principio abierto-cerrado.
    // ----------------------------------------------------------------------------------
}
