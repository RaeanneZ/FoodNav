package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.ResultSet;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetDietPlanOption extends AsyncTaskExecutorService<String, String , String> {

    int id = 0;
    String name = " ";
    int reccCarbIntake = 0;
    int reccProteinIntake = 0;
    int reccFatsIntake = 0;
    boolean isTracked = false;

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
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String trackBloodSugar = strings[1];
        ResultSet resultSet = dietPlanOptDB.GetRecord(name, trackBloodSugar);

        try{
            // If there is a result
            if(resultSet.next()){
                id = resultSet.getInt("DietPlanID");
                name = resultSet.getString("Name");
                reccCarbIntake = resultSet.getInt("ReccCarbIntake");
                reccProteinIntake = resultSet.getInt("ReccProteinIntake");
                reccFatsIntake = resultSet.getInt("ReccFatsIntake");
                trackBloodSugar =  resultSet.getString("TrackBloodSugar");

                if (trackBloodSugar.compareTo("T") == 0){
                    isTracked = true;
                } else {
                    isTracked = false;
                }
            }
        } catch( Exception e){}

        // TODO: RETURN ALL THE VARIABLES FROM RESULTSET
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
