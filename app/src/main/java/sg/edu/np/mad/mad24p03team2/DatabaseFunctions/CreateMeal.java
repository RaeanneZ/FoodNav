package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class CreateMeal extends AsyncTaskExecutorService<String, String , String> {
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;
    MealDB mealDB = null;

    public CreateMeal(Context appContext){
        this.mealDB = new MealDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public CreateMeal(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    protected String doInBackground(FoodItemClass foodItem, String... strings) {
        String mealName = strings[0];
        String quantity = strings[1];
        String bloodSugar = strings[2];
        try {
            isSuccess = mealDB.CreateRecord(foodItem, mealName, Integer.parseInt(quantity), Float.parseFloat(bloodSugar));
        } catch (Exception e) {
            Log.d("Create Meal", "Error occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

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
