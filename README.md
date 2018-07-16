# PSL Challenge: Números “ordenados”

La solución se compone de 2 archivos: Runner.java y Solver.java

La clase Runner es el punto de entrada que se encarga de las operación de I/O sobre el disco y al mismo tiempo orquesta la creación de hilos y su respectiva ejecución en paralelo.
Nótese que el en la línea 14 se puede modificar el número de hilos en paralelo que se desea ejecutar, dependiendo del hardware donde se ejecute la aplicación.

La clase Solver contiene la lógica para resolver el ejercicio dado un solo valor de entrada. El objetivo es armar el número "ordenado" de la siguiente manera:

1. Buscar el punto a lo largo del número en el cual un dígito es mayor al dígito siguiente.
2. Reducir en 1 el valor del dígito marcado.
3. Llenar con el número 9 los demás dígitos que se encuentren a la derecha de la marca.
4. Eliminar un cero al inicio del número en caso de encontrar uno.


## Cómo utilizar la librería

1. Compilar todo el proyecto con maven:

mvn install

2. Copiar la librería ./target/PSLChallenge.jar en el mismo directorio donde se encuentra el archivo "entrada.txt"

3. Ejecutar por línea de comandos:

java -jar PSLChallenge.jar

4. Esperar a que termine la ejecución y se genere el archivo "salida.txt" en el mismo directorio donde se encuentran "entrada.txt" y "PSLChallenge.jar"