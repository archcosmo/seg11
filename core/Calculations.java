package core;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    String lastCalculationBreakdown;

    //TODO: can take whole runway object (and calculate for each logical runway)
    public void calculateDistances(LogicalRunway runway, Obstacle obstacle, int blastAllowance /* = -1 */) {
        int newTora;
        int newToda;
        int newAsda;
        int newLda;

        //TODO: if obstacle not inside cleared and graded area, no need to redeclare distances

		double obstacleMidXPosition = obstacle.left + ((obstacle.right - obstacle.left) / 2.0);
        //Calculate which half of runway, the obstacle is on
        if (obstacleMidXPosition > (runway.tora / 2.0)) {
            //Take off / land before obstacle
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            int ALSDistance = runway.tora - obstacle.left - ALSWidth;
            newTora = runway.tora - runway.stopwayLength - Math.max(DEFAULT_RESA, ALSDistance);
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacle.left -(DEFAULT_RESA + runway.stopwayLength);
        } else {
            //Take off / land after obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
            newTora = runway.tora - obstacle.left;
            newToda = runway.toda - obstacle.left - runway.clearwayLength;
            newAsda = runway.asda - obstacle.left - runway.stopwayLength;
            //TODO: displaced thresholds
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            newLda = runway.tora - obstacle.left - Math.max(blastAllowance, runway.stopwayLength + Math.max(DEFAULT_RESA, ALSWidth));
        }
        //TODO: return new Threshold Class
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
}