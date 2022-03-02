package ro.unibuc.hello.data;


public class StatisticEntity {


        public String title;
        public String description;
        public long statistic;

        public StatisticEntity() {
        }

        public StatisticEntity(String title, String description, long statistic) {
            this.title = title;
            this.description = description;
            this.statistic = statistic;
        }

        @Override
        public String toString() {
            return String.format(
                    "Statistic[title='%s', description='%s', statistic='%o']",
                    title, description, statistic);
        }
}
