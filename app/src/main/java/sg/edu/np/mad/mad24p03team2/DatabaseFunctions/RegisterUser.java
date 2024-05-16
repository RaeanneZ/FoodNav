package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class RegisterUser extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;

    // LoginInfo DB Class
    LoginInfoDB loginInfoDB = null;

    // Account DB
    AccountDB accountDB = null;

    private ArrayList<IDBProcessListener> listeners = new ArrayList<IDBProcessListener>();


    public RegisterUser(Context appContext){
        this.loginInfoDB = new LoginInfoDB(appContext);
        this.accountDB = new AccountDB(appContext);
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
            String name = inputs[0];
            String email = inputs[1];
            String password = inputs[2];
            isSuccess = (loginInfoDB.CreateRecord(email, password) && accountDB.CreateRecord(name, email));
        }
        catch (Exception e) {
            z = e.getMessage();
        }
        return z;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : listeners){
            listener.afterProcess(isSuccess);
        }
    }
}
