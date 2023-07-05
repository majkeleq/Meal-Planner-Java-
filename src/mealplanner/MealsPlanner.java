package mealplanner;

import java.sql.Statement;

public class MealsPlanner {
    private final Statement statement;
    public MealsPlanner(Statement statement) {
        this.statement = statement;
    }
}
