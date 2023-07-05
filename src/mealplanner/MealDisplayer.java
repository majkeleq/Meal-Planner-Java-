package mealplanner;

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
        Map<String, HashMap<Integer, String>> menu = databaseReader.readCategory(category);
        Map<Integer, LinkedHashSet<String>> ingredients = databaseReader.readIngredients();
        if (menu.getOrDefault(category, new HashMap<>()).isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.println("Category: " + category);
            menu.forEach((key, meals) -> meals.forEach((key1, value) -> {
                System.out.println();
                System.out.println("Name: " + value);
                System.out.println("Ingredients:");
                ingredients.get(key1).forEach(System.out::println);
                System.out.println();
            }));
        }
    }

    private String chooseCategory(Scanner sc) {
        boolean isGroupChoosen = false;
        String group = null;
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        while (!isGroupChoosen) {
            group = sc.nextLine();
            switch (group) {
                case "breakfast", "lunch", "dinner" -> isGroupChoosen = true;
                default -> System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
        return group;
    }

    /*public void displayMeal(){

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
     */
    /*
     * Metoda wyświetla posiłki i składniki z HashMapy
     * Metoda była używana w Stage 2 - bez użycia SQL
     */
    /*@Deprecated
    private void displayMealsType(Map<String, List<String>> menu, String type) {
        for (var meal : menu.entrySet()) {
            System.out.println();
            System.out.printf("Category: %s\n", type);
            System.out.printf("Name: %s\n", meal.getKey());
            System.out.println("Ingredients:");
            meal.getValue().forEach(System.out::println);
        }
    }*/
    /*
     * Metoda odczytywała posiłki i składniki bezpośrednio z zapytania SQL
     */
    /*private void displayMealsType(Statement statement, String mealType) throws SQLException {
        String returnMeals = String.format("select * from meals where category LIKE '%s'",mealType);
        ResultSet rs = statement.executeQuery(returnMeals);
        while (rs.next()) {
            System.out.println();
            System.out.printf("Category: %s\nName: %s\nIngredients:\n", mealType, rs.getString("meal"));
            String returnIngredients = String.format("select * from ingredients where meal_id = %d",rs.getInt(
                    "meal_id"));
        }
    }*/
}