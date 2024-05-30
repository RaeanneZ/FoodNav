package sg.edu.np.mad.mad24p03team2.ApplicationSetUp;

import android.app.Application;
import android.util.Log;

import java.sql.Connection;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.SingletonSession;

public class StartUp extends Application {
    private Connection con = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // Connection
        ConnectionClass connectionClass = new ConnectionClass();
        con = connectionClass.connectDatabase();
        connectionClass.ExecuteDatabase(con);

        if(con != null) {
            Log.d("STARTUP","Connection is established");
        } else {
            Log.d("STARTUP","Connection is not established");
        }
    }

    public Connection getConnection(){
        return con;
    }
}
