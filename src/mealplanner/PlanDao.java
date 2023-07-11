package mealplanner;

import java.util.List;

public interface PlanDao {
    void add(Meal meal, int day);
    void delete();
    List<Meal> findByDay(int day);
}
