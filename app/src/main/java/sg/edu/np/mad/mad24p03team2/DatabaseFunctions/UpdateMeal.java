package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class UpdateMeal extends AsyncTaskExecutorService<String, String , String> {

    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;
    MealDB mealDB = null;

    public UpdateMeal(Context appContext){
        this.mealDB = new MealDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public UpdateMeal(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }


    protected String doInBackground(FoodItemClass foodItem, String... strings) {
        int accId = Integer.parseInt(strings[0]);
        String mealName = strings[1];
        int quantity = Integer.parseInt(strings[2]);

        // If there is record, update the record
        // If there is no record, create the record
        // If quantity is reduced to 0, delete the record
        try {
            isSuccess = mealDB.UpdateQuantity(accId, foodItem, mealName, quantity);
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
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    // IGNORE --------------------------------------------------------------------------------------
}
