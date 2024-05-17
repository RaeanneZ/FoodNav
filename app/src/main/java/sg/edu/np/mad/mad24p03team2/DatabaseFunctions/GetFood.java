package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetFood extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    ArrayList<FoodItemClass> foodItems;
    // Login Data Class
    FoodDB foodDB = null;
    boolean isSuccess = false;

    public GetFood(Context appContext){
        // Later will pass in ApplicationContext
        this.foodDB = new FoodDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
        this.foodItems = new ArrayList<FoodItemClass>();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }


    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        FoodItemClass foodItem;

        ResultSet resultSet = foodDB.GetSpecificRecord(name);

        try{
            if (resultSet.next()){
                foodItem = new FoodItemClass(resultSet.getInt("FoodID"), resultSet.getString("Name"), resultSet.getInt("Calories"), resultSet.getInt("Carbohydrates"), resultSet.getInt("Protein"), resultSet.getInt("Fats"), resultSet.getString("ServingSize"));
                foodItems.add(foodItem);
                isSuccess = true;
            }
        } catch(Exception e) {
          Log.d("GetSpecificFood", "Get specific food failed: " + e.getMessage());
        }

        return foodItems;
    }



    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {
        return null;
    }
    @Override
    protected ArrayList<FoodItemClass> doInBackground() {
        return null;
    }
    // IGNORE --------------------------------------------------------------------------------------
}
