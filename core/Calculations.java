package core;

import java.util.ArrayList;

public class Calculations {

	private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
	private StringBuilder lastCalculationBreakdown;
	
	public Calculations() {
		lastCalculationBreakdown =  new StringBuilder();
	}

	public ArrayList<Integer> calculateDistances(LogicalRunway logicalRunway, Obstacle obstacle, boolean towards) {

		if (obstacle == null || (Math.abs(obstacle.distanceFromCenterline) > 75 && (obstacle.distanceFromThreshold > 60 || obstacle.distanceFromThreshold > logicalRunway.tora + 60))) {
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
			//Take off away/ land away from logical runway start

			int blastAllowance = logicalRunway.runway.blastAllowance;

			int RESA_StripEnd = RESA + logicalRunway.getStripEnd();
			int blast_DThreshold = blastAllowance + logicalRunway.displacedThreshold;
			if (RESA_StripEnd > blast_DThreshold) {

				newTora = logicalRunway.tora - fromThresh - (RESA_StripEnd);
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

			newLda = logicalRunway.lda - fromThresh - obstacle.length - ALSWidth - logicalRunway.getStripEnd();
			lastCalculationBreakdown.append("LDA  = Original LDA - Distance from Threshold - Slope Calculation - Strip End\n");
			lastCalculationBreakdown.append("     = "+logicalRunway.lda+" - "+fromThresh+" - "+DEFAULT_ANGLE_OF_DESCENT +"*"+ obstacle.height+" - "+logicalRunway.getStripEnd()+"\n");
			lastCalculationBreakdown.append("     = "+newLda+"\n");
		}

//		if(!towards) {
//			newTora -= obstacle.length;
//			newToda -= obstacle.length;
//			newAsda -= obstacle.length;
//			newLda -= obstacle.length;
//		}
		
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
	
	static class BreakdownWrapper {
		public String breakdownStr;
	}
}