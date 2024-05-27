package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.ApiHandler;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetFoodByID extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    FoodItemClass foodItem;
    // Login Data Class
    FoodDB foodDB = null;
    boolean isSuccess = false;

    public GetFoodByID(Context appContext){
        // Later will pass in ApplicationContext
        this.foodDB = new FoodDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    protected FoodItemClass doInBackground(int id) {
        ResultSet resultSet = foodDB.GetRecordById(id);
        try {
            // Add the food if its not there
            if(resultSet.next()) {
                foodItem = new FoodItemClass(resultSet.getInt("FoodID"), resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try{
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) { Log.d("Get Food", "ResultSet unable to close"); }
        }

        return foodItem;
    }


    @Override
    protected void onPostExecute(String s) {

    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }
    @Override
    protected ArrayList<FoodItemClass> doInBackground() {
        return null;
    }
    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {
        return null;
    }
    // IGNORE --------------------------------------------------------------------------------------
}
