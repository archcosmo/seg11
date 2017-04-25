package tests;

import Model.*;
import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class CalculationsTest {
	Calculations calc = new Calculations();
	Runway testRunway = new Runway(240, 300, 60, 4000, 100); //Using default values - changeable!
	LogicalRunway nineR = new LogicalRunway("09R", testRunway, 3660, 3660, 3660, 3353);
	LogicalRunway twentySevenL = new LogicalRunway("27L", testRunway, 3660, 3660 ,3660, 3660);
	LogicalRunway nineL = new LogicalRunway("09L", testRunway, 3902, 3902, 3902, 3595);
	LogicalRunway twentySevenR = new LogicalRunway("27R", testRunway, 3884, 3962, 3884 , 3884);
/*
	@Test
	public void testTORA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setPosition(-50,0);
		int tora = calc.calculateDistances(nineL, scen1,false).get(0);
		System.out.println(tora);
		assertEquals(3345,tora);
		scen1.setPosition(3646,0);
		tora = calc.calculateDistances(twentySevenR, scen1,true).get(0);
		System.out.println(calc.getLastCalculationBreakdown());	//Output to compare against given example
		assertEquals(2986,tora);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setPosition(2853,20);
		tora = calc.calculateDistances(nineR, scen2,true).get(0);
		assertEquals(1850,tora);
		scen2.setPosition(500,20);
		tora = calc.calculateDistances(twentySevenL, scen2,false).get(0);
		assertEquals(2860,tora);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setPosition(150,60);
		tora = calc.calculateDistances(nineR, scen3,false).get(0);
		assertEquals(2903,tora);
		scen3.setPosition(3203,60);
		tora = calc.calculateDistances(twentySevenL, scen3,true).get(0);
		assertEquals(2393,tora);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setPosition(3546,20);
		tora = calc.calculateDistances(nineL, scen4,true).get(0);
		assertEquals(2793,tora);
		scen4.setPosition(50,20);
		tora = calc.calculateDistances(twentySevenR, scen4,false).get(0);
		assertEquals(3534,tora);
	}
	
	@Test
	public void testTODA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setPosition(-50,0);
		int toda = calc.calculateDistances(nineL, scen1,false).get(1);
		System.out.println(toda);
		assertEquals(3345,toda);
		scen1.setPosition(3646,0);
		toda = calc.calculateDistances(twentySevenR, scen1,true).get(1);
		System.out.println(calc.getLastCalculationBreakdown());	//Output to compare against given example
		assertEquals(2986,toda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setPosition(2853,20);
		toda = calc.calculateDistances(nineR, scen2,true).get(1);
		assertEquals(1850,toda);
		scen2.setPosition(500,20);
		toda = calc.calculateDistances(twentySevenL, scen2,false).get(1);
		assertEquals(2860,toda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setPosition(150,60);
		toda = calc.calculateDistances(nineR, scen3,false).get(1);
		assertEquals(2903,toda);
		scen3.setPosition(3203,60);
		toda = calc.calculateDistances(twentySevenL, scen3,true).get(1);
		assertEquals(2393,toda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setPosition(3546,20);
		toda = calc.calculateDistances(nineL, scen4,true).get(1);
		assertEquals(2793,toda);
		scen4.setPosition(50,20);
		toda = calc.calculateDistances(twentySevenR, scen4,false).get(1);
		assertEquals(3612,toda);
	}
	
	@Test
	public void testASDA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setPosition(-50,0);
		int asda = calc.calculateDistances(nineL, scen1,false).get(2);
		System.out.println(asda);
		assertEquals(3345,asda);
		scen1.setPosition(3646,0);
		asda = calc.calculateDistances(twentySevenR, scen1,true).get(2);
		System.out.println(calc.getLastCalculationBreakdown());	//Output to compare against given example
		assertEquals(2986,asda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setPosition(2853,20);
		asda = calc.calculateDistances(nineR, scen2,true).get(2);
		assertEquals(1850,asda);
		scen2.setPosition(500,20);
		asda = calc.calculateDistances(twentySevenL, scen2,false).get(2);
		assertEquals(2860,asda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setPosition(150,60);
		asda = calc.calculateDistances(nineR, scen3,false).get(2);
		assertEquals(2903,asda);
		scen3.setPosition(3203,60);
		asda = calc.calculateDistances(twentySevenL, scen3,true).get(2);
		assertEquals(2393,asda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setPosition(3546,20);
		asda = calc.calculateDistances(nineL, scen4,true).get(2);
		assertEquals(2793,asda);
		scen4.setPosition(50,20);
		asda = calc.calculateDistances(twentySevenR, scen4,false).get(2);
		assertEquals(3534,asda);
	}
	
	@Test
	public void testLDA() {
		Obstacle scen1 = new Obstacle("Test Object", 50, 50, 12);
		scen1.setPosition(-50,0);
		int lda = calc.calculateDistances(nineL, scen1,false).get(3);
		System.out.println(lda);
		assertEquals(2985,lda);
		scen1.setPosition(3646,0);
		lda = calc.calculateDistances(twentySevenR, scen1,true).get(3);
		System.out.println(calc.getLastCalculationBreakdown());	//Output to compare against given example
		assertEquals(3346,lda);

		Obstacle scen2 = new Obstacle("Test Object", 50, 50, 25);
		scen2.setPosition(2853,20);
		lda = calc.calculateDistances(nineR, scen2,true).get(3);
		assertEquals(2553,lda);
		scen2.setPosition(500,20);
		lda = calc.calculateDistances(twentySevenL, scen2,false).get(3);
		assertEquals(1850,lda);
		

		Obstacle scen3 = new Obstacle("Test Object", 50, 50, 15);
		scen3.setPosition(150,60);
		lda = calc.calculateDistances(nineR, scen3,false).get(3);
		assertEquals(2393,lda);
		scen3.setPosition(3203,60);
		lda = calc.calculateDistances(twentySevenL, scen3,true).get(3);
		assertEquals(2903,lda);
		

		Obstacle scen4 = new Obstacle("Test Object", 50, 50, 20);
		scen4.setPosition(3546,20);
		lda = calc.calculateDistances(nineL, scen4,true).get(3);
		assertEquals(3246,lda);
		scen4.setPosition(50,20);
		lda = calc.calculateDistances(twentySevenR, scen4,false).get(3);
		assertEquals(2774,lda);
	}*/
}