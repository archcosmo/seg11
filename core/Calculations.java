package core;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;
    private static final int DEFAULT_BLAST_ALLOWANCE = 300;

    String lastCalculationBreakdown;
    int toda, tora, asda, lda, stopwayLength, stopwayWidth, clearway;

    //TODO: if obstacle lies on stopway/clearway
    //TODO: move clearway and stopway
    public Threshold calculateDistances(Threshold threshold, int stopwayLength, int clearway,
                                        Obstacle obstacle, int blastAllowance) {
        int newTora;
        int newToda;
        int newAsda;
        int newLda;

        double obstacleMidXPosition = obstacle.left + ((obstacle.right - obstacle.left) / 2.0);
        //TODO: if obstacle not inside cleared and graded area, no need to redeclare distances

        //Calculate which half of runway, the obstacle is on
        if (obstacleMidXPosition > (threshold.tora / 2.0)) {
            //Take off / land before obstacle
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            int ALSDistance = threshold.tora - obstacle.left - ALSWidth;
            newTora = threshold.tora - stopwayLength - Math.max(DEFAULT_RESA, ALSDistance);
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacle.left -(DEFAULT_RESA + stopwayLength); //Strip-end + new RESA
        } else {
            //Take off / land after obstacle
            if (blastAllowance == -1) {
                blastAllowance = DEFAULT_BLAST_ALLOWANCE;
            }
            newTora = threshold.tora - obstacle.left;
            newToda = threshold.toda - obstacle.left - clearway;
            newAsda = threshold.asda - obstacle.left - stopwayLength;
            //TODO: displaced thresholds
            int ALSWidth = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            int ALSDistance = threshold.tora - obstacle.left - ALSWidth;
            newLda = threshold.tora - Math.max(blastAllowance, stopwayLength + Math.max(DEFAULT_RESA, ALSDistance));
        }
        return new Threshold(threshold.designator, newTora, newToda, newAsda, newLda);
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
}