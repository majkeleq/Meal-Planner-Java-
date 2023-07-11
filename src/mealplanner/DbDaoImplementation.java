package mealplanner;


public class DbDaoImplementation {
    private final DbMealDaoImplementation dbMealDaoImplementation = new DbMealDaoImplementation();
    private final DbPlanDaoImplementation dbPlanDaoImplementation = new DbPlanDaoImplementation();


    public DbDaoImplementation() {
    }

    public void addMeal() {
        dbMealDaoImplementation.add();
    }

    public void showMeals() {
        dbMealDaoImplementation.show();
    }

    public void addPlan() {
        dbPlanDaoImplementation.add(dbMealDaoImplementation.getMeals());
        dbPlanDaoImplementation.show();
    }

    public void save() {
        dbPlanDaoImplementation.save();
    }
}
