package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class UpdateUserProfile extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;
    // Login Data Class
    AccountDB accountDB = null;

    public UpdateUserProfile(Context appContext){
        // Later will pass in ApplicationContext
        this.accountDB = new AccountDB(appContext);
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
            isSuccess = accountDB.UpdateRecord(email, dietPlanOpt, gender, birthDate, height, weight);
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
}
