package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.ApiHandler;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetAllFood extends AsyncTaskExecutorService<String, String , String> {

    boolean isSuccess = false;
    FoodDB foodDB = null;
    ArrayList<IDBProcessListener> dbListeners = null;
    FoodItemClass foodItem;
    ArrayList<FoodItemClass> foodItems = null;
    ApiHandler apiHandler = new ApiHandler();

    public GetAllFood(Context appContext){
        // Later will pass in ApplicationContext
        this.foodDB = new FoodDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
        this.foodItems = new ArrayList<FoodItemClass>();
    }

    public GetAllFood(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground() {
        ResultSet resultSet = foodDB.GetAllRecord();
        try {
            while (resultSet.next()) {
                foodItem = new FoodItemClass(resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
                foodItems.add(foodItem);
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
            } catch (Exception e) { Log.d("Get Food", "Resultset unable to close"); }
        }
        return foodItems;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }

    // Ignored -------------------------------------------------------------------------------------
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {
        return null;
    }
    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) { return null; }
    // Ignored -------------------------------------------------------------------------------------
}
