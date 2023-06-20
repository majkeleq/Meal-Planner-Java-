package mealplanner;

import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args)  throws SQLException{
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> breakfast = new HashMap<>();
        Map<String, List<String>> lunch = new HashMap<>();
        Map<String, List<String>> dinner = new HashMap<>();

        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "1111";

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();
        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS meals (
                    meal_id INT PRIMARY KEY,
                meal VARCHAR(20),
                category VARCHAR(10)
                );""");
        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS ingredients (
                    ingredient_id INT PRIMARY KEY,
                ingredient VARCHAR(20),
                meal_id INT,
                CONSTRAINT fk_meal FOREIGN KEY (meal_id)
                REFERENCES meals(meal_id)
                );""");

        MealAdder mealAdder = new MealAdder();
        MealDisplayer mealDisplayer = new MealDisplayer(breakfast, lunch, dinner);


        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> mealAdder.chooseMealType(sc, statement);
                case "show" -> mealDisplayer.displayMeals(sc, statement);
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