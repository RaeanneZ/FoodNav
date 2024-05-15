package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.Interfaces.IDBProcessListener;

public class RegisterUser extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;

    // Login Data Class
    LoginInfoClass loginInfoClass = null;


    private ArrayList<IDBProcessListener> listeners = new ArrayList<IDBProcessListener>();


    public RegisterUser(Context appContext){
        this.loginInfoClass = new LoginInfoClass(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public RegisterUser(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        listeners.add(listener);
    }


    @Override
    protected String doInBackground(String... inputs)  {
        try{
            String email = inputs[0];
            String password = inputs[1];
            isSuccess = loginInfoClass.CreateRecord(email, password);
        }
        catch (Exception e) {
            z = e.getMessage();
        }
        return z;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : listeners){
            Log.d("PostExecute Listner", "execution state = "+ isSuccess);
            listener.afterProcess(isSuccess);
        }
    }
}
