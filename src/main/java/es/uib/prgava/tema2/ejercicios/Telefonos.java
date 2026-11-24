package es.uib.prgava.tema2.ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Ejercicio 2.3.3. Tabla o árbol: una guía de teléfonos con dos preguntas.
 *
 * <p>«El teléfono de este nombre» la contestan las dos estructuras; «los nombres que empiezan por
 * este prefijo», solo el árbol. Por eso la guía es un {@code TreeMap}, y el precio es que la
 * búsqueda exacta pasa de tiempo constante esperado a logarítmico garantizado.
 */
public final class Telefonos {

    private final NavigableMap<String, String> guia = new TreeMap<>();

    /**
     * Añade o sustituye un número.
     *
     * @throws IllegalArgumentException si el nombre o el teléfono son {@code null} o están en
     *         blanco
     */
    public void anadir(String nombre, String telefono) {
        if (nombre == null || nombre.isBlank() || telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El nombre y el telefono no pueden estar en blanco");
        }
        guia.put(nombre, telefono);
    }

    /** El teléfono de ese nombre exacto, o vacío si no está en la guía. */
    public Optional<String> telefonoDe(String nombre) {
        return Optional.ofNullable(guia.get(nombre));   // O(log n) en el arbol
    }

    /**
     * Los nombres que empiezan por {@code prefijo}, en orden alfabético.
     *
     * <p>El prefijo vacío devuelve todos los nombres, porque toda cadena empieza por la cadena
     * vacía.
     *
     * @return una lista nueva e inmutable
     */
    public List<String> nombresQueEmpiezanPor(String prefijo) {
        var nombres = new ArrayList<String>();
        // tailMap baja hasta el primer nombre >= prefijo en O(log n). Todos los que empiezan por el
        // prefijo son consecutivos a partir de ahi, porque en orden alfabetico un prefijo comun
        // los junta; asi que en cuanto aparece uno que no empieza por el, ya no habra mas.
        for (String nombre : guia.tailMap(prefijo, true).keySet()) {
            if (!nombre.startsWith(prefijo)) {
                break;
            }
            nombres.add(nombre);
        }
        return List.copyOf(nombres);
    }

    /** Cuántos nombres hay en la guía. */
    public int tamano() {
        return guia.size();
    }

    // ----------------------------------------------------------------------------------
    // LAS DOS PREGUNTAS. Un HashMap contestaria la primera, telefonoDe, en O(1) esperado. La
    // segunda le costaria recorrer todas las claves, quedarse con las que empiezan por el prefijo
    // y ordenarlas: O(n) mas O(k log k). En el arbol cuesta O(log n + k): bajar hasta el prefijo y
    // recorrer los k que coinciden. El orden de las claves ES el indice: en orden alfabetico, todos
    // los nombres con un mismo prefijo forman un tramo contiguo, y el arbol sabe llegar al
    // principio de ese tramo sin mirar el resto. La tabla, para repartir las claves, destruye ese
    // orden a proposito.
    //
    // RESPUESTA al «Para pensar»: ¿dos estructuras a la vez con un millon de nombres?
    //
    // Probablemente no. Con un millon de nombres, log2(n) son unas 20 comparaciones: la busqueda
    // exacta en el arbol ya es rapidisima, y pasar a O(1) esperado ahorra poco. A cambio, dos
    // estructuras doblan la memoria y obligan a que cada alta, cambio y baja toque las dos, con el
    // riesgo de que dejen de coincidir (y, con varios hilos, una carrera entre ellas). Merece la
    // pena solo si una medicion muestra que la busqueda exacta es de verdad el cuello de botella;
    // sin esa medicion, una estructura.
    // ----------------------------------------------------------------------------------
}
