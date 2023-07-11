package mealplanner;

import java.util.List;

public class DbMealDao implements MealDao {
    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private static final String CREATE_MEALS_TABLE = """
            CREATE TABLE IF NOT EXISTS meals (
                meal_id INT PRIMARY KEY,
            meal VARCHAR(20),
            category VARCHAR(10)
            );""";
    private static final String CREATE_INGREDIENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id INT PRIMARY KEY,
            ingredient VARCHAR(20),
            meal_id INT,
            CONSTRAINT fk_meal FOREIGN KEY (meal_id)
            REFERENCES meals(meal_id)
            );""";
    private static final String SELECT_MEALS = "SELECT * FROM meals";
    private static final String SELECT_CATEGORY = "SELECT * FROM meals WHERE category = '%s'";
    private static final String SELECT_MEAL = "SELECT * FROM meals WHERE meal_id = %d";
    private static final String INSERT_MEAL = """
            INSERT INTO meals (meal_id, meal, category)
            VALUES (%d, '%s', '%s')""";
    private static final String INSERT_INGREDIENT = """
            INSERT INTO ingredients (ingredient_id, ingredient, meal_id)
            VALUES (%d, '%s', %d)""";
    private final DbClient dbClient;

    public DbMealDao() {
        this.dbClient = new DbClient(DB_URL, USER, PASS);
        dbClient.run(CREATE_MEALS_TABLE);
        dbClient.run(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public List<Meal> findAll() {
        return dbClient.selectForList(SELECT_MEALS);
    }

    @Override
    public List<Meal> findCategory(String category) {
        return dbClient.selectForList(String.format(SELECT_CATEGORY, category));
    }

    @Override
    public Meal findById(int id) {
        return dbClient.select(String.format(SELECT_MEAL, id));
    }

    @Override
    public int findLastId() {
        int lastId = 0;
        try {
            lastId = findAll().stream().map(Meal::getId).reduce(Integer::max).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

    @Override
    public void add(Meal meal) {
        dbClient.run(String.format(INSERT_MEAL, meal.getId(), meal.getName(), meal.getCategory()));

        meal.getIngredients().forEach(ingredient -> {
            int ingredientLastId = dbClient.selectLastIdFromTable("ingredient_id", "ingredients") + 1;
            dbClient.run(String.format(INSERT_INGREDIENT, ingredientLastId, ingredient.toLowerCase(), meal.getId()));
        });
    }
}
