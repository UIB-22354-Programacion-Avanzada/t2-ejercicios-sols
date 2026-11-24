# t2-ejercicios-sols — Soluciones del Tema 2

Soluciones comentadas de los **17 ejercicios** del Tema 2 de *Programación Avanzada (22354)*,
Grado en Ingeniería Telemática, UIB-EPS.

> ⚠️ **Repositorio privado, uso docente.** Es la contrapartida de las dos plantillas que resuelve
> el alumnado,
> [`t2-ejercicios-2-1`](https://github.com/UIB-22354-Programacion-Avanzada/t2-ejercicios-2-1) y
> [`t2-ejercicios-2-2-3`](https://github.com/UIB-22354-Programacion-Avanzada/t2-ejercicios-2-2-3).
> No debe hacerse público mientras los ejercicios estén en uso: los enunciados son la base de los
> Talleres y del Examen Parcial.

## Qué tiene de distinto

Mismo proyecto Maven, mismas clases, mismas pruebas, pero **todo resuelto y todas las pruebas en
verde**. Y, sobre todo, **comentado**: cada solución explica no solo qué hace el código, sino por
qué se ha elegido esa forma y qué habría pasado con las alternativas.

Las dos plantillas del alumnado se han **fusionado en un solo proyecto**: tienen el mismo paquete
base, las mismas clases del monitor y ningún nombre en común entre sus ejercicios, así que caben
juntas sin tocar nada.

Los comentarios son de tres tipos:

- **Javadoc de la clase**: la decisión de fondo, por ejemplo por qué un `TreeMap` y no un
  `HashMap`, o por qué un solo recorrido y no dos.
- **Comentarios de línea**: el detalle que se pasa por alto, como `izq + (der - izq) / 2` en lugar
  de `(izq + der) / 2`, `copias += tamano` antes de copiar, o el `break` que hace lineal el
  recorrido por prefijo.
- **Bloques de respuesta al final de la clase**: los recuentos y tablas que pide el enunciado,
  y la respuesta a la pregunta de **«Para pensar»**, que no está en el tema y es exactamente el
  tipo de pregunta del Examen Parcial.

En este tema la mayoría de los ejercicios entregan **un número que tiene que coincidir con una
fórmula**, no solo un resultado correcto. Las pruebas lo comprueban, y los bloques de respuesta
dicen de dónde sale cada número.

## Comprobación

```bash
mvn test
```

Debe dar **100 pruebas, todas en verde**. Si alguna falla, la solución y su prueba se han
desincronizado: es el primer sitio donde mirar antes de tocar un enunciado.

Los números de los enunciados están comprobados con estas soluciones: los recuentos de 2.3.1
(2.000, 1.001 y 2), la tabla de 2.3.4 (de 2.001.000 frente a 101.000 con 100 rondas a 128.008.000
frente a 6.408.000 con 800), los de 2.1.9 (21 y 2.001.000) y los de `ArrayCreciente` (1.023 y
49.500 copias).

## Dónde está cada ejercicio

| Ejercicio | Clases |
|---|---|
| 2.1.1 | `ejercicios/RecorridosEjercicio` |
| 2.1.2 | `ejercicios/EstadosEjercicio` |
| 2.1.3 | `ejercicios/CotasEjercicio` |
| 2.1.4 | `ejercicios/CruceEjercicio` |
| 2.1.5 | `ejercicios/CasosEjercicio` |
| 2.1.6 | `ejercicios/ArrayCreciente` |
| 2.1.7 | `ejercicios/BusquedasEjercicio` |
| 2.1.8 | `ejercicios/OrdenEjercicio` |
| 2.1.9 | `monitor/RepositorioContador` |
| 2.2.1 | `ejercicios/PalabrasUnicas` |
| 2.2.2 | `ejercicios/HistorialDeNavegacion` |
| 2.2.3 | `ejercicios/Agenda` |
| 2.2.4 | `ejercicios/ResultadosMasLentos` |
| 2.3.1 | `ejercicios/RepetidosEjercicio` |
| 2.3.2 | `ejercicios/ConsumirPorElPrincipio` |
| 2.3.3 | `ejercicios/Telefonos` |
| 2.3.4 | `ejercicios/ComparativaRepositorios` |

Las clases de `tema2/eficiencia`, `tema2/monitor/RepositorioIndexado` y `tema1/monitor` son copia
exacta de las plantillas, que a su vez son el código del tema.

## Diferencias respecto a las plantillas

Lo que hay aquí y no en `t2-ejercicios-2-1` ni en `t2-ejercicios-2-2-3`:

- los cuerpos de todos los métodos, comentados, y las constantes que el estudiante tiene que
  rellenar (recuentos, testigos, conclusiones);
- los bloques de respuesta al final de cada clase de ejercicio;
- el `artifactId` del `pom.xml`, que pasa a `t2-ejercicios-sols`.

Las pruebas son las de las plantillas, sin cambios. Un `diff -r` entre este repositorio y cada
plantilla muestra exactamente el trabajo del alumnado en esa plantilla, más los ficheros de la
otra.

## Cómo usarlo en clase

Las soluciones no están pensadas para entregarse, sino para **corregir y discutir**. Los bloques de
respuesta al final de cada clase están escritos como se espera que las conteste el alumnado en el
Examen Parcial: en prosa, con la fórmula y explicando la consecuencia práctica. Sirven de rúbrica.

## Licencia

Mismo régimen que las plantillas: el código, bajo licencia [MIT](LICENSE), copyright © 2026
Alejandro Mesejo. El material docente del sitio web, bajo
[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.en).
