package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;
import sg.edu.np.mad.mad24p03team2.ApplicationSetUp.StartUp;


public class AccountDB extends AbstractDBProcess {

    Statement stmt;

    // CONNECT TO DATABASE
    protected AccountDB(Context appContext) {
        super(appContext);
    }

    // Read Function
    public ResultSet GetRecord(String email, Connection con){
        String sql = "SELECT * FROM Account WHERE AccEmail = '"+email+"'";

        try {
            stmt = con.createStatement();
            return stmt.executeQuery(sql); // Column 1 = email, Column 2 = password
        }
        catch (Exception e) {
            return null;
        }
    }

    // Create Function
    public boolean CreateRecord(String name, String email) throws SQLException {
        Boolean isSuccess = false;
        ResultSet resultSet = null;
        String sql = "INSERT INTO Account(Name, AccEmail) VALUES ('"+name+"', '"+email+"')";

        try{
            // Check if record is already inside LoginInfo
            Log.d("CreateRecord", "Connection = " +dbCon);
            resultSet = GetRecord(email,dbCon);
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0){
                // Create and execute the SQL statement to Database
                Log.d("CreateRecord","IM IN");
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);
                isSuccess = true;

                // Save current user account to Account object created in StartUp
                saveCurrentUserInfo(email);
            }
        }
        catch (Exception e) {
            return isSuccess;
        }
        finally{
            // Close resultset
            if(resultSet != null) {
                resultSet.close();
            }
        }

        return isSuccess;
    }

    public boolean UpdateRecord(String email, String gender, String birthDate, String height, String weight) throws SQLException {
        boolean isUpdateSuccessful = false;
        ResultSet resultSet = null;
        String sql = null;

        try{
            resultSet = GetRecord(email,dbCon);
            if(resultSet.next()){
                // Create and execute the SQL statement to Database
                if (gender != null) {
                    sql = "UPDATE ACCOUNT SET Gender = '"+gender+"' WHERE AccEmail = '"+email+"'";
                    stmt = dbCon.createStatement();
                    stmt.executeUpdate(sql);
                }
                if(birthDate != null) {
                    sql = "UPDATE ACCOUNT SET BirthDate = '"+birthDate+"' WHERE AccEmail = '"+email+"'";
                    stmt = dbCon.createStatement();
                    stmt.executeUpdate(sql);
                }
                if(height != null) {
                    sql = "UPDATE ACCOUNT SET Height = '"+height+"' WHERE AccEmail = '"+email+"'";
                    stmt = dbCon.createStatement();
                    stmt.executeUpdate(sql);
                }
                if(weight != null) {
                    sql = "UPDATE ACCOUNT SET Weight = '"+weight+"' WHERE AccEmail = '"+email+"'";
                    stmt = dbCon.createStatement();
                    stmt.executeUpdate(sql);
                }

                isUpdateSuccessful = true;
            }
        }
        catch (Exception e) {
            Log.d("UpdateRecord failed", e.getMessage());
            return isUpdateSuccessful = false;
        }
        finally{
            // Close resultset
            if(resultSet != null) {
                resultSet.close();
            }
        }

        Log.d("Update Record", "status = "+isUpdateSuccessful);
        return isUpdateSuccessful;
    }

    // Save Account Info to Start
    private void saveCurrentUserInfo(String email){

        int id = 0;
        String name = " ";
        int dietPlanOpt = 0;

        StartUp startUpApp = (StartUp) this.getApplicationContext();

        try{
            ResultSet resultSet = GetRecord(email, dbCon);
            // There is Data
            if(resultSet.next()){
                id = resultSet.getInt("AccID");
                name = resultSet.getString("Name");
                dietPlanOpt = resultSet.getInt("DietPlanID");

                AccountClass userAccount = new AccountClass(id, name, email, dietPlanOpt);
                startUpApp.setCurrentUser(userAccount);
            }
        } catch(Exception e) {
            Log.d("GetCurrentUserInfo", "No Account to get");
        }

//        Log.d("SaveCurrentUserInfo", "ID: " + startUpApp.getCurrentUser().getId());
//        Log.d("SaveCurrentUserInfo", "Name: " + startUpApp.getCurrentUser().getName());
//        Log.d("SaveCurrentUserInfo", "Email: " + startUpApp.getCurrentUser().getEmail());
    }

}
