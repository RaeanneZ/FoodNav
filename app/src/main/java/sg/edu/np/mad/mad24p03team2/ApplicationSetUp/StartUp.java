package sg.edu.np.mad.mad24p03team2.ApplicationSetUp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

import java.sql.Connection;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.SingletonSession;

public class StartUp extends Application implements Application.ActivityLifecycleCallbacks {
    private Connection con = null;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

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

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    // If App goes to the background
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    // When App is killed, close connection
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        try {
            con.close();
        } catch(Exception e) {
            Log.d("STARTUP", "Unable to close connection: " + e.getMessage());
        }
    }
}
