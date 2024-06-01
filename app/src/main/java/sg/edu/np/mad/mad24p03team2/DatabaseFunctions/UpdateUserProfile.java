package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class UpdateUserProfile extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;
    DietPlanOptDB dietPlanOptDB = null;
    AccountDB accountDB = null;

    public UpdateUserProfile(Context appContext){
        // Later will pass in ApplicationContext
        this.accountDB = new AccountDB(appContext);
        this.dietPlanOptDB = new DietPlanOptDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public UpdateUserProfile(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    @Override
    protected String doInBackground(String... inputs) {
        try{
            String email = inputs[0];
            String dietPlanOpt = inputs[1];
            String gender = inputs[2];
            String birthDate = inputs[3];
            String height = inputs[4];
            String weight = inputs[5];

            ResultSet resultset = null;
            String dietPlanid = "1";

            try{
                resultset = dietPlanOptDB.GetRecord(dietPlanOpt, gender);
                if (resultset.next()) {
                    dietPlanid = resultset.getString("DietPlanID");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SingletonSession.getInstance().UpdateProfile(gender, sdf.parse(birthDate), Float.parseFloat(height), Float.parseFloat(weight));
                }
            } catch(Exception e){
                Log.d("UpdateUserProfile", "Something went wrong: " + e.getMessage());
            }

            isSuccess = accountDB.UpdateRecord(email, dietPlanid, gender, birthDate, height, weight);
        }
        catch (Exception e){
            isSuccess = false;
            z = e.getMessage();
        }

        return z;
    }

    @Override
    protected void onPostExecute(String s) {
        // Notify all listeners
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
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
