package com.psl.rockfield.challenge;

import java.util.concurrent.Callable;

/**
 * Clase que se encarga de encontrar el mayor número "ordenado" menor o igual al
 * número dado. Solver implementa Callable para poder ser ejecutado en paralelo.
 */
public class Solver implements Callable<String> {

	private final String entry;

	/**
	 * @param entry Número de entrada propuesto por el reto.
	 */
	public Solver(String entry) {
		this.entry = entry;
	}

	/**
	 * Método que encuentra el mayor número "ordenado" menor o igual a "entry".
	 * Complejidad O(N), donde N es el número dígitos (caracteres) que posee la
	 * variable "entry".
	 *
	 * @return la línea formateada para ser escrita en el archivo de salida.
	 */
	@Override
	public String call() {
		if (entry.length() == 1) {
			return "N=" + entry + ", Último número “ordenado”: " + entry + ".";
		}
		char[] values = entry.toCharArray();
		// find reduce index
		int index = 0;
		int i = 0;
		for (i = 1; i < entry.length(); i++) {
			if (values[i] < values[i - 1]) {
				break;
			} else if (values[i] > values[i - 1]) {
				index = i;
			}
		}
		if (i < entry.length()) {
			// reduce value at index
			values[index] = (char) (values[index] - 1);
			// fill everything else with 9
			for (i = index + 1; i < entry.length(); i++) {
				values[i] = '9';
			}
			String response = removeStartingZero(values);
			return "N=" + entry + ", Último número “ordenado”: " + response + ".";
		}
		return "N=" + entry + ", Último número “ordenado”: " + entry + ".";
	}

	private String removeStartingZero(char[] values) {
		String response = new String(values);
		if (values[0] == '0') {
			return response.substring(1);
		}
		return response;
	}
}
