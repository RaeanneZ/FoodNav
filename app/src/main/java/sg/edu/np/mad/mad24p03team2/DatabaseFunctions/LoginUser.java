package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.Interfaces.IDBProcessListener;

public class LoginUser extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean[] checks;
    ArrayList<IDBProcessListener> dbListeners = null;
    Boolean isValid;
    Boolean isExist;
    Integer isValidIdx = 0;
    Integer isExistIdx = 1;


    // Login Data Class
    LoginInfoClass loginInfoClass = null;

    public LoginUser(Context appContext){
        // Later will pass in ApplicationContext
        this.loginInfoClass = new LoginInfoClass(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public LoginUser(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        dbListeners.add(listener);
    }

    // This is to check password corresponds
    @Override
    protected String doInBackground(String... inputs) {
        try{
            checks = loginInfoClass.ValidateRecord(inputs[0], inputs[1]);
            isValid = checks[isValidIdx];
            isExist = checks[isExistIdx];
        }
        catch (Exception e){
            z = e.getMessage();
        }
        return z;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("LoginUser", "Notify all listeners!");
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isValid, isExist);
        }
    }
}
