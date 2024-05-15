package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

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

    public void registerListener(IDBProcessListener listener){
        listeners.add(listener);
    }


    public RegisterUser(LoginInfoClass loginInfoClass){
        this.loginInfoClass = loginInfoClass;
    }


    @Override
    protected String doInBackground(String... inputs)  {
        try{
            isSuccess = loginInfoClass.CreateRecord(inputs[0], inputs[1]);
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
