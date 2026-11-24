package es.uib.prgava.tema2.ejercicios;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * Ejercicio 2.1.6. Un almacén de {@code int} que crece solo, contando las copias.
 *
 * <p>Es lo que hace {@code ArrayList} por dentro, y el objeto del ejercicio es la cuenta: con una
 * estrategia que <strong>multiplica</strong> la capacidad, las copias totales son menos de dos por
 * elemento añadido —de ahí el coste amortizado constante—; con una estrategia que suma una
 * cantidad fija, crecen con el cuadrado del número de elementos.
 *
 * <p>La estrategia entra por el constructor, de modo que las dos políticas se comparan sin
 * escribir la clase dos veces: es el patrón Strategy del Tema 1.
 */
public final class ArrayCreciente {

    private int[] elementos;
    /** Cuántos hay de verdad. No es {@code elementos.length}, que es la capacidad. */
    private int tamano;
    private final IntUnaryOperator crecimiento;
    private long copias;

    /**
     * @param capacidadInicial longitud del array con el que se empieza, al menos 1
     * @param crecimiento recibe la capacidad actual y devuelve la nueva; por ejemplo
     *        {@code c -> 2 * c} o {@code c -> c + 10}
     * @throws IllegalArgumentException si {@code capacidadInicial} es menor que 1
     * @throws NullPointerException si {@code crecimiento} es {@code null}
     */
    public ArrayCreciente(int capacidadInicial, IntUnaryOperator crecimiento) {
        if (capacidadInicial < 1) {
            throw new IllegalArgumentException("La capacidad inicial tiene que ser al menos 1");
        }
        this.crecimiento = Objects.requireNonNull(crecimiento, "crecimiento");
        this.elementos = new int[capacidadInicial];
    }

    /**
     * Añade un valor al final, haciendo crecer el array si está lleno.
     *
     * <p>Cuando toca crecer, al contador de copias se le suma el número de elementos copiados,
     * que es el tamaño actual y no la capacidad.
     *
     * @throws IllegalStateException si la estrategia de crecimiento no aumenta la capacidad
     */
    public void anadir(int valor) {
        if (tamano == elementos.length) {
            int nueva = crecimiento.applyAsInt(elementos.length);
            // Una estrategia que no crece dejaria el almacen lleno para siempre: mejor fallar
            // aqui, con un mensaje claro, que con un ArrayIndexOutOfBoundsException una linea mas
            // abajo que nadie sabria relacionar con la estrategia.
            if (nueva <= elementos.length) {
                throw new IllegalStateException("La estrategia de crecimiento no aumenta la capacidad: "
                        + elementos.length + " -> " + nueva);
            }
            elementos = Arrays.copyOf(elementos, nueva);
            // Se copian los que hay, que cuando el array esta lleno son tantos como la capacidad
            // vieja. Contar 'nueva' seria contar las casillas vacias, que no se copian.
            copias += tamano;
        }
        elementos[tamano] = valor;
        tamano++;
    }

    /**
     * El valor de una posición escrita.
     *
     * @throws IndexOutOfBoundsException si {@code posicion} no está entre 0 y {@code tamano() - 1}
     */
    public int get(int posicion) {
        // Contra el tamano, no contra la capacidad: con capacidad 16 y tres elementos, la posicion
        // 5 existe en el array pero no se ha escrito, y devolver su 0 seria mentir.
        Objects.checkIndex(posicion, tamano);
        return elementos[posicion];
    }

    /** Cuántos elementos se han añadido. */
    public int tamano() {
        return tamano;
    }

    /** Longitud del array interno, que es otra cosa que el tamaño. */
    public int capacidad() {
        return elementos.length;
    }

    /** Elementos copiados en total desde que se creó el almacén. */
    public long copiasRealizadas() {
        return copias;
    }

    // ----------------------------------------------------------------------------------
    // RESPUESTA al «Para pensar»: ¿que se gana multiplicando por 1,5 en lugar de por 2?
    //
    // Memoria. Con el factor 2, justo despues de crecer la mitad del array esta vacia; con 1,5,
    // como mucho un tercio. Y hay un argumento mas fino: con un factor menor que la razon aurea
    // (~1,618), la suma de los bloques que se van liberando acaba siendo suficiente para alojar
    // el siguiente, de modo que el gestor de memoria puede reutilizarlos; con 2 nunca ocurre.
    //
    // Lo que se pierde son copias: con factor k el total copiado tiende a n/(k-1), es decir n con
    // k = 2 y 2n con k = 1,5, y hay mas crecimientos. Las dos son lineales, asi que el orden es el
    // mismo; la eleccion es un compromiso entre memoria desperdiciada y trabajo de copia, y OpenJDK
    // ha preferido gastar menos memoria.
    // ----------------------------------------------------------------------------------
}
