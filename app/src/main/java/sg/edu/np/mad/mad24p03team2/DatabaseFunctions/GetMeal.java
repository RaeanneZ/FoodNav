package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;

/**
 * GetMeal
 * DB-Controller to handle UI-request to get all food of specified meal
 */
public class GetMeal extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    MealDB mealDB = null;
    FoodDB foodDB = null;
    FoodItemClass foodItem = null;
    MealClass mealClass = null;
    boolean isSuccess = false;

    public GetMeal(Context appContext){
        // Later will pass in ApplicationContext
        this.mealDB = new MealDB(appContext);
        this.foodDB = new FoodDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public GetMeal(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }


    @Override
    protected String doInBackground(String... strings) {
        String mealName = strings[0];
        int accID = Integer.parseInt(strings[1]);

        ResultSet foodResultSet = null;

        //get record OF the DAY for the accID and mealName
        ResultSet mealResultSet = mealDB.GetRecord(mealName, accID);

        try {
            mealClass = new MealClass(mealName);

            if(mealResultSet != null) {
                //if theres is food item
                while (mealResultSet.next()) {
                    try {
                        mealClass.setId(mealResultSet.getInt("MealID"));
                        // Get the foodData from Meal data
                        foodResultSet = foodDB.GetRecordById(mealResultSet.getInt("FoodID"));
                        if (foodResultSet.next()) {
                            // Change it into an object
                            // int id, String name, double calories, double serving_size_g, double fat_total_g, double protein_g, double carbohydrates_total_g)
                            foodItem = new FoodItemClass(foodResultSet.getInt("FoodID"),
                                    foodResultSet.getString("Name"),
                                    foodResultSet.getDouble("Calories"),
                                    foodResultSet.getDouble("ServingSize"),
                                    foodResultSet.getDouble("Fats"),
                                    foodResultSet.getDouble("Sugar"),
                                    foodResultSet.getDouble("Carbohydrates"),
                                    foodResultSet.getString("Recommend"));

                            mealClass.getSelectedFoodList().put(foodItem, mealResultSet.getInt("Quantity"));
                        }

                    } catch (Exception e) {
                        Log.d("GetMeal: Food", e.getMessage());
                    } finally {
                        if (foodResultSet != null) {
                            foodResultSet.close();
                        }
                    }
                }
            }
            // Save db return for global access
            SingletonTodayMeal.getInstance().AddMeal(mealClass);
            isSuccess = true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try{
                if(mealResultSet != null) {
                    mealResultSet.close();
                }
            } catch (Exception e) { Log.d("Get Meal", "ResultSet unable to close"); }
        }

        return mealName;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess, s, GetMeal.class);
        }
    }

    // IGNORE --------------------------------------------------------------------------------------

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    // IGNORE --------------------------------------------------------------------------------------
}

