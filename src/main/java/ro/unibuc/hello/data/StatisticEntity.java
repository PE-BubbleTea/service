package ro.unibuc.hello.data;


public class StatisticEntity {


        public String title;
        public String description;
        public float statistic;

        public StatisticEntity() {
        }

        public StatisticEntity(String title, String description, float statistic) {
            this.title = title;
            this.description = description;
            this.statistic = statistic;
        }

        @Override
        public String toString() {
            return String.format(
                    "Statistic[title='%s', description='%s', statistic='%.2f']",
                    title, description, statistic);
        }
}
