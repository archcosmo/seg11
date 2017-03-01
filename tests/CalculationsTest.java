package tests;

import core.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class CalculationsTest {
	Calculations calc = new Calculations();
	Runway testRunway = new Runway("Test Runway", 240, 300); //Using default values - changeable!
	LogicalRunway nineR = new LogicalRunway("09R", testRunway, 3660, 3660, 3660, 3353);
	LogicalRunway twentySevenL = new LogicalRunway("27L", testRunway, 3660, 3660 ,3660, 3660);
	LogicalRunway nineL = new LogicalRunway("09L", testRunway, 3902, 3902, 3902, 3595);
	LogicalRunway twentySevenR = new LogicalRunway("27R", testRunway, 3884, 3962, 3884 , 3884);

	@Test
	public void testScenario1AwayOver() {
		/*
		 * Scenario 1
		 *	12m tall obstacle, on the centreline, 50m before the 09L threshold, i.e. to the west 
		 *	of the threshold. The same obstacle is 3646m from the 27R threshold
		 * 
		 */
		Obstacle obstacle = new Obstacle("Test Object", -50, -50, 12);
		obstacle.setPos(-10, 10);
		
		ArrayList<Integer> thresholds = calc.calculateDistances(nineL, obstacle, 300);
		System.out.println("TORA (3346): " + thresholds.get(0));
		System.out.println("TODA (3346): " + thresholds.get(1));
		System.out.println("ASDA (3346): " + thresholds.get(2));
		System.out.println("LDA (2985): " + thresholds.get(3));
		assertEquals("09L Away/Over TORA", 3346, (int) thresholds.get(0));
		assertEquals("09L Away/Over TODA", 3346, (int) thresholds.get(1));
		assertEquals("09L Away/Over ASDA", 3346, (int) thresholds.get(2));
		assertEquals("09L Away/Over LDA", 2985, (int) thresholds.get(3));
		
	}
//	
//	@Test
//	public void testScenario1Toward() {
//		Obstacle obstacle = new Obstacle("Test Object", 50 ,50,12, -10, 10);
//		
//		ArrayList<Integer> thresholds = calc.calculateDistances(twentySevenR, obstacle, 300);
//		System.out.println("TORA (2986): " + thresholds.get(0));
//		System.out.println("TODA (2986): " + thresholds.get(1));
//		System.out.println("ASDA (2986): " + thresholds.get(2));
//		System.out.println("LDA (3346): " + thresholds.get(3));
//		assertEquals("09L Away/Over TORA", 2984, (int) thresholds.get(0));
//		assertEquals("09L Away/Over TODA", 2984, (int) thresholds.get(1));
//		assertEquals("09L Away/Over ASDA", 2984, (int) thresholds.get(2));
//		assertEquals("09L Away/Over LDA", 3346, (int) thresholds.get(3));
//		
//	}
}