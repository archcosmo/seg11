package core;

public class Calculations {

    private static final int DEFAULT_RESA = 240;
    private static final int DEFAULT_ANGLE_OF_DESCENT = 50;

    String lastCalculationBreakdown;
    int toda, tora, asda, lda, stopwayLength, stopwayWidth, clearway;

    //TODO: if obstacle lies on stopway/clearway

    public Threshold calculateDistances(Threshold threshold, int stopwayLength, int stopwayWidth, int clearway,
                                        Obstacle obstacle, int obstacleXPos, int obstacleYPos) {
        /*
        takes int, calculates and returns real number.
        if obstacle closer to start of runway, calculate landing over and taking off after obstacle
        assuming obstaclePos is the position of the middle of the obstacle
        */

        int newTora;
        int newToda;
        int newAsda;
        int newLda;

        double obstacleMidXPosition = obstacleXPos + (obstacle.length / 2.0);

        //TODO: if obstacle not inside cleared and graded area, no need to redeclare distances

        //Calculate which half of runway, the obstacle is on
        if (obstacleMidXPosition > (threshold.tora / 2.0)) {
            //Take off / land before obstacle
            //TODO: use height of obstacle to calculate take-off (ALS)
            int ALS = DEFAULT_ANGLE_OF_DESCENT * obstacle.height;
            newTora = threshold.tora - stopwayLength - Math.max(DEFAULT_RESA, ALS); //Strip-end + (new RESA / threshold)
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacleXPos -(240 + stopwayLength); //Strip-end + new RESA
        } else {
            //Take off / land after obstacle
            //TODO: engine blast allowance (300-500m depending on aircraft)
            newTora = threshold.tora - obstacleXPos;
            newToda = threshold.toda - obstacleXPos - clearway;
            newAsda = threshold.asda - obstacleXPos - stopwayLength;
            //TODO: displaced thresholds
            //TODO: use height of obstacle to calculate take-off (ALS)
            int ALS = 0;
            newLda = threshold.tora - stopwayLength - Math.max(DEFAULT_RESA, ALS); //Strip-end + (new RESA / threshold)
        }
        return new Threshold(threshold.designator, newTora, newToda, newAsda, newLda);
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
}