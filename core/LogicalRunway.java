package core;

public class LogicalRunway {

    String name;
    int stopwayLength, stopwayWidth;
    int clearway;
    int tora, toda, asda, lda;
    String designator;

    public LogicalRunway(String designator, int tora, int toda, int asda, int lda) {
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
    }
}
