package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonBloodSugarResult;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonDietConstraints;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonDietPlanResult;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;

/**
 * GetCurrentUserProfile
 * Model-Controller that handles UI-request to retrieve all currentUserProfile at Login
 */
public class GetCurrentUserProfile extends AsyncTaskExecutorService<String, String , String> {

    private final String TAG = "GetCurrentUserProfile";
    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;

    // Account DB
    AccountDB accountDB = null;

    MealDB mealDB = null;
    FoodDB foodDB = null;
    FoodItemClass foodItem = null;
    MealClass mealClass = null;
    DietPlanOptDB dietPlanOptDB = null;
    BloodSugarTrackingDB bloodSugarTrackingDB = null;
    DietConstraintDB dietConstraintDB = null;


    private ArrayList<IDBProcessListener> listeners = new ArrayList<IDBProcessListener>();

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0];
        ResultSet resultSet = null;

        try{
            resultSet = accountDB.GetRecord(email);
            if (resultSet.next()) {

                // Store info locally
                int accID = resultSet.getInt("AccID");
                SingletonSession.getInstance().GetAccount().setId(accID);
                SingletonSession.getInstance().SetUpAccount(resultSet.getString("Name"),
                        resultSet.getString("AccEmail"));

                // Update the user profile
                String gender = resultSet.getString("Gender");

                SingletonSession.getInstance().UpdateProfile(gender,
                        resultSet.getDate("BirthDate"), resultSet.getInt("Height"),
                        resultSet.getFloat("Weight"));
                SingletonSession.getInstance().SetBloodSugarTracking(resultSet.getString("TrackBloodSugar"));

                //loadDietPlanOption
                getDietPlanOption("Diabetic Friendly", gender);

                //Load meal Logs
                getMeal("Breakfast", accID);
                getMeal("Lunch", accID);
                getMeal("Dinner", accID);

                //load glucose logs
                getTodayGlucoseReading("Breakfast");
                getTodayGlucoseReading("Lunch");
                getTodayGlucoseReading("Dinner");

                //load diet constraints
                getDietConstraint(accID);

              }

            isSuccess = true;
        }
        catch (Exception e) {
            z = "Fail to load user application details";
            Log.d(TAG,"Exception = "+e.getMessage());
        } finally {
            try{
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) { Log.d(TAG, "ResultSet unable to close"); }
        }

        return z;
    }

    private void getDietConstraint(int accID){
        ResultSet dietTypeResultSet = dietConstraintDB.GetRecord(accID);
        ArrayList<String> dietContraintList = new ArrayList<String>();

        try {
            //no constraint
            if (dietTypeResultSet == null) {
                SingletonDietConstraints.getInstance().setDietProfile(dietContraintList);
            }
            // If there are any diet constraints
            while (dietTypeResultSet.next()) {
                try{
                    // Append it to a list to contain all constraints
                    dietContraintList.add(dietTypeResultSet.getString("DietType"));
                }
                catch (Exception e) {
                    Log.d("GetDietConstraint: ", e.getMessage());
                }
            }

            // Save db return for global access
            SingletonDietConstraints.getInstance().setDietProfile(dietContraintList);

        }
        catch (SQLException e) {
            z = "Fail to get Diet Constraints from DB";
            Log.d(TAG, "Exception msg = "+e.getMessage());
        }
        finally {
            try{
                if(dietTypeResultSet != null) {
                    dietTypeResultSet.close();
                }
            } catch (Exception e) { Log.d("Get Diet Constraint", "ResultSet unable to close"); }
        }

    }

    private void getTodayGlucoseReading(String mealName){
        ResultSet resultSet = bloodSugarTrackingDB.GetRecord(
                SingletonSession.getInstance().GetAccount().getId(), mealName);
        try {
            // Return empty String if there is no results
            if(resultSet == null || (!resultSet.isBeforeFirst() && resultSet.getRow() == 0)) {
                Log.d("GetTodayBloodSugarByMeal","No record of Blood Sugar recorded for "+mealName);

            }else if (resultSet.next()) {
                // Make sure there is a record
                BloodSugarClass bloodSugarObj = new BloodSugarClass(resultSet.getInt("BloodSugarID"),
                        resultSet.getFloat("BloodSugarReading"),
                        resultSet.getString("MealName"), resultSet.getString("Timestamp"));

                // Add the result into the singleton for global access
                SingletonBloodSugarResult.getInstance().addTodayBloodSugarByMeal(bloodSugarObj);
            }
        }
        catch (SQLException e) {
            z = "Fail to fetch user glucose reading from DB";
            Log.d(TAG, "Exception msg = "+e.getMessage());
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
    }

    private void getDietPlanOption(String name, String gender) throws SQLException {
        ResultSet resultSet = dietPlanOptDB.GetRecord(name, gender);

        try {
            // If there is a result
            if (resultSet.next()) {
                int id = resultSet.getInt("DietPlanID");
                name = resultSet.getString("Name");
                int reccCarbIntake = resultSet.getInt("ReccCarbIntake");
                int reccSugarIntake = resultSet.getInt("ReccSugarIntake");
                int reccFatsIntake = resultSet.getInt("ReccFatsIntake");
                gender = resultSet.getString("Gender");
                int calories = resultSet.getInt("Calories");

                DietPlanClass dietPlan = new DietPlanClass(id, name, reccCarbIntake, reccSugarIntake, reccFatsIntake, gender, calories);

                SingletonDietPlanResult.getInstance().setDietPlan(dietPlan);
            }
        } catch (Exception e) {
            z = "Fail to fetch user diet plan's option";
            Log.d(TAG, "Exception msg = "+e.getMessage());
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    private void getMeal(String mealName, int accID){
        //get record OF the DAY for the accID and mealName
        ResultSet mealResultSet = mealDB.GetRecord(mealName, accID);
        ResultSet foodResultSet = null;

        try {
            mealClass = new MealClass(mealName);
            if(mealResultSet != null) {
                //if theres is food item
                while (mealResultSet.next()) {
                    try {
                        mealClass.setId(mealResultSet.getInt("MealID"));
                        // Get the foodData from Meal data
                        foodResultSet = foodDB.GetRecordById(mealResultSet.getInt("FoodID"));
                        if (foodResultSet.next()) {
                            // Change it into an object
                            // int id, String name, double calories, double serving_size_g, double fat_total_g, double protein_g, double carbohydrates_total_g)
                            foodItem = new FoodItemClass(foodResultSet.getInt("FoodID"),
                                    foodResultSet.getString("Name"),
                                    foodResultSet.getDouble("Calories"),
                                    foodResultSet.getDouble("ServingSize"),
                                    foodResultSet.getDouble("Fats"),
                                    foodResultSet.getDouble("Sugar"),
                                    foodResultSet.getDouble("Carbohydrates"),
                                    foodResultSet.getString("Recommend"));

                            mealClass.getSelectedFoodList().put(foodItem, mealResultSet.getInt("Quantity"));
                        }

                    } catch (Exception e) {
                        z = "Fail to get food items for "+mealName+"from DB";
                        Log.d(TAG, e.getMessage());
                    } finally {
                        if (foodResultSet != null) {
                            foodResultSet.close();
                        }
                    }
                }
            }
            // Save db return for global access
            SingletonTodayMeal.getInstance().AddMeal(mealClass);
        }
        catch (SQLException e) {
            Log.d(TAG, "Exception msg = "+e.getMessage());
        }
        finally {
            try{
                if(mealResultSet != null) {
                    mealResultSet.close();
                }
            } catch (Exception e) { Log.d("Get Meal", "ResultSet unable to close"); }
        }
    }

    public GetCurrentUserProfile(Context appContext){
        this.accountDB = new AccountDB(appContext);

        this.mealDB = new MealDB(appContext);
        this.foodDB = new FoodDB(appContext);
        this.dietPlanOptDB = new DietPlanOptDB(appContext);
        this.bloodSugarTrackingDB = new BloodSugarTrackingDB(appContext);
        this.dietConstraintDB = new DietConstraintDB(appContext);

        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public GetCurrentUserProfile(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        listeners.add(listener);
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess, s, GetCurrentUserProfile.class);
        }
    }
}
