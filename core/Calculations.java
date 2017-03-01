package core;

import java.util.ArrayList;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    private String lastCalculationBreakdown;

    //TODO: can take whole runway object (and calculate for each logical runway)
    public ArrayList<Integer> calculateDistances(LogicalRunway logicalRunway, Obstacle obstacle, int blastAllowance /* = -1 */) {
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
		double obstacleMidXPosition = obstacle.xPos + ((obstacle.width) / 2.0);
        //Calculate which half of runway, the obstacle is on
        if (obstacleMidXPosition > (logicalRunway.tora / 2.0)) {
            //Take off / land before obstacle
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            int ALSDistance = logicalRunway.tora - obstacle.xPos - ALSWidth;
            newTora = logicalRunway.tora - logicalRunway.stopwayLength - Math.max(DEFAULT_RESA, ALSDistance);
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacle.xPos -(DEFAULT_RESA + logicalRunway.stopwayLength);
        } else {
            //Take off / land after obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
            newTora = logicalRunway.tora - obstacle.xPos;
            newToda = logicalRunway.toda - obstacle.xPos - logicalRunway.clearwayLength;
            newAsda = logicalRunway.asda - obstacle.xPos - logicalRunway.stopwayLength;
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            newLda = logicalRunway.tora - obstacle.xPos - Math.max(blastAllowance, logicalRunway.stopwayLength + Math.max(DEFAULT_RESA, ALSWidth));
        }
        //TODO: return new Threshold Class
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