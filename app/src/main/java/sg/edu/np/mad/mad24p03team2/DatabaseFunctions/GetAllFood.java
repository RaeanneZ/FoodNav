package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.ResultSet;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetAllFood extends AsyncTaskExecutorService<String, String , String> {

    boolean isSuccess = false;
    FoodDB foodDB = null;
    ArrayList<IDBProcessListener> dbListeners = null;
    ArrayList<FoodItemClass> foodItems = null;

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
        int id;
        String name;
        int calories;
        int carb;
        int protein;
        int fat;
        String servingSize;

        try{
            ResultSet resultSet = foodDB.GetAllRecords();

            while (resultSet.next()) {
                id = resultSet.getInt("FoodID");
                name = resultSet.getString("Name");
                calories = resultSet.getInt("Calories");
                carb = resultSet.getInt("Carbohydrates");
                protein = resultSet.getInt("Protein");
                fat = resultSet.getInt("Fat");
                servingSize = resultSet.getString("ServingSize");

                FoodItemClass foodItem = new FoodItemClass(id, name, calories, carb, protein, fat, servingSize);
                foodItems.add(foodItem);
            }
        } catch (Exception e){ }

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
