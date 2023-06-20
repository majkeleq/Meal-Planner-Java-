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
        //statement.executeUpdate("drop table if exists ingredients");
        //statement.executeUpdate("drop table if exists meals");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals (\n" +
                "    meal_id INT PRIMARY KEY,\n" +
                "\tmeal VARCHAR(20),\n" +
                "\tcategory VARCHAR(10)\n" +
                ");");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (\n" +
                "    ingredient_id INT PRIMARY KEY,\n" +
                "\tingredient VARCHAR(20),\n" +
                "\tmeal_id INT,\n" +
                "\tCONSTRAINT fk_meal FOREIGN KEY (meal_id)\n" +
                "\tREFERENCES meals(meal_id)\n" +
                ");");

        MealAdder mealAdder = new MealAdder();
        MealDisplayer mealDisplayer = new MealDisplayer(breakfast, lunch, dinner);


        boolean toContinue = true;
        while (toContinue) {

            //System.out.println(menu);
            //System.out.println(ingredients);
            System.out.println("What would you like to do (add, show, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> mealAdder.chooseMealType(sc, statement);
                case "show" -> mealDisplayer.displayMeals(statement);
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