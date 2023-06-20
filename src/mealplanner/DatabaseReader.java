package mealplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabaseReader {
    public DatabaseReader() {
    }

    public void readMeals(Map<String, HashMap<Integer, String>> menu, Statement statement)
            throws SQLException {
        menu.clear();
        ResultSet rs = statement.executeQuery("select * from meals");
        while (rs.next()) {
            String category = rs.getString("category");
            HashMap<Integer, String> meals = menu.getOrDefault(category, new HashMap<>());
            meals.put(rs.getInt("meal_id"), rs.getString("meal"));
            menu.put(category, meals);
        }
    }

    public void readIngredients(Map<Integer, LinkedHashSet<String>> ingredients, Statement statement)
            throws SQLException {
        ingredients.clear();
        ResultSet rs = statement.executeQuery("select * from ingredients");
        while (rs.next()) {
            LinkedHashSet<String> mealIngredients = ingredients.getOrDefault(rs.getInt("meal_id"), new LinkedHashSet<>());
            mealIngredients.add(rs.getString("ingredient"));
            ingredients.put(rs.getInt("meal_id"), mealIngredients);
        }
    }
}
