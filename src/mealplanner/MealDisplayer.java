package mealplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MealDisplayer {
    private final Statement statement;

    public MealDisplayer(Statement statement) {
        this.statement = statement;
    }

    public void displayMeals(Scanner sc) throws SQLException {
        String category = chooseCategory(sc);
        DatabaseReader databaseReader = new DatabaseReader(statement);
        HashMap<Integer, String> menu = databaseReader.readCategory(category);
        Map<Integer, LinkedHashSet<String>> ingredients = databaseReader.readIngredients();
        if (menu.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.println("Category: " + category);
            menu.forEach((key, meal) -> {
                System.out.println();
                System.out.println("Name: " + meal);
                System.out.println("Ingredients:");
                ingredients.get(key).forEach(System.out::println);
                System.out.println();
            });
        }
    }

    private String chooseCategory(Scanner sc) {
        boolean isCategoryChoosen = false;
        String category = null;
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        while (!isCategoryChoosen) {
            category = sc.nextLine();
            switch (category) {
                case "breakfast", "lunch", "dinner" -> isCategoryChoosen = true;
                default -> System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
        return category;
    }

    public HashMap<Integer, String> getCategory(String category) throws SQLException {
        return new DatabaseReader(statement)
                .readCategory(category);
                //.values()
                //.stream()
                //.sorted()
                //.collect(Collectors.toList());
    }

}