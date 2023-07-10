package mealplanner;

import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        MealAdder mealAdder = new MealAdder(dbConnection.getStatement());
        MealDisplayer mealDisplayer = new MealDisplayer(dbConnection.getStatement());
        MealPlanner mealPlanner = new MealPlanner(dbConnection.getStatement());
        ShoppingListFileWriter shoppingListFileWriter = new ShoppingListFileWriter();

        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> mealAdder.chooseMealType(sc);
                case "show" -> mealDisplayer.displayMeals(sc);
                case "plan" -> mealPlanner.run();
                case "save" -> {
                    if (!mealPlanner.isEmpty()) {
                        System.out.println("Input a filename:");
                        String name = sc.nextLine();
                        shoppingListFileWriter.writeShoppingList(mealPlanner.getShoppingList(), name);
                    } else {
                        System.out.println("Unable to save. Plan your meals first.");
                    }
                }
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