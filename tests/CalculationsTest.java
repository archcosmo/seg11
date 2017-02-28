package tests;

import core.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CalculationsTest {
	Calculations calc = new Calculations();

	@Test
	public void testTORA() {
		LogicalRunway nineR = new LogicalRunway("09R", 3660, 3660, 3660, 3353);
		LogicalRunway twentySevenL = new LogicalRunway("27L", 3660, 3660 ,3660, 3660);
		LogicalRunway nineL = new LogicalRunway("09L", 3902, 3902, 3902, 3595);
		LogicalRunway twentySevenR = new LogicalRunway("27R", 3884, 3962, 3884 , 3884);
		
		Obstacle obstacle = new Obstacle(50,50,12,"Object");
		
		LogicalRunway newRun = calc.calculateDistances(nineL, obstacle, 300);
		System.out.println(newRun.tora);
		assertEquals("09L Away/Over = 3346", 3346, newRun.tora);
//		assertEquals("27R Towards = 2986", 2986, );
//		assertEquals("09R Towards = 1850", 1850, );
//		assertEquals("27L Away/Over = 2860", 2860, );
//		assertEquals("09R Away/Over = 2903", 2903, );
//		assertEquals("27L Towards = 2393", 2393, );
//		assertEquals("09L Towards = 2792", 2792, );
//		assertEquals("27R Away/Over = 3534", 3534, );
	}
}