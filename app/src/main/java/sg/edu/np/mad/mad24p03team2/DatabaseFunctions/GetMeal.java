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
        ResultSet mealResultSet = mealDB.GetRecord(mealName, accID);
        try {
            mealClass = new MealClass(mealResultSet.getString("MealName"));

            // If there are meals recorded today
            while (mealResultSet.next()) {
                try{
                    // Get the foodData from Meal data
                    foodResultSet =  foodDB.GetRecordById(mealResultSet.getInt("FoodID"));
                    // Change it into an object
                    foodItem = new FoodItemClass(foodResultSet.getInt("FoodID"), foodResultSet.getString("Name"), foodResultSet.getFloat("Calories"), foodResultSet.getFloat("Carbohydrates"), foodResultSet.getFloat("Protein"), foodResultSet.getFloat("Fats"), foodResultSet.getFloat("ServingSize"));
                    mealClass.getSelectedFoodList().put(foodItem, mealResultSet.getInt("Quantity"));
                }
                catch (Exception e) {
                    Log.d("GetMeal: Food", e.getMessage());
                }
                finally {
                    if(foodResultSet != null) {
                        foodResultSet.close();
                    }
                }

                // Optional: only if User tracks blood sugar level
                if (!Objects.equals(mealResultSet.getString("BloodSugar"), "")) {
                    mealClass.setBloodSugar(mealResultSet.getFloat("BloodSugar"));
                    mealClass.setTimestamp(mealResultSet.getString("TimeStamp"));
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
            listener.afterProcess(isSuccess);
        }
    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    protected ArrayList<FoodItemClass> doInBackground() {
        return null;
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {
        return null;
    }
    // IGNORE --------------------------------------------------------------------------------------
}
