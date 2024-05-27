package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import java.util.ArrayList;
import java.util.Map;

public class MealClass {
    // Attributes
    private int id;
    private String mealName;
    private Map<FoodItemClass, Integer> selectedFoodList;

    // Constructor
    public MealClass(String mealName) {
        this.mealName = mealName;
    }

    public MealClass(int id, String mealName, Map<FoodItemClass, Integer> selectedFoodList) {
        this.id = id;
        this.mealName = mealName;
        this.selectedFoodList = selectedFoodList;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Map<FoodItemClass, Integer> getSelectedFoodList() {
        return selectedFoodList;
    }

    public void setSelectedFoodList(Map<FoodItemClass, Integer> selectedFoodList) {
        this.selectedFoodList = selectedFoodList;
    }



    
}
