package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.ApiHandler;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonFoodSearchResult;

public class GetFood extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    FoodItemClass foodItem;
    ArrayList<FoodItemClass> foodItems;
    // Login Data Class
    FoodDB foodDB = null;
    ApiHandler apiHandler = new ApiHandler();
    boolean isSuccess = false;

    public GetFood(Context appContext){
        // Later will pass in ApplicationContext
        this.foodDB = new FoodDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
        this.foodItems = new ArrayList<FoodItemClass>();
    }

    public GetFood(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("GETFOOD", "Im supposed to be here");
        String name = strings[0].toLowerCase();
        ResultSet resultSet = foodDB.GetRecord(name);
        try {
            // Add the food if its not there
            if(!resultSet.isBeforeFirst() && resultSet.getRow() == 0) {
                apiHandler.fetchNutritionInfo(name, foodDB);
            }
            resultSet = foodDB.GetRecord(name);
            while(resultSet.next()) {
                foodItem = new FoodItemClass(resultSet.getInt("FoodID"), resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
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

        SingletonFoodSearchResult.getInstance().setFoodItemList(foodItems);
        return name;
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }
}
