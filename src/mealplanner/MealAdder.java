package mealplanner;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MealAdder {
    public MealAdder() {
    }

    public void chooseMealType(Scanner sc, Statement statement) throws SQLException {
        boolean toContinue = true;
        while (toContinue) {
            System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            String category = sc.nextLine().toLowerCase();
            switch (category) {
                case "breakfast", "dinner", "lunch" -> addMeal(sc, statement, category);
                case "exit" -> toContinue = false;
                default -> {
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    continue;
                }
            }
            toContinue = false;
        }
    }

    public void addMeal(Scanner sc, Statement statement, String category) throws SQLException {
        /* NAME */
        String name = null;
        String updateQuery = null;
        boolean isNameValid = false;
        while (!isNameValid) {
            System.out.println("Input the meal's name:");
            name = sc.nextLine();
            if (isEntryValid(name.replaceAll(" ", ""))) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                isNameValid = true;
            }
        }
        //
        int mealId = lastIndex("meals", "meal_id", statement) + 1;
        updateQuery = String.format("insert into meals (meal_id, meal, category) values (%d, '%s', '%s')"
                , mealId, name, category);
        statement.executeUpdate(updateQuery);
        /* INGREDIENTS */
        boolean areIngredientsAdded = false;
        while (!areIngredientsAdded) {
            System.out.println("Input the ingredients:");
            List<String> ingredients = Arrays.stream(sc.nextLine()
                            .split(","))
                    .map(String::trim)
                    .toList();
            if (ingredients.stream().map(e -> e.replaceAll(" ", "")).anyMatch(this::isEntryValid)) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                int ingredientId = lastIndex("ingredients", "ingredient_id", statement);
                for (String ingredient : ingredients) {
                    ingredientId += 1;
                    updateQuery = String.format("insert into ingredients (ingredient_id,ingredient," +
                            "meal_id) " +
                            "values " +
                            "(%d, '%s', %d)", ingredientId, ingredient, mealId);
                    statement.executeUpdate(updateQuery);
                }
                //mealType.put(name, ingredients);
                System.out.println("The meal has been added!");
                areIngredientsAdded = true;
            }
        }
    }

    private boolean isEntryValid(String entry) {
        return entry.isEmpty() || Arrays.stream(entry.split("")).anyMatch(ch -> !Character.isLetter(ch.charAt(0)));
    }

    private int lastIndex (String tableName, String columnName, Statement statement) throws SQLException {
        String query = String.format("select %s from %s order by %s desc limit 1;", columnName, tableName, columnName);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(columnName);
        } else {
            return 0;
        }
    }
}
