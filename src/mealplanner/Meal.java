package mealplanner;

import java.util.List;

public class Meal {
    private int id;
    private String name;
    private List<String> ingredients;
    private String category;

    public Meal(int id, String name, List<String> ingredients, String category) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.category = category;
    }
    public Meal(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
    public void addIngredientsList(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    @Override
    public String toString() {
        String nameString = "Name: " + getName();
        StringBuilder ingredientsString = new StringBuilder("\nIngredients:\n");
        ingredients.forEach(ingredient -> {
            ingredientsString.append(ingredient + "\n");
        });
        return nameString + ingredientsString;
    }
}
