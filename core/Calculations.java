package core;

public class Calculations {

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

        //Calculate which half of runway, the obstacle is on
        if (obstacleXPos > (threshold.tora / 2.0)) {
            //Take off / land before obstacle
            //TODO: use height of obstacle to calculate take-off (ALS)
            int ALS = 0;
            newTora = threshold.tora - stopwayLength - Math.max(240, ALS); //Strip-end + (new RESA / threshold)
            newToda = newTora;
            newAsda = newTora;
            newLda = obstacleXPos -(240 + stopwayLength); //Strip-end + new RESA
        } else {
            //Take off / land after obstacle
            //TODO: engine blast radius
            newTora = threshold.tora - obstacleXPos;
            newToda = threshold.toda - obstacleXPos - clearway;
            newAsda = threshold.asda - obstacleXPos - stopwayLength;
            //TODO: displaced thresholds
            //TODO: use height of obstacle to calculate take-off (ALS)
            int ALS = 0;
            newLda = threshold.tora - stopwayLength - Math.max(240, ALS); //Strip-end + (new RESA / threshold)
        }
        /*
        calculateTORA();
        calculateTODA();
        calculateASDA();
        calculateLDA();
        */
        return new Threshold(threshold.designator, newTora, newToda, newAsda, newLda);
    }

    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }


    /*
     * All calculations need functionality for either direction and
     * how to choose which calculation to use. 
     * Other values need to also be created/stored (not sure how this is handled by console)
     *
    public int calculateTORA() {

    }

    /*
     * TODA and ASDA calculations use recalculated TORA value
     *
    public int calculateTODA() {
        if (towards) return tora;
        else
            return tora + clearway;
    }

    public int calculateASDA() {
        if (towards) return tora;
        else
            return tora + stopwayLength;
    }

    public int calculateLDA() {
        if (towards)
            return (distance from threshold)-(RESA) - (strip end);
        else
        return lda - (obstacleHeight * 50) - (distance from threshold)-(strip end);
    }
    */
}

/*
Calculation information:
Landing only changes LDA
Take Off changes TORA/TODA/ASDA

Take Off towards/Landing towards
TORA:	
TODA:	(Recalculated) TORA
ASDA:	(Recalculated) TORA
LDA:	Distance from Threshold - RESA - Strip End


Take Off away/Landing over
TORA:	
TODA:	(Recalculated) TORA + CLEARWAY
ASDA:	(Recalculated) TORA + STOPWAY
LDA:	Original LDA - Slope Calculation - Distance from Threshold - Strip End

*/