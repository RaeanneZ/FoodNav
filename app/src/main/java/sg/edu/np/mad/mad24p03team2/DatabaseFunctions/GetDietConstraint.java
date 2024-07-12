package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonDietConstraints;

/**
 * GetDietConstraint
 * DB-Controller to handle UI-request to get User's diet constraint/preference
 */
public class GetDietConstraint extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    DietConstraintDB dietConstraintDB = null;
    ArrayList<String> dietContraintList = null;
    boolean isSuccess = false;

    public GetDietConstraint(Context appContext){
        // Later will pass in ApplicationContext
        this.dietConstraintDB = new DietConstraintDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();

        // Instantiate the list to contain all diet constraints
        dietContraintList = new ArrayList<String>();
    }

    public GetDietConstraint(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }


    @Override
    protected String doInBackground(String... strings) {
        int accID = Integer.parseInt(strings[0]);

        // Get all diet constraints from database
        ResultSet dietTypeResultSet = dietConstraintDB.GetRecord(accID);
        dietContraintList.clear();

        try {
            //no constraint
            if (dietTypeResultSet == null) {
                SingletonDietConstraints.getInstance().setDietProfile(dietContraintList);
                return "";
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
            isSuccess = true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try{
                if(dietTypeResultSet != null) {
                    dietTypeResultSet.close();
                }
            } catch (Exception e) { Log.d("Get Diet Constraint", "ResultSet unable to close"); }
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess, s, GetDietConstraint.class);
        }
    }

    // IGNORE --------------------------------------------------------------------------------------

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }
}
