package mealplanner;

import java.util.*;

public class DbPlanDaoImplementation {
    private final DbPlanDao dbPlanDao = new DbPlanDao();
    private final Scanner sc = new Scanner(System.in);

    public DbPlanDaoImplementation() {
    }

    public void add(List<Meal> meals) {
        dbPlanDao.delete();
        addDay(1, meals);
        addDay(2, meals);
        addDay(3, meals);
        addDay(4, meals);
        addDay(5, meals);
        addDay(6, meals);
        addDay(7, meals);

    }

    private void addDay(int i, List<Meal> meals) {
        List<Meal> breakfast = meals.stream().filter(meal -> meal.getCategory().equals("breakfast")).toList();
        List<Meal> lunch = meals.stream().filter(meal -> meal.getCategory().equals("lunch")).toList();
        List<Meal> dinner = meals.stream().filter(meal -> meal.getCategory().equals("dinner")).toList();
        System.out.println(getDay(i));

        addDayCategory(i, breakfast);
        addDayCategory(i, lunch);
        addDayCategory(i, dinner);
        System.out.printf("Yeah! We planned the meals for %s.\n", getDay(i));
    }

    private void addDayCategory(int i, List<Meal> category) {
        String cat = category.get(0).getCategory();
        category.stream().map(Meal::getName).sorted(Comparator.naturalOrder()).forEach(System.out::println);
        System.out.printf("Choose the %s for %s from the list above:\n", cat, getDay(i));
        boolean isNameValid = false;
        Meal meal;
        while (!isNameValid) {
            String name = sc.nextLine();
            Optional<Meal> optionalMeal = category.stream().filter(meal1 -> meal1.getName().equals(name)).findFirst();
            isNameValid = optionalMeal.isPresent();
            if (isNameValid) {
                meal = optionalMeal.get();
                dbPlanDao.add(meal, i);
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        }

    }

    public void show() {
        showDay(1);
        showDay(2);
        showDay(3);
        showDay(4);
        showDay(5);
        showDay(6);
        showDay(7);
    }

    private void showDay(int day) {
        System.out.println(getDay(day));
        List<Meal> meals = dbPlanDao.findByDay(day);
        System.out.printf("Breakfast: %s\nLunch: %s\nDinner: %s\n", meals.get(0).getName(), meals.get(1).getName(),
                meals.get(2).getName());
    }

    private String getDay(int number) {
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