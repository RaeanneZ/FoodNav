package sg.edu.np.mad.mad24p03team2.ApplicationSetUp;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionClass {
    String classes = "net.sourceforge.jtds.jdbc.Driver";
    public static String ip =  "172.16.168.235";
    public static String userName =  "user";
    public static String password =  "guestD@TABASE!";
    public static String dbName =  "FoodNav";
    public static String portNumber = "1433";

    // This is to create the connection between the server and the application
    public Connection connectDatabase(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try{
            Class.forName(classes);
            connectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + portNumber + ";databasename=" + dbName + ";user=" + userName + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }

        return connection;
    }

    // Connection con should be a variable that calls on the conn() class
    public void ExecuteDatabase(Connection con){

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                if(con == null){
                    String str = "Check Your Internet Connection";
                }
                else{
                    String str = "Connected with SQL server";
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
