package mealplanner;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MealAction {
    public Map<String, List<String>> getBreakfast() {
        return breakfast;
    }

    public Map<String, List<String>> getLunch() {
        return lunch;
    }

    public Map<String, List<String>> getDinner() {
        return dinner;
    }

    private final Map<String, List<String>> breakfast;
    private final Map<String, List<String>> lunch;
    private final Map<String, List<String>> dinner;

    public MealAction(Map<String, List<String>> breakfast, Map<String, List<String>> lunch, Map<String, List<String>> dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

}
