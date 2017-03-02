package tests;

import core.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CalculationsTest {
	Calculations calc = new Calculations();
	Runway testRunway = new Runway("Test Runway", 240, 300, 60); //Using default values - changeable!
	LogicalRunway nineR = new LogicalRunway("09R", testRunway, 3660, 3660, 3660, 3353, 0);
	LogicalRunway twentySevenL = new LogicalRunway("27L", testRunway, 3660, 3660 ,3660, 3660, 0);
	LogicalRunway nineL = new LogicalRunway("09L", testRunway, 3902, 3902, 3902, 3595, 0);
	LogicalRunway twentySevenR = new LogicalRunway("27R", testRunway, 3884, 3962, 3884 , 3884, 0);

	@Test
	public void testTORA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setFrom(0, -50, 3646);
		int tora = calc.calculateDistances(nineL, scen1,"away").get(0);
		assertEquals(3345,tora);
		tora = calc.calculateDistances(twentySevenR, scen1,"towards").get(0);
		System.out.println(calc.getLastCalculationBreakdown());	//Output to compare against given example
		assertEquals(2986,tora);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setFrom(20, 500, 2853);
		tora = calc.calculateDistances(nineR, scen2,"towards").get(0);
		assertEquals(1850,tora);
		tora = calc.calculateDistances(twentySevenL, scen2,"away").get(0);
		assertEquals(2860,tora);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setFrom(60, 3203, 150);
		tora = calc.calculateDistances(nineR, scen3,"away").get(0);
		assertEquals(2903,tora);
		tora = calc.calculateDistances(twentySevenL, scen3,"towards").get(0);
		assertEquals(2393,tora);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setFrom(20, 3546, 50);
		tora = calc.calculateDistances(nineL, scen4,"towards").get(0);
		assertEquals(2793,tora);
		tora = calc.calculateDistances(twentySevenR, scen4,"away").get(0);
		assertEquals(3534,tora);
	}
	
	@Test
	public void testTODA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setFrom(0, -50, 3646);
		int toda = calc.calculateDistances(nineL, scen1,"away").get(1);
		assertEquals(3345,toda);
		toda = calc.calculateDistances(twentySevenR, scen1,"towards").get(1);
		assertEquals(2986,toda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setFrom(20, 500, 2853);
		toda = calc.calculateDistances(nineR, scen2,"towards").get(1);
		assertEquals(1850,toda);
		toda = calc.calculateDistances(twentySevenL, scen2,"away").get(1);
		assertEquals(2860,toda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setFrom(60, 3203, 150);
		toda = calc.calculateDistances(nineR, scen3,"away").get(1);
		assertEquals(2903,toda);
		toda = calc.calculateDistances(twentySevenL, scen3,"towards").get(1);
		assertEquals(2393,toda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setFrom(20, 3546, 50);
		toda = calc.calculateDistances(nineL, scen4,"towards").get(1);
		assertEquals(2793,toda);
		toda = calc.calculateDistances(twentySevenR, scen4,"away").get(1);
		assertEquals(3612,toda);
	}
	
	@Test
	public void testASDA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setFrom(0, -50, 3646);
		int asda = calc.calculateDistances(nineL, scen1,"away").get(0);
		assertEquals(3345,asda);
		asda = calc.calculateDistances(twentySevenR, scen1,"towards").get(0);
		assertEquals(2986,asda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setFrom(-20, 500, 2853);
		asda = calc.calculateDistances(nineR, scen2,"towards").get(0);
		assertEquals(1850,asda);
		asda = calc.calculateDistances(twentySevenL, scen2,"away").get(0);
		assertEquals(2860,asda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setFrom(60, 3203, 150);
		asda = calc.calculateDistances(nineR, scen3,"away").get(0);
		assertEquals(2903,asda);
		asda = calc.calculateDistances(twentySevenL, scen3,"towards").get(0);
		assertEquals(2393,asda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setFrom(20, 3546, 50);
		asda = calc.calculateDistances(nineL, scen4,"towards").get(0);
		assertEquals(2793,asda);
		asda = calc.calculateDistances(twentySevenR, scen4,"away").get(0);
		assertEquals(3534,asda);
	}
	
	@Test
	public void testLDA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setFrom(0, -50, 3646);
		int lda = calc.calculateDistances(nineL, scen1,"away").get(3);
		assertEquals(2985,lda);
		lda = calc.calculateDistances(twentySevenR, scen1,"towards").get(3);
		assertEquals(3346,lda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setFrom(20, 500, 2853);
		lda = calc.calculateDistances(nineR, scen2,"towards").get(3);
		assertEquals(2553,lda);
		lda = calc.calculateDistances(twentySevenL, scen2,"away").get(3);
		assertEquals(1850,lda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setFrom(60, 3203, 150);
		lda = calc.calculateDistances(nineR, scen3,"away").get(3);
		assertEquals(2393,lda);
		lda = calc.calculateDistances(twentySevenL, scen3,"towards").get(3);
		assertEquals(2903,lda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setFrom(20, 3546, 50);
		lda = calc.calculateDistances(nineL, scen4,"towards").get(3);
		assertEquals(3246,lda);
		lda = calc.calculateDistances(twentySevenR, scen4,"away").get(3);
		assertEquals(2774,lda);
	}
}