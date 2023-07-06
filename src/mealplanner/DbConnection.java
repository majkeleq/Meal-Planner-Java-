package mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";
    private static final String initializeMeals = """
                CREATE TABLE IF NOT EXISTS meals (
                    meal_id INT PRIMARY KEY,
                meal VARCHAR(20),
                category VARCHAR(10)
                );""";
    private static final String initializeIngridients = """
                CREATE TABLE IF NOT EXISTS ingredients (
                    ingredient_id INT PRIMARY KEY,
                ingredient VARCHAR(20),
                meal_id INT,
                CONSTRAINT fk_meal FOREIGN KEY (meal_id)
                REFERENCES meals(meal_id)
                );""";
    private static final String initializePlan = """
                CREATE TABLE IF NOT EXISTS plan (
                day INT,
                meal VARCHAR(20),
                category VARCHAR(10),
                meal_id INT,
                CONSTRAINT fk_meal FOREIGN KEY (meal_id)
                REFERENCES meals(meal_id)
                );""";

    private final Connection connection;

    public Statement getStatement() {
        return statement;
    }

    private final Statement statement;

    public DbConnection() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);
        this.statement = connection.createStatement();
        statement.executeUpdate(initializeMeals);
        statement.executeUpdate(initializeIngridients);
        statement.executeUpdate(initializePlan);
    }
}
