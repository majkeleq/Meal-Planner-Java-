package mealplanner;

import java.util.List;

public class DbPlanDao implements PlanDao {

    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";
    private static final String CREATE_PLAN_TABLE = """
            CREATE TABLE IF NOT EXISTS plan (
            day INT,
            meal VARCHAR(20),
            category VARCHAR(10),
            meal_id INT,
            CONSTRAINT fk_meal FOREIGN KEY (meal_id)
            REFERENCES meals(meal_id)
            );""";
    private static final String DELETE_PLAN_CONTENT = "DELETE FROM plan";
    private static final String INSERT_MEAL = """
            INSERT INTO plan (day, meal, category, meal_id)
            VALUES (%d, '%s', '%s', %d)""";
    private static final String SELECT_DAY = """
            SELECT m.*
            FROM plan p JOIN meals m ON (p.meal_id = m.meal_id)
             WHERE p.day = %d""";
    private static final String SELECT_INGREDIENTS = "SELECT * FROM ingredients WHERE meal_id = %d";
    private final DbClient dbClient;

    public DbPlanDao() {
        this.dbClient = new DbClient(DB_URL, USER, PASS);
        dbClient.run(CREATE_PLAN_TABLE);
    }

    @Override
    public void add(Meal meal, int day) {
        dbClient.run(String.format(INSERT_MEAL, day, meal.getName(), meal.getCategory(), meal.getId()));
    }

    @Override
    public void delete() {
        dbClient.run(DELETE_PLAN_CONTENT);
    }

    @Override
    public List<Meal> findByDay(int day) {

        return dbClient.selectForList(String.format(SELECT_DAY, day));
    }
}
