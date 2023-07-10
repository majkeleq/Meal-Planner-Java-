package mealplanner;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        DbDaoImplementation dbDaoImplementation = new DbDaoImplementation();

        boolean toContinue = true;
        while (toContinue) {
            System.out.println("What would you like to do (add, show, exit)?");
            switch (sc.nextLine().toLowerCase()) {
                case "add" -> dbDaoImplementation.add();
                case "show" -> dbDaoImplementation.show();
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