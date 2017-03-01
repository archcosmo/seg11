package core;

import java.util.ArrayList;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    private String lastCalculationBreakdown;

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
            newTora = fromThresh + logicalRunway.displacedThreshold - ALSWidth - logicalRunway.stripEnd; 
            newToda = newTora;
            newAsda = newTora;
            newLda = fromThresh - DEFAULT_RESA - logicalRunway.stripEnd;
        } else {
            //Take off away/ land over obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
            newTora = logicalRunway.tora - fromThresh - Math.max((DEFAULT_RESA + logicalRunway.stripEnd), (DEFAULT_BLAST_ALLOWANCE + logicalRunway.displacedThreshold));
            newToda = newTora + logicalRunway.clearwayLength;
            newAsda = newTora + logicalRunway.stopwayLength;
            newLda = logicalRunway.lda - fromThresh - ALSWidth - logicalRunway.stripEnd;
        }

        ArrayList<Integer> thresholds = new ArrayList<>();
        thresholds.add(newTora);
        thresholds.add(newToda);
        thresholds.add(newAsda);
        thresholds.add(newLda);

        return thresholds;
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
}