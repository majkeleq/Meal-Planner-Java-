package mealplanner;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        DbDaoImplementation dbDaoImplementation = new DbDaoImplementation();

        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, plan, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> dbDaoImplementation.addMeal();
                case "show" -> dbDaoImplementation.showMeals();
                case "plan" -> dbDaoImplementation.addPlan();
                case "exit" -> {
                    toContinue = false;
                    System.out.println("Bye!");
                }
                default -> {
                }
            }
        }
        //statement.executeUpdate("delete from ingredients");
        //statement.executeUpdate("delete from meals");
    }

}