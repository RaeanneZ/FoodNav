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
    protected String doInBackground(String... strings) {
        Log.d("GetAllFood::DoInBckgnd", "String");
        ResultSet resultSet = foodDB.GetAllRecord();
        try {
            while (resultSet.next()) {
                foodItem = new FoodItemClass(resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
                foodItems.add(foodItem);

                Log.d("GetAllFood", "FoodITem Added to array List = "+this.foodItems.size());
            }

            Log.d("GetAllFood", "THERE IS NO FOOD RETURN FOR SEARCH");
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
        return "";
    }


//    public ArrayList<FoodItemClass> GetAll() {
//        Log.d("GetAllFood::DoInBckgnd to get arrayList", "isFoodItem init: "+this.foodItems);
//        ResultSet resultSet = foodDB.GetAllRecord();
//        try {
//            while (resultSet.next()) {
//                foodItem = new FoodItemClass(resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
//                foodItems.add(foodItem);
//
//                Log.d("GetAllFood", "FoodITem Added to array List = "+this.foodItems.size());
//            }
//
//            Log.d("GetAllFood", "THERE IS NO FOOD RETURN FOR SEARCH");
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            try{
//                if(resultSet != null) {
//                    resultSet.close();
//                }
//            } catch (Exception e) { Log.d("Get Food", "Resultset unable to close"); }
//        }
//        return foodItems;
//
//    }

//    @Override
//    protected ArrayList<FoodItemClass> doInBackground() {
//
//        Log.d("GetAllFood::DoInBckgnd to get arrayList", "isFoodItem init: "+this.foodItems);
//        ResultSet resultSet = foodDB.GetAllRecord();
//        try {
//            while (resultSet.next()) {
//                foodItem = new FoodItemClass(resultSet.getString("Name"), resultSet.getFloat("Calories"), resultSet.getFloat("Carbohydrates"), resultSet.getFloat("Protein"), resultSet.getFloat("Fats"), resultSet.getFloat("ServingSize"));
//                foodItems.add(foodItem);
//
//                Log.d("GetAllFood", "FoodITem Added to array List = "+this.foodItems.size());
//            }
//
//            Log.d("GetAllFood", "THERE IS NO FOOD RETURN FOR SEARCH");
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            try{
//                if(resultSet != null) {
//                    resultSet.close();
//                }
//            } catch (Exception e) { Log.d("Get Food", "Resultset unable to close"); }
//        }
//        return foodItems;
//    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }

    // Ignored -------------------------------------------------------------------------------------
    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {

        Log.d("GetAllFood::DoInBckgnd", "DietPlanClass");
        return null;
    }
    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        Log.d("GetAllFood::DoInBckgnd", "FoodItemClass");
        return null; }
    // Ignored -------------------------------------------------------------------------------------
}
