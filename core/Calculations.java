package core;

import java.util.ArrayList;

public class Calculations {

	private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
	private StringBuilder lastCalculationBreakdown;

	public ArrayList<Integer> calculateDistances(LogicalRunway logicalRunway, Obstacle obstacle, boolean towards) {
		if(obstacle == null) {
			ArrayList<Integer> origValues = new ArrayList<Integer>();
			origValues.add(logicalRunway.tora);
			origValues.add(logicalRunway.toda);
			origValues.add(logicalRunway.asda);
			origValues.add(logicalRunway.lda);
			return origValues;
		}
		
		int newTora;
		int newToda;
		int newAsda;
		int newLda;

		if (Math.abs(obstacle.distanceFromCenterline) > 75 && (obstacle.distanceFromThreshold > 60 || obstacle.distanceFromThreshold > logicalRunway.tora + 60)) {
			ArrayList<Integer> thresholds = new ArrayList<>();
			thresholds.add(logicalRunway.tora);
			thresholds.add(logicalRunway.toda);
			thresholds.add(logicalRunway.asda);
			thresholds.add(logicalRunway.lda);
			return thresholds;
		}
		//if (obstacle.bottomYPos > topOfRunway || obstacle.topYPos < bottomOfRunway) {
		//	return logicalRunway.getThresholdClass
		//}

		int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
		lastCalculationBreakdown =  new StringBuilder();

		int RESA = logicalRunway.runway.RESA;

		int fromThresh = obstacle.distanceFromThreshold;
		
//		double obstacleMidXPosition = obstacle.xPos + ((obstacle.length) / 2.0);
//		if (obstacleMidXPosition > (logicalRunway.tora / 2.0)) {
		if (towards){
			//Take off towards/ land towards obstacle

			newTora = fromThresh + logicalRunway.displacedThreshold - ALSWidth - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("TORA = Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End\n");
			lastCalculationBreakdown.append("     = "+fromThresh+" + "+logicalRunway.displacedThreshold+" - "+DEFAULT_ANGLE_OF_DESCENT +"*"+ obstacle.height+" - "+logicalRunway.getStripEnd()+"\n");
			lastCalculationBreakdown.append("     = "+newTora+"\n");

			newToda = newTora;
			lastCalculationBreakdown.append("TODA = (R) TORA\n");
			lastCalculationBreakdown.append("     = "+newToda+"\n");

			newAsda = newTora;
			lastCalculationBreakdown.append("ASDA = (R) TORA\n");
			lastCalculationBreakdown.append("     = "+newAsda+"\n");

			newLda = fromThresh - RESA - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("LDA  = Distance from Threshold - RESA - Strip End\n");
			lastCalculationBreakdown.append("     = "+fromThresh+" - "+RESA+" - "+logicalRunway.getStripEnd()+"\n");
			lastCalculationBreakdown.append("     = "+newLda+"\n");

		} else {
			//Take off away/ land over obstacle

			int blastAllowance = logicalRunway.runway.blastAllowance;

			int RESA_StripEnd = RESA + logicalRunway.getStripEnd();
			int blast_DThreshold = blastAllowance + logicalRunway.displacedThreshold;
			if (RESA_StripEnd > blast_DThreshold) {

				newTora = logicalRunway.tora - fromThresh -(RESA_StripEnd);
				lastCalculationBreakdown.append("TORA = Original TORA - Distance from Threshold - "+"RESA - Strip End\n");
				lastCalculationBreakdown.append("     = "+logicalRunway.tora+" - "+fromThresh+" - "+RESA+" - "+logicalRunway.getStripEnd()+"\n");
				lastCalculationBreakdown.append("     = "+newTora+"\n");
			} else {

				newTora = logicalRunway.tora - fromThresh - (blast_DThreshold);
				lastCalculationBreakdown.append("TORA = Original TORA - Distance from Threshold - "+"Blast Protection - Displaced Threshold\n");
				lastCalculationBreakdown.append("     = "+logicalRunway.tora+" - "+fromThresh+" - "+blastAllowance + " - "+logicalRunway.displacedThreshold+"\n");
				lastCalculationBreakdown.append("     = "+newTora+"\n");
			}

			newToda = newTora + logicalRunway.clearwayLength;
			lastCalculationBreakdown.append("TODA = (R) TORA + Clearway\n");
			lastCalculationBreakdown.append("     = "+newTora+" + "+logicalRunway.clearwayLength+"\n");
			lastCalculationBreakdown.append("     = "+newToda+"\n");

			newAsda = newTora + logicalRunway.stopwayLength;
			lastCalculationBreakdown.append("ASDA = (R) TORA + Stopway\n");
			lastCalculationBreakdown.append("     = "+newTora+" + "+logicalRunway.stopwayLength+"\n");
			lastCalculationBreakdown.append("     = "+newAsda+"\n");

			newLda = logicalRunway.lda - fromThresh - ALSWidth - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("LDA  = Original LDA - Distance from Threshold - Slope Calculation - Strip End\n");
			lastCalculationBreakdown.append("     = "+logicalRunway.lda+" - "+fromThresh+" - "+DEFAULT_ANGLE_OF_DESCENT +"*"+ obstacle.height+" - "+logicalRunway.getStripEnd()+"\n");
			lastCalculationBreakdown.append("     = "+newLda+"\n");
		}

		ArrayList<Integer> thresholds = new ArrayList<>();
		thresholds.add(newTora);
		thresholds.add(newToda);
		thresholds.add(newAsda);
		thresholds.add(newLda);

		return thresholds;
	}

	public String getLastCalculationBreakdown() {
		return lastCalculationBreakdown.toString();
	}
	
	static class BreakdownWrapper {
		public String breakdownStr;
	}
}