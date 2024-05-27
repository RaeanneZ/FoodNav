package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetMeal extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    MealDB mealDB = null;

    public GetMeal(Context appContext){
        // Later will pass in ApplicationContext
        this.mealDB = new MealDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }


    @Override
    protected String doInBackground(String... strings) {
        ResultSet resultSet = mealDB.GetRecord(strings[0]);
        try {
            // If there are meals recorded today
            if(resultSet.next()) {
                // Get the meal data
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
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

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
