package mealplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MealPlanner {
    private final String DELETE_PLAN = "DELETE FROM plan";
    private final Statement statement;

    public MealPlanner(Statement statement) {
        this.statement = statement;
    }

    public void run() throws SQLException {
        statement.executeUpdate(DELETE_PLAN);
        planDay(1);
        planDay(2);
        planDay(3);
        planDay(4);
        planDay(5);
        planDay(6);
        planDay(7);
        getPlan();
    }

    private void planDay(int day) throws SQLException {
        System.out.println(returnDay(day));
        planMeal(day, "breakfast");
        planMeal(day, "lunch");
        planMeal(day, "dinner");
        System.out.printf("Yeah! We planned the meals for %s.\n\n", returnDay(day));
    }

    private void planMeal(int day, String category) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String meal;
        boolean isValid = false;
        HashMap<Integer, String> categoryMeals = new MealDisplayer(statement).getCategory(category.toLowerCase());
        categoryMeals.values().stream().sorted().forEach(System.out::println);
        System.out.printf("Choose the %s for %s from the list above:\n", category, returnDay(day));
        while (!isValid) {
            meal = sc.nextLine();
            if (categoryMeals.containsValue(meal)) {
                isValid = true;
                for (Map.Entry<Integer, String> entry : categoryMeals.entrySet()) {
                    if (entry.getValue().equals(meal)) {
                        addMealToPlan(day, entry.getKey(), meal, category);
                        break;
                    }
                }
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        }

    }

    private void addMealToPlan(int day, int id, String meal, String category) throws SQLException {
        statement.executeUpdate(String.format("insert into plan (day, meal, category, meal_id) values (%d, '%s', '%s', %d)",
                day, meal, category, id));
    }

    public void getPlan() throws SQLException {
        int day = -1;
        ResultSet rs = statement.executeQuery("select * from plan");
        while (rs.next()) {
            if (day != rs.getInt("day")) {
                day = rs.getInt("day");
                System.out.println();
                System.out.println(returnDay(day));
            }
            System.out.printf("%s: %s\n",
                    rs.getString("category").substring(0,1).toUpperCase() + rs.getString("category").substring(1) ,
                    rs.getString("meal"));
        }
    }
    private String returnDay(int number) {
        String day = null;
        switch (number % 7) {
            case 1 -> day = "Monday";
            case 2 -> day = "Tuesday";
            case 3 -> day = "Wednesday";
            case 4 -> day = "Thursday";
            case 5 -> day = "Friday";
            case 6 -> day = "Saturday";
            case 0 -> day = "Sunday";
        }
        return day;
    }

    public boolean isEmpty() throws SQLException {
        ResultSet rs = statement.executeQuery("select count(*) from plan");
        int rowCount = 0;
        if (rs.next()) {
            rowCount = rs.getInt(1);
        }
        return rowCount != 0 ? false : true;
    }

    public HashMap<String, Integer> getShoppingList() throws SQLException {
        ResultSet rs = statement.executeQuery("select * from plan");
        List<Integer> mealIDs = new ArrayList<>();
        while (rs.next()) {
            mealIDs.add(Integer.valueOf(rs.getString("meal_id")));
        }
        HashMap<String, Integer> shoppingList = new HashMap<>();
        for(Integer id : mealIDs) {
            rs = statement.executeQuery(String.format("select ingredient from ingredients where meal_id = %d", id));
            while (rs.next()) {
                String ingredient = rs.getString(1);
                int count = shoppingList.getOrDefault(ingredient, 0);
                shoppingList.put(ingredient, count + 1);
            }
        }
        return shoppingList;
    }
}
