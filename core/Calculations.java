package core;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    String lastCalculationBreakdown;

    //TODO: if obstacle lies on stopway/clearway
    //TODO: move clearway and stopway
    public LogicalRunway calculateDistances(LogicalRunway runway, int stopwayLength, int clearway,
                                        Obstacle obstacle, int blastAllowance) {
        int newTora;
        int newToda;
        int newAsda;
        int newLda;

        double obstacleMidXPosition = obstacle.left + ((obstacle.right - obstacle.left) / 2.0);
        //TODO: if obstacle not inside cleared and graded area, no need to redeclare distances

        //Calculate which half of runway, the obstacle is on
        if (obstacleMidXPosition > (runway.tora / 2.0)) {
            //Take off / land before obstacle
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            int ALSDistance = runway.tora - obstacle.left - ALSWidth;
            newTora = runway.tora - stopwayLength - Math.max(DEFAULT_RESA, ALSDistance);
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacle.left -(DEFAULT_RESA + stopwayLength);
        } else {
            //Take off / land after obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
            newTora = runway.tora - obstacle.left;
            newToda = runway.toda - obstacle.left - clearway;
            newAsda = runway.asda - obstacle.left - stopwayLength;
            //TODO: displaced thresholds
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            newLda = runway.tora - obstacle.left - Math.max(blastAllowance, stopwayLength + Math.max(DEFAULT_RESA, ALSWidth));
        }
        return new LogicalRunway(runway.designator, newTora, newToda, newAsda, newLda);
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
}