package mealplanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> breakfast = new HashMap<>();
        Map<String, List<String>> lunch = new HashMap<>();
        Map<String, List<String>> dinner = new HashMap<>();

        MealAdder mealAdder = new MealAdder(breakfast, lunch, dinner);
        MealDisplayer mealDisplayer = new MealDisplayer(breakfast, lunch, dinner);

        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> mealAdder.chooseMealType(sc);
                case "show" -> mealDisplayer.displayMeal();
                case "exit" -> {
                    toContinue = false;
                    System.out.println("Bye!");
                }
                default -> {
                }
            }
        }
    }

}