package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.ResultSet;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetDietPlanOption extends AsyncTaskExecutorService<String, String , String> {

    int id = 0;
    String name = " ";
    boolean isSuccess = false;
    int reccCarbIntake = 0;
    int reccProteinIntake = 0;
    int reccFatsIntake = 0;
    String gender = " ";
    DietPlanClass dietPlan;

    ArrayList<IDBProcessListener> dbListeners = null;
    // Login Data Class
    DietPlanOptDB dietPlanOptDB = null;
    private ArrayList<IDBProcessListener> listeners = new ArrayList<IDBProcessListener>();


    public GetDietPlanOption(Context appContext){
        // Later will pass in ApplicationContext
        this.dietPlanOptDB = new DietPlanOptDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }
    public GetDietPlanOption(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    @Override
    protected DietPlanClass doInBackground(String name, String trackBloodSugar) {

        ResultSet resultSet = dietPlanOptDB.GetRecord(name, trackBloodSugar);

        try{
            // If there is a result
            if(resultSet.next()){
                id = resultSet.getInt("DietPlanID");
                name = resultSet.getString("Name");
                reccCarbIntake = resultSet.getInt("ReccCarbIntake");
                reccProteinIntake = resultSet.getInt("ReccProteinIntake");
                reccFatsIntake = resultSet.getInt("ReccFatsIntake");
                gender =  resultSet.getString("Gender");

                DietPlanClass dietPlan = new DietPlanClass(id, name, reccCarbIntake, reccProteinIntake, reccFatsIntake, gender);
                isSuccess = true;
            }
        } catch( Exception e){}

        return dietPlan;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess);
        }
    }

    // IGNORED -------------------------------------------------------------------------------------
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground() {
        return null;
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) { return null; }
    // IGNORED -------------------------------------------------------------------------------------
}
