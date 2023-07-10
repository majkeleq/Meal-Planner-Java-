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
                    System.out.println("The meal has been added!");
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
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String category = sc.nextLine().toLowerCase();
        boolean isCategoryValid = false;
        while (!isCategoryValid) {
            isCategoryValid = category.equals("breakfast") || category.equals("lunch") || category.equals("dinner");
            if (isCategoryValid) {
                String finalCategory = category;
                List<Meal> categoryList = dbMealDao.findAll()
                        .stream()
                        .filter(meal -> meal.getCategory().equals(finalCategory))
                        .toList();
                if (categoryList.size() == 0) {
                    System.out.println("No meals found.");
                } else {
                    System.out.println("Category: " + category + "\n");
                    categoryList.forEach(meal -> System.out.println(meal.toString()));
                }
            } else {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                category = sc.nextLine().toLowerCase();
            }
        }

    }
}
