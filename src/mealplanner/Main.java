package mealplanner;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        DbDaoImplementation dbDaoImplementation = new DbDaoImplementation();

        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> dbDaoImplementation.addMeal();
                case "show" -> dbDaoImplementation.showMeals();
                case "plan" -> dbDaoImplementation.addPlan();
                case "save" -> dbDaoImplementation.save();
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