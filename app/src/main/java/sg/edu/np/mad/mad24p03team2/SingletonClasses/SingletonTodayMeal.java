package sg.edu.np.mad.mad24p03team2.SingletonClasses;

import android.content.Context;

import java.util.List;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.ApplicationSetUp.StartUp;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetMeal;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealDB;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateMeal;

public class SingletonTodayMeal {
    private static final int BREAKFAST = 0;
    private static final int LUNCH = 1;
    private static final int DINNER = 2;
    private static final int OTHERS = 3;

    MealClass[] mealClassArray = new MealClass[4];
    UpdateMeal updateMeal = null;

    private static volatile SingletonTodayMeal INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private SingletonTodayMeal() { }

    // public static method to retrieve the singleton instance
    public static SingletonTodayMeal getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (SingletonTodayMeal.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new SingletonTodayMeal();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public void AddMeal(MealClass meal){
        if (meal.getMealName().compareTo("Breakfast") == 0) {
            mealClassArray[BREAKFAST] = meal;
        } else if (meal.getMealName().compareTo("Lunch") == 0) {
            mealClassArray[LUNCH] = meal;
        } else if(meal.getMealName().compareTo("Dinner") == 0) {
            mealClassArray[DINNER] = meal;
        } else {
            mealClassArray[OTHERS] = meal;
        }
    }
}
