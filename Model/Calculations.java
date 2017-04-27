package Model;

import java.util.ArrayList;

public class Calculations {

	private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
	private StringBuilder lastCalculationBreakdown;
	private String lastCalculationValues = "";

	public Calculations() {
		lastCalculationBreakdown =  new StringBuilder();
	}

	private boolean obInCGA(LogicalRunway lr, Obstacle obstacle) {
		if(obstacle.distanceFromLowAngleEndOfRunway > -60 - obstacle.length && obstacle.distanceFromLowAngleEndOfRunway < lr.runway.length + 60
				&& obstacle.distanceFromCenterline < 75 + obstacle.width + lr.runway.width/2 && obstacle.distanceFromCenterline > -75 - lr.runway.width/2)
			return true;
		if(obstacle.distanceFromLowAngleEndOfRunway > 150 - obstacle.length && obstacle.distanceFromLowAngleEndOfRunway < lr.runway.length -150
				&& obstacle.distanceFromCenterline < 105 + obstacle.width + lr.runway.width/2 && obstacle.distanceFromCenterline > -105 - lr.runway.width/2)
			return true;
		return false;
	}

	public ArrayList<Integer> calculateDistances(LogicalRunway logicalRunway, Obstacle obstacle, boolean towards) {

		if (obstacle == null || !obInCGA(logicalRunway, obstacle)) {
			ArrayList<Integer> thresholds = new ArrayList<>();
			thresholds.add(logicalRunway.tora);
			thresholds.add(logicalRunway.toda);
			thresholds.add(logicalRunway.asda);
			thresholds.add(logicalRunway.lda);
			return thresholds;
		}

		int newTora;
		int newToda;
		int newAsda;
		int newLda;
		int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
		lastCalculationBreakdown =  new StringBuilder();
		int RESA = logicalRunway.runway.RESA;
		int fromThresh = obstacle.distanceFromThreshold;

		if (towards){
			//Take off towards/ land towards logical runway start

			newTora = fromThresh + logicalRunway.displacedThreshold - ALSWidth - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("TORA = Distance from Threshold + <br> &nbsp; &nbsp; Displaced Threshold - Slope Calculation - Strip End<br>");
			lastCalculationBreakdown.append("     = "+fromThresh+" + "+logicalRunway.displacedThreshold+" - "+DEFAULT_ANGLE_OF_DESCENT +"*"+ obstacle.height+" - "+logicalRunway.getStripEnd()+"<br>");
			lastCalculationBreakdown.append("     = "+newTora+"<br>");

			newToda = newTora;
			lastCalculationBreakdown.append("TODA = (R) TORA<br>");
			lastCalculationBreakdown.append("     = "+newToda+"<br>");

			newAsda = newTora;
			lastCalculationBreakdown.append("ASDA = (R) TORA<br>");
			lastCalculationBreakdown.append("     = "+newAsda+"<br>");

			newLda = fromThresh - RESA - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("LDA  = Distance from Threshold - RESA - Strip End<br>");
			lastCalculationBreakdown.append("     = "+fromThresh+" - "+RESA+" - "+logicalRunway.getStripEnd()+"<br>");
			lastCalculationBreakdown.append("     = "+newLda+"<br>");

		} else {
			//Take off away/ land away from logical runway start

			int blastAllowance = logicalRunway.runway.blastAllowance;

			int RESA_StripEnd = RESA + logicalRunway.getStripEnd();
			int blast_DThreshold = blastAllowance + logicalRunway.displacedThreshold;
			if (RESA_StripEnd > blast_DThreshold) {

				newTora = logicalRunway.tora - fromThresh - (RESA_StripEnd);
				lastCalculationBreakdown.append("TORA = Original TORA - <br> &nbsp; &nbsp; Distance from Threshold - "+"RESA - Strip End<br>");
				lastCalculationBreakdown.append("     = "+logicalRunway.tora+" - "+fromThresh+" - "+RESA+" - "+logicalRunway.getStripEnd()+"<br>");
				lastCalculationBreakdown.append("     = "+newTora+"<br>");
			} else {

				newTora = logicalRunway.tora - fromThresh - (blast_DThreshold);
				lastCalculationBreakdown.append("TORA = Original TORA - <br> &nbsp; &nbsp; Distance from Threshold - "+"Blast Protection - Displaced Threshold<br>");
				lastCalculationBreakdown.append("     = "+logicalRunway.tora+" - "+fromThresh+" - "+blastAllowance + " - "+logicalRunway.displacedThreshold+"<br>");
				lastCalculationBreakdown.append("     = "+newTora+"<br>");
			}

			newToda = newTora + logicalRunway.clearwayLength;
			lastCalculationBreakdown.append("TODA = (R) TORA + Clearway<br>");
			lastCalculationBreakdown.append("     = "+newTora+" + "+logicalRunway.clearwayLength+"<br>");
			lastCalculationBreakdown.append("     = "+newToda+"<br>");

			newAsda = newTora + logicalRunway.stopwayLength;
			lastCalculationBreakdown.append("ASDA = (R) TORA + Stopway<br>");
			lastCalculationBreakdown.append("     = "+newTora+" + "+logicalRunway.stopwayLength+"<br>");
			lastCalculationBreakdown.append("     = "+newAsda+"<br>");

			newLda = logicalRunway.lda - fromThresh - obstacle.length - ALSWidth - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("LDA  = Original LDA - <br> &nbsp; &nbsp; Distance from Threshold - Slope Calculation - Strip End<br>");
			lastCalculationBreakdown.append("     = "+logicalRunway.lda+" - "+fromThresh+" - "+DEFAULT_ANGLE_OF_DESCENT +"*"+ obstacle.height+" - "+logicalRunway.getStripEnd()+"<br>");
			lastCalculationBreakdown.append("     = "+newLda+"<br>");
		}
		
		lastCalculationValues = 
				"Recalculated Threshold Values: <br> <br>" +
				"TORA = " + newTora + "<br>" +
				"TODA = " + newToda + "<br>" +
				"ASDA = " + newTora + "<br>" +
				"LDA  = " + newLda + "<br>";

		ArrayList<Integer> thresholds = new ArrayList<>();
		thresholds.add(newTora);
		thresholds.add(newToda);
		thresholds.add(newAsda);
		thresholds.add(newLda);

		//Prevent negative numbers
		for (int i = 0; i < 4; i++) {
			if (thresholds.get(i) < 0) {
				thresholds.set(i, 0);
			}
		}
		return thresholds;
	}

	public String getLastCalculationBreakdown() {
		return lastCalculationBreakdown.toString();
	}
	public String getLastCalculationValues() {
		return lastCalculationValues;
	}
	
	static class BreakdownWrapper {
		public String breakdownStr;
	}
}