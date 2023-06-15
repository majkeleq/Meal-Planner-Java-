package mealplanner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MealAdder extends MealAction {
    public MealAdder(Map<String, List<String>> breakfast, Map<String, List<String>> lunch, Map<String, List<String>> dinner) {
        super(breakfast, lunch, dinner);
    }

    public void chooseMealType(Scanner sc) {
        boolean toContinue = true;
        while (toContinue) {
            System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            switch (sc.nextLine().toLowerCase()) {
                case "breakfast" -> addMeal(sc, getBreakfast());
                case "lunch" -> addMeal(sc, getLunch());
                case "dinner" -> addMeal(sc, getDinner());
                case "exit" -> toContinue = false;
                default -> {System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                continue;}
            }
            toContinue = false;
        }
    }

    public void addMeal(Scanner sc, Map<String, List<String>> mealType) {
        /* NAME */
        String name;
        while (true) {
            System.out.println("Input the meal's name:");
            name = sc.nextLine();
            if (isEntryValid(name.replaceAll(" ",""))) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                break;
            }
        }

        /* INGREDIENTS */
        while (true) {
            System.out.println("Input the ingredients:");
            List<String> ingredients = Arrays.stream(sc.nextLine()
                            .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (ingredients.stream().map(e -> e.replaceAll(" ","")).anyMatch(this::isEntryValid)) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                mealType.put(name, ingredients);
                System.out.println("The meal has been added!");
                break;
            }
        }
    }

    public boolean isEntryValid(String entry) {
        return entry.isEmpty() || Arrays.stream(entry.split("")).anyMatch(ch -> !Character.isLetter(ch.charAt(0)));
    }
}
