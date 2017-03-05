package Model;

public class Runway 
{
	int angle;
	char designator;
    int stopwayLength, clearwayLength;
    public int tora, toda, asda, lda, displacedThreshold;
    int RESA, blastAllowance, stripEnd;

    public String getID() 
    {
    	return Integer.toString(angle / 10) + designator;
    }
}
