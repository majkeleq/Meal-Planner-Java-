package mealplanner;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
        List<String> meals = categoryMeals.values().stream().sorted().toList();
        meals.forEach(System.out::println);
        System.out.printf("Choose the %s for %s from the list above:\n", category, returnDay(day));
        while (!isValid) {
            meal = sc.nextLine();
            if (meals.contains(meal)) {
                isValid = true;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
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
}
