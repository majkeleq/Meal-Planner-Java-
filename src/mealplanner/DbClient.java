package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbClient {

    private final String DB_URL;
    private final String USER;
    private final String PASS;

    public DbClient(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public void run(String str) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Meal select(String query) {
        List<Meal> meals = selectForList(query);
        if (meals.size() == 1) {
            return meals.get(0);
        } else if (meals.size() == 0) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Meal> selectForList(String query) {
        List<Meal> meals = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("meal_id");
                    String name = rs.getString("meal");
                    String category = rs.getString("category");
                    Meal meal = new Meal(id, name, category);
                    meals.add(meal);
                }
            }
            meals.forEach(meal -> {
                String ingredientsQuery = String.format("SELECT * FROM ingredients WHERE meal_id = %d", meal.getId());
                List<String> ingredients = new ArrayList<>();
                try (ResultSet rs = statement.executeQuery(ingredientsQuery)) {
                    while (rs.next()) {
                        ingredients.add(rs.getString("ingredient"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                meal.addIngredientsList(ingredients);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    public int selectLastIdFromTable(String column, String table) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            String query = "SELECT max(%s) FROM %s";
            try (ResultSet rs = statement.executeQuery(String.format(query, column, table))) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
