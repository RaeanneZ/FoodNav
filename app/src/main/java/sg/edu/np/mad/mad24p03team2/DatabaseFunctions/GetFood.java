package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;

public class GetFood extends AsyncTaskExecutorService<String, String , String> {
    ArrayList<IDBProcessListener> dbListeners = null;
    // Login Data Class
    LoginInfoDB loginInfoDB = null;

    public GetFood(Context appContext){
        // Later will pass in ApplicationContext
        this.loginInfoDB = new LoginInfoDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
