package com.psl.rockfield.challenge;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Runner {

	private static final int NUMBER_OF_PARALLEL_THREADS = 20;
	private static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_PARALLEL_THREADS);
	private final List<Future<String>> threads = new LinkedList();

	/**
	 * Método de entrada que a su vez ejecuta los métodos de lectura y escritura
	 * a disco. Complejidad O(N), dado que es la misma complejidad de los
	 * metodos readFromEntryFile y writeAnswerToFile.
	 */
	public void run() {
		try {
			Date start = new Date();
			readFromEntryFile();
			writeAnswerToFile();
			Date end = new Date();
			System.out.println("Gone in " + (end.getTime() - start.getTime()) + " ms");
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
				if (!line.isEmpty()) {
					threads.add(executor.submit(new Solver(line)));
				}
			});
		} catch (IOException e) {
			System.out.println("Unexpected error reading entrada.txt");
			System.err.println(e);
		}
	}

	/**
	 * Complejidad O(N), donde N es el número total de líneas a escribir en el
	 * buffer.
	 */
	private void writeAnswerToFile() {
		try {
			int counter = 1;
			StringBuilder buffer = new StringBuilder();
			for (Future<String> future : threads) {
				buffer.append("Caso ").append(counter).append(": ").append(future.get()).append("\n");
			}
			flushBufferToHardDisk(buffer);
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Unexpected error solving the problems in parallel");
			System.err.println(e);
		}
	}

	/**
	 * Escritura del archivo "salida.txt".
	 */
	private void flushBufferToHardDisk(StringBuilder buffer) {
		Path path = Paths.get("salida.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(buffer.toString());
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
