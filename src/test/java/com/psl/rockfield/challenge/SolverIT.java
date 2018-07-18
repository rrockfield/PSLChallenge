package com.psl.rockfield.challenge;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import org.junit.Test;

public class SolverIT {

	static final long MAXVALUE = 1000000000000000000L;

	@Test
	public void stressTest() {
		generateEntradaTxt(2000000);
		new Runner().run();
	}

	private void generateEntradaTxt(int n) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < n; i++) {
			long number = (long) (Math.random() * MAXVALUE);
			buffer.append(number).append("\n");
		}
		Path path = Paths.get("entrada.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(buffer.toString());
		} catch (IOException e) {
			System.out.println("Unexpected error writing salida.txt");
			System.err.println(e);
		}
	}
}
