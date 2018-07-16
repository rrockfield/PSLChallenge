package com.psl.rockfield.challenge;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Runner {

	private static final int NUMBER_OF_PARALLEL_THREADS = 16;
	private static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_PARALLEL_THREADS);
	private final List<Future<String>> threads = new LinkedList();

	/**
	 * Método de entrada que a su vez ejecuta los métodos de lectura y escritura
	 * a disco. Complejidad O(N), dado que es la misma complejidad de los
	 * metodos readFromEntryFile y writeAnswerToFile.
	 */
	public void run() {
		try {
			Instant start = Instant.now();
			readFromEntryFile();
			writeAnswerToFile();
			Instant end = Instant.now();
			System.out.println("Gone in " + Duration.between(start, end).getSeconds() + " seconds");
		} finally {
			executor.shutdownNow();
		}
	}

	/**
	 * Lectura del archivo "entrada.txt" línea por línea. Complejidad O(N),
	 * donde N es el número total de líneas del archivo.
	 */
	private void readFromEntryFile() {
		String fileName = "entrada.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				threads.add(executor.submit(new Solver(line)));
			});
		} catch (IOException e) {
			System.out.println("Unexpected error reading entrada.txt");
			System.err.println(e);
		}
	}

	/**
	 * Escritura del archivo "salida.txt" línea por línea. Complejidad O(N),
	 * donde N es el número total de líneas a escribir en el archivo.
	 */
	private void writeAnswerToFile() {
		Path path = Paths.get("salida.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			int counter = 1;
			for (Future<String> future : threads) {
				writer.write("Caso " + counter + ": " + future.get() + "\n");
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Unexpected error solving the problems in parallel");
			System.err.println(e);
		} catch (IOException e) {
			System.out.println("Unexpected error writing salida.txt");
			System.err.println(e);
		}
	}

	/**
	 * Método principal ejecutado por línea de comandos.
	 *
	 * @param args Los argumentos por línea de comandos se ignoran
	 */
	public static void main(String[] args) {
		new Runner().run();
	}
}
