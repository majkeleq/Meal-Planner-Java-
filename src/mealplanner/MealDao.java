package mealplanner;

import java.util.List;

public interface MealDao {
    List<Meal> findAll();
    List<Meal> findCategory(String category);
    Meal findById(int id);
    int findLastId();
    void add(Meal meal);

}
