package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonBloodSugarResult;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

/**
 * GetTodayBloodSugarByMeal
 * DB-Controller to handle UI-request to get today's blood sugar of specified meal
 */
public class GetTodayBloodSugarByMeal extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    boolean isSuccess = false;

    // Login Data Class
    BloodSugarTrackingDB bloodSugarTrackingDB = null;

    public GetTodayBloodSugarByMeal(Context appContext){
        // Later will pass in ApplicationContext
        this.bloodSugarTrackingDB = new BloodSugarTrackingDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public GetTodayBloodSugarByMeal(Context appContext, IDBProcessListener listener){
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
        // Get current account ID and the mealName
        ResultSet resultSet = bloodSugarTrackingDB.GetRecord(
                SingletonSession.getInstance().GetAccount().getId(), mealName);
        try {
            // Return empty String if there is no results
            if(resultSet == null || (!resultSet.isBeforeFirst() && resultSet.getRow() == 0)) {
                Log.d("GetTodayBloodSugarByMeal","No record of Blood Sugar recorded for "+mealName);
                isSuccess = true;

            }else if (resultSet.next()) {
                // Make sure there is a record
                BloodSugarClass bloodSugarObj = new BloodSugarClass(resultSet.getInt("BloodSugarID"),
                        resultSet.getFloat("BloodSugarReading"),
                        resultSet.getString("MealName"), resultSet.getString("Timestamp"));

                // Add the result into the singleton for global access
                SingletonBloodSugarResult.getInstance().addTodayBloodSugarByMeal(bloodSugarObj);
                isSuccess = true;
            }
        }
        catch (SQLException e) {
            isSuccess = false;
            Log.d("GetTodayBloodSugarByMeal", "SQL error : "+e.getMessage());
        }
        finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            } catch (Exception e) {
                Log.d("Get Today Blood Sugar by Meal", "ResultSet unable to close");
            }
        }

        return mealName;
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess, s, GetTodayBloodSugarByMeal.class);
        }
    }
}
