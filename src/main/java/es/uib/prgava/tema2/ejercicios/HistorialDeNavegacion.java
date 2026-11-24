package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * Ejercicio 2.2.2. La pila: el botón «atrás» de un navegador.
 *
 * <p>La última página visitada es la primera que se recupera. Por dentro, un {@code ArrayDeque}
 * usado como pila; nunca {@code java.util.Stack}, que es sincronizada sin necesidad y hereda de
 * {@code Vector}, así que permite insertar en medio de una «pila».
 */
public final class HistorialDeNavegacion {

    /** Declarado como Deque: se usan solo las operaciones de los extremos. */
    private final Deque<String> paginas = new ArrayDeque<>();

    /**
     * Apila una página y la convierte en la actual.
     *
     * @throws IllegalArgumentException si la {@code url} es {@code null} o está en blanco
     */
    public void visitar(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("La url no puede estar en blanco");
        }
        paginas.addLast(url);
    }

    /** La página en la que estás, sin quitarla, o vacío si no has visitado ninguna. */
    public Optional<String> actual() {
        // peekLast devuelve null con la pila vacia, y Optional.ofNullable lo convierte en vacio.
        return Optional.ofNullable(paginas.peekLast());
    }

    /**
     * Retrocede: descarta la página actual y devuelve la anterior, que pasa a ser la actual.
     *
     * <p>Si solo hay una página, o ninguna, no hay a dónde retroceder: no cambia nada y devuelve
     * vacío.
     */
    public Optional<String> atras() {
        // Con una sola pagina no se retrocede: la comprobacion va antes de quitar nada, para que
        // «no cambia nada» sea literalmente cierto.
        if (paginas.size() < 2) {
            return Optional.empty();
        }
        paginas.pollLast();
        return Optional.of(paginas.peekLast());
    }

    /** Cuántas páginas hay apiladas. */
    public int tamano() {
        return paginas.size();
    }

    // ----------------------------------------------------------------------------------
    // LA PAREJA DE METODOS. Aqui una pila vacia es un caso ESPERADO —un navegador recien abierto—,
    // asi que se usan los que devuelven un valor especial (peekLast, pollLast) y no los que lanzan
    // (getLast, removeLast); el vacio llega al que llama como Optional, visible en el tipo.
    //
    // RESPUESTA al «Para pensar»: en la pila de llamadas de la maquina virtual, no.
    //
    // Ahi sacar de una pila vacia no es un caso que haya que atender, sino la prueba de que el
    // programa esta roto: una llamada que vuelve sin que nadie la hiciera. Un Optional obligaria a
    // cada llamador a decidir que hacer con algo que no deberia ocurrir nunca, y casi siempre
    // acabaria ignorado. Lo correcto es la pareja que lanza, para que el error salte en el punto
    // exacto en que se produce. La regla: Optional para la ausencia normal, excepcion para la
    // ausencia que revela un fallo.
    // ----------------------------------------------------------------------------------
}
