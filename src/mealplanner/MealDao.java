package mealplanner;

import java.util.List;

public interface MealDao {
    List<Meal> findAll();
    Meal findById(int id);
    int findLastId();
    void add(Meal meal);

}
