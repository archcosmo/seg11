package core;

public class Calculations {

    private String lastCalculationBreakdown;

    public void calculateDistances(int Threshold, int obstacle, int xPos, int yPos){
        //takes int, calculates and returns real number.
    	
    	calculateTORA();
    	calculateTODA();
    	calculateASDA();
    	calculateLDA();
    }
    
    public String getLastCalculationBreakdown() {
        return lastCalculationBreakdown;
    }
    
    int toda, tora, asda, lda, stopwayLength, clearway;
    
    /*
     * All calculations need functionality for either direction and
     * how to choose which calculation to use. 
     * Other values need to also be created/stored (not sure how this is handled by console)
     */
    public int calculateTORA() {
    	
    }
    
    /*
     * TODA and ASDA calculations use recalculated TORA value
     */
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
    		return (distance from threshold) - (RESA) - (strip end);
    	else 
    		return lda - (obstacleHeight*50) - (distance from threshold) - (strip end); 
    }
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