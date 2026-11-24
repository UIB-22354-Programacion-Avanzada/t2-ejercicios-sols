package es.uib.prgava.tema2.ejercicios;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Ejercicio 2.2.3. El árbol de búsqueda: una agenda de un día.
 *
 * <p>Las tres preguntas de esta clase —el intervalo, la siguiente cita, el recorrido en orden— son
 * las que un árbol responde y una tabla <em>hash</em> no, porque calcular la cubeta destruye el
 * orden. Por dentro, un {@code TreeMap<LocalTime, String>}.
 */
public final class Agenda {

    /** Una cita: la hora y lo que hay a esa hora. */
    public record Cita(LocalTime hora, String texto) { }

    /** Declarado como NavigableMap, que es la interfaz que ofrece subMap y higherEntry. */
    private final NavigableMap<LocalTime, String> citas = new TreeMap<>();

    /**
     * Añade una cita. Si ya había una a esa hora, la sustituye.
     *
     * @throws IllegalArgumentException si el texto es {@code null} o está en blanco
     * @throws NullPointerException si la hora es {@code null}
     */
    public void anadir(LocalTime hora, String texto) {
        Objects.requireNonNull(hora, "hora");
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto de la cita no puede estar en blanco");
        }
        citas.put(hora, texto);   // put sustituye: una agenda de un dia no tiene dos citas a la vez
    }

    /**
     * Las citas desde {@code desde} incluido hasta {@code hasta} excluido, en orden de hora.
     *
     * @return una lista nueva e inmutable
     * @throws IllegalArgumentException si {@code hasta} es anterior a {@code desde}
     */
    public List<Cita> entre(LocalTime desde, LocalTime hasta) {
        if (hasta.isBefore(desde)) {
            throw new IllegalArgumentException("El intervalo esta al reves: " + desde + " - " + hasta);
        }
        // subMap baja por el arbol hasta 'desde' en O(log n) y recorre solo las k citas del
        // intervalo: O(log n + k), sin mirar las demas.
        return comoCitas(citas.subMap(desde, true, hasta, false));
    }

    /**
     * La primera cita estrictamente posterior a la hora dada, o vacío si no hay ninguna.
     *
     * <p>Estrictamente posterior: una cita a esa misma hora no cuenta.
     */
    public Optional<Cita> siguienteDespuesDe(LocalTime hora) {
        // higherEntry es «estrictamente mayor»; ceilingEntry seria «mayor o igual» y devolveria la
        // cita de esa misma hora. Las dos cuestan O(log n).
        var entrada = citas.higherEntry(hora);
        return entrada == null ? Optional.empty() : Optional.of(new Cita(entrada.getKey(), entrada.getValue()));
    }

    /** Todas las citas, en orden de hora. */
    public List<Cita> todas() {
        // El recorrido en orden del arbol ya es el orden de las horas: no hay nada que ordenar.
        return comoCitas(citas);
    }

    /** Convierte las entradas, que ya vienen en orden, en una lista inmutable de citas. */
    private static List<Cita> comoCitas(Map<LocalTime, String> parte) {
        var lista = new ArrayList<Cita>();
        for (var entrada : parte.entrySet()) {
            lista.add(new Cita(entrada.getKey(), entrada.getValue()));
        }
        return List.copyOf(lista);
    }

    // ----------------------------------------------------------------------------------
    // CON UN HashMap. entre() tendria que recorrer TODAS las citas, quedarse con las que caen en
    // el intervalo y despues ordenarlas, porque la tabla no guarda ningun orden: O(n) para filtrar
    // mas O(k log k) para ordenar las k elegidas, frente al O(log n + k) del arbol. Y
    // siguienteDespuesDe seria otro recorrido completo buscando el minimo de las posteriores.
    //
    // RESPUESTA al «Para pensar»: subMap devuelve una VISTA del propio arbol, no una copia. Si
    // despues de pedirla se anade una cita dentro del intervalo, la vista la muestra, porque no
    // tiene datos propios: cada vez que se recorre, recorre el arbol. Y al reves, un put sobre la
    // vista escribe en el arbol (y una hora fuera del intervalo lanza IllegalArgumentException).
    // La lista que devuelve entre() no cambia: es una copia hecha con List.copyOf en el momento de
    // la llamada. Por eso un metodo publico devuelve copias, y las vistas se usan dentro de la
    // clase, donde se sabe cuanto viven.
    // ----------------------------------------------------------------------------------
}
