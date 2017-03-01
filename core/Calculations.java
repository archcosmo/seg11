package core;

import java.util.ArrayList;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    private StringBuilder lastCalculationBreakdown;

    //TODO: can take whole runway object (and calculate for each logical runway)
    public ArrayList<Integer> calculateDistances(LogicalRunway logicalRunway, Obstacle obstacle, int blastAllowance, String direction /* = -1 */) {
        int newTora;
        int newToda;
        int newAsda;
        int newLda;

        //TODO: if obstacle not inside cleared and graded area, no need to redeclare distances
		//if (obstacle.bottomYPos > topOfRunway || obstacle.topYPos < bottomOfRunway) {
		//	return logicalRunway.getThresholdClass
		//}


		//TODO: displaced thresholds
		//TODO: obstacle distance: from which end of runway?
//		double obstacleMidXPosition = obstacle.xPos + ((obstacle.width) / 2.0);
		int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
		
		int fromThresh = 0;
        if (logicalRunway.designator.endsWith("R")) fromThresh = obstacle.fromR;
        else fromThresh = obstacle.fromL;


        if (direction.equalsIgnoreCase("towards")) {//if (obstacleMidXPosition > (logicalRunway.tora / 2.0)) {
            //Take off towards/ land towards obstacle
        	lastCalculationBreakdown =  new StringBuilder();
        	
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
            newLda = fromThresh - DEFAULT_RESA - logicalRunway.getStripEnd();
            lastCalculationBreakdown.append("LDA  = Distance from Threshold - RESA - Strip End\n");
            lastCalculationBreakdown.append("     = "+fromThresh+" - "+DEFAULT_RESA+" - "+logicalRunway.getStripEnd()+"\n");
            lastCalculationBreakdown.append("     = "+newLda+"\n");
        } else {
            //Take off away/ land over obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
        	lastCalculationBreakdown =  new StringBuilder();
            
            newTora = logicalRunway.tora - fromThresh - Math.max((DEFAULT_RESA + logicalRunway.getStripEnd()), (DEFAULT_BLAST_ALLOWANCE + logicalRunway.displacedThreshold));
            String eq, eq2;
            if ((DEFAULT_RESA + logicalRunway.getStripEnd()) == Math.max((DEFAULT_RESA + logicalRunway.getStripEnd()), (DEFAULT_BLAST_ALLOWANCE + logicalRunway.displacedThreshold))) {
            	eq = "RESA - Strip End\n";
            	eq2 = DEFAULT_RESA + " - " + logicalRunway.getStripEnd()+"\n";
            }
            else {
            	eq = "Blast Protection - Displaced Threshold\n";
            	eq2 = DEFAULT_BLAST_ALLOWANCE + " - " + logicalRunway.displacedThreshold+"\n";
            }
            lastCalculationBreakdown.append("TORA = Original TORA - Distance from Threshold - "+eq);
            lastCalculationBreakdown.append("     = "+logicalRunway.tora+" - "+fromThresh+" - "+eq2);
            lastCalculationBreakdown.append("     = "+newTora+"\n");
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
}