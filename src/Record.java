// Assignment: ISU
// Name: Max Luo and Dami Peng
// Date: June 22, 2022
// Description: a class for scores

public class Record implements Comparable<Record> {

    // declare variables
    private int attempt;
    private int score;

    public Record(int attempt, int score) {
        this.attempt = attempt;
        this.score = score;
    }

    @Override
    public int compareTo(Record o) {
        return o.score - this.score;
    }

    public String toString() {
        return attempt + " " + score;
    }

    // GETTERS
    public int getattempt() {
        return this.attempt;
    }

    public int getscore() {
        return this.score;
    }

}
