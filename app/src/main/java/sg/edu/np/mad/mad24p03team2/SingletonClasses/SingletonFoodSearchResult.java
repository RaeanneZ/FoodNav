package sg.edu.np.mad.mad24p03team2.SingletonClasses;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;

public class SingletonFoodSearchResult {
    private FoodItemClass foodItem = null;
    private ArrayList<FoodItemClass> foodItemList;
    private static volatile SingletonFoodSearchResult INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private SingletonFoodSearchResult() {
        foodItem = new FoodItemClass();
    }

    // public static method to retrieve the singleton instance
    public static SingletonFoodSearchResult getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (SingletonFoodSearchResult.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new SingletonFoodSearchResult();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public ArrayList<FoodItemClass> getFoodSearchResult() {
        return foodItemList;
    }

    // Database return search result
    public void setFoodItemList(ArrayList<FoodItemClass> foodList) {
        foodItemList = foodList;
    }

}
