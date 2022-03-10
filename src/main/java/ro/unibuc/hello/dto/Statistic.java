package ro.unibuc.hello.dto;

public class Statistic {

    private final String title;
    private final String description;
    private final float statistic;

    public Statistic(String title, String description, float statistic) {
        this.title = title;
        this.description = description;
        this.statistic = statistic;
    }

    public float getStatistic() {
        return statistic;
    }

    public String getTitle() {return title;}

    public String getDescription() {return description;}

}
