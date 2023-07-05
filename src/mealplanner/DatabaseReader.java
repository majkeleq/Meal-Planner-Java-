package mealplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabaseReader {
    private final Statement statement;
    public DatabaseReader(Statement statement) {
    this.statement = statement;
    }

    @Deprecated
    public Map<String, HashMap<Integer, String>> readMeals()
            throws SQLException {
        Map<String, HashMap<Integer, String>> menu = new HashMap<>();
        ResultSet rs = statement.executeQuery("select * from meals");
        while (rs.next()) {
            String category = rs.getString("category");
            HashMap<Integer, String> meals = menu.getOrDefault(category, new HashMap<>());
            meals.put(rs.getInt("meal_id"), rs.getString("meal"));
            menu.put(category, meals);
        }
        return menu;
    }

    public Map<Integer, LinkedHashSet<String>> readIngredients()
            throws SQLException {
        Map<Integer, LinkedHashSet<String>> ingredients = new HashMap<>();
        ResultSet rs = statement.executeQuery("select * from ingredients");
        while (rs.next()) {
            LinkedHashSet<String> mealIngredients = ingredients.getOrDefault(rs.getInt("meal_id"), new LinkedHashSet<>());
            mealIngredients.add(rs.getString("ingredient"));
            ingredients.put(rs.getInt("meal_id"), mealIngredients);
        }
        return ingredients;
    }

    public Map<String, HashMap<Integer, String>> readCategory(String category)
            throws SQLException {
        Map<String, HashMap<Integer, String>> menu = new HashMap<>();
        String query = String.format("select * from meals where category LIKE '%s'", category);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            HashMap<Integer, String> meals = menu.getOrDefault(category, new HashMap<>());
            meals.put(rs.getInt("meal_id"), rs.getString("meal"));
            menu.put(category, meals);
        }
        return menu;
    }
}
