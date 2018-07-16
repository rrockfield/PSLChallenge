package com.psl.rockfield.challenge;

import org.junit.*;

public class SolverTest {
	
	@Test
	public void test_20() throws Exception {
		test("20", "19");		
	}

	@Test
	public void test_132() throws Exception {
		test("132", "129");		
	}

	@Test
	public void test_1000() throws Exception {
		test("1000", "999");		
	}

	@Test
	public void test_8() throws Exception {
		test("8", "8");		
	}
	
	@Test
	public void test_11111110() throws Exception {
		test("11111110", "9999999");		
	}
	
	private void test(String n, String expected) throws Exception {
		Solver solver = new Solver(n);
		Assert.assertEquals("N=" + n + ", Último número “ordenado”: " + expected + ".", solver.call());
	}
}
