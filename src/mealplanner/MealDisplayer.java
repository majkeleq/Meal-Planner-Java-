package mealplanner;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MealDisplayer extends MealAction {
    public MealDisplayer(Map<String, List<String>> breakfast, Map<String, List<String>> lunch,
                         Map<String, List<String>> dinner) {
        super(breakfast, lunch, dinner);
    }


    public void displayMeals(Statement statement) throws SQLException {
        Map<String, HashMap<Integer, String>> menu = new HashMap<>();
        Map<Integer, LinkedHashSet<String>> ingredients = new HashMap<>();
        DatabaseReader databaseReader = new DatabaseReader();
        databaseReader.readMeals(menu, statement);
        databaseReader.readIngredients(ingredients, statement);
        System.out.println();
        menu.forEach((key, meals) -> {
            System.out.println("Category: " + key);
            meals.forEach((key1, value) -> {
                System.out.println("Name: " + value);
                System.out.println("Ingredients:");
                //List<String> reversedIngredients = new ArrayList<>(ingredients.get(key1));
                //Collections.reverse(reversedIngredients);
                //reversedIngredients.forEach(System.out::println);
                ingredients.get(key1).forEach(System.out::println);
                System.out.println();
            });
        });
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
    /**
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
    /**
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