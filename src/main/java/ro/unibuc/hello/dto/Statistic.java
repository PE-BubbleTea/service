package ro.unibuc.hello.dto;

public class Statistic {

    private final String title;
    private final String description;
    private final long statistic;

    public Statistic(String title, String description, long statistic) {
        this.title = title;
        this.description = description;
        this.statistic = statistic;
    }

    public long getStatistic() {
        return statistic;
    }

    public String getTitle() {return title;}

    public String getDescription() {return description;}

}
