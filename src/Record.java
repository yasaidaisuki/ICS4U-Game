public class Record implements Comparable<Record> {

    // declare variables
    private String date;
    private int time;

    public Record(String date, int time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public int compareTo(Record o) {
        return o.time - this.time;
    }

    // GETTERS
    public String getDate() {
        return this.date;
    }

    public int getTime() {
        return this.time;
    }

}
