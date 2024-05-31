package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealClass {
    // Attributes
    private String mealName;
    private Map<FoodItemClass, Integer> selectedFoodList ;
    private float bloodSugar;
    private String timestamp;

    // Constructor
    public MealClass(String mealName) {
        this.mealName = mealName;
        this.selectedFoodList = new HashMap<FoodItemClass, Integer>();
    }

    // Getters and Setters
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

    public float getBloodSugar() {
        return bloodSugar;
    }
    public void setBloodSugar(float bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
