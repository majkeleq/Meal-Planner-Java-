package mealplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DbDaoImplementation {
    private final DbMealDao dbMealDao = new DbMealDao();
    private final Scanner sc = new Scanner(System.in);


    public DbDaoImplementation() {
    }

    public void add() {
        boolean toContinue = true;
        while (toContinue) {
            System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            String category = sc.nextLine().toLowerCase();
            switch (category) {
                case "breakfast", "dinner", "lunch" -> {
                    Meal meal = readMeal(category);
                    meal.addIngredientsList(readIngredients());
                    dbMealDao.add(meal);
                    System.out.println("The meal has been added.");
                    toContinue = false;
                }
                case "exit" -> toContinue = false;
                default -> System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
    }

    private Meal readMeal(String category) {
        String name = null;
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
        return new Meal(dbMealDao.findLastId() + 1, name, category);
    }

    private List<String> readIngredients() {
        /* INGREDIENTS */
        List<String> ingredients = new ArrayList<>();
        boolean areIngredientsAdded = false;
        while (!areIngredientsAdded) {
            System.out.println("Input the ingredients:");
            ingredients = Arrays.stream(sc.nextLine()
                            .split(","))
                    .map(String::trim)
                    .toList();
            if (ingredients.stream().map(e -> e.replaceAll(" ", "")).anyMatch(this::isEntryValid)) {
                System.out.println("Wrong format. Use letters only!");
            } else {

                //mealType.put(name, ingredients);
                areIngredientsAdded = true;
            }
        }
        return ingredients;
    }

    private boolean isEntryValid(String entry) {
        return entry.isEmpty() || Arrays.stream(entry.split("")).anyMatch(ch -> !Character.isLetter(ch.charAt(0)));
    }

    public void show() {
        List<Meal> breakfast = dbMealDao.findAll()
                .stream()
                .filter(meal -> meal.getCategory().equals("breakfast"))
                .toList();
        List<Meal> lunch = dbMealDao.findAll()
                .stream()
                .filter(meal -> meal.getCategory().equals("lunch"))
                .toList();
        List<Meal> dinner = dbMealDao.findAll()
                .stream()
                .filter(meal -> meal.getCategory().equals("dinner"))
                .toList();
        if (breakfast.size() == 0 && lunch.size() == 0 && dinner.size() == 0) {
            System.out.println("No meals added");
        } else {
            if (breakfast.size() != 0) {
                System.out.println("Category: breakfast");
                breakfast.forEach(meal -> System.out.println(meal.toString()));
            }
            if (lunch.size() != 0) {
                System.out.println("Category: lunch");
                lunch.forEach(meal -> System.out.println(meal.toString()));
            }
            if (dinner.size() != 0) {
                System.out.println("Category: dinner");
                dinner.forEach(meal -> System.out.println(meal.toString()));
            }
        }

    }
}
