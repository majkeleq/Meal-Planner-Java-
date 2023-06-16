package mealplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class MealDisplayer extends MealAction {
    public MealDisplayer(Map<String, List<String>> breakfast, Map<String, List<String>> lunch,
                         Map<String, List<String>> dinner) {
        super(breakfast, lunch, dinner);
    }


    public void displayMeal(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from meals");
        if (!rs.next()) {
            System.out.println("No meals saved. Add a meal first.");
        }
        if (getBreakfast().isEmpty()
                && getDinner().isEmpty()
                && getLunch().isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
        } else {
            displayMealsType(getLunch(), "lunch");
            displayMealsType(getBreakfast(), "breakfast");
            displayMealsType(getDinner(), "dinner");
            System.out.println();
        }
    }

    private void displayMealsType(Map<String, List<String>> menu, String type) {
        for (var meal : menu.entrySet()) {
            System.out.println();
            System.out.printf("Category: %s\n", type);
            System.out.printf("Name: %s\n", meal.getKey());
            System.out.println("Ingredients:");
            meal.getValue().forEach(System.out::println);
        }
    }
}