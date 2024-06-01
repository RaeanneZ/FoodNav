package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;

/*
If a statement in try block raised an exception, then the rest of the try block doesn’t execute
and control passes to the corresponding catch block. After executing the catch block,
the control will be transferred to finally block(if present) and
then the rest program will be executed.
* */
public class LoginInfoDB extends AbstractDBProcess{
    Statement stmt;

    public LoginInfoDB(Context base) {
        super(base);
    }

    // Read Function
    public ResultSet GetRecord(String email, Connection con){
        String sql = "SELECT * FROM LoginInfo WHERE AccEmail = '"+email+"'";

        try {
            stmt = con.createStatement();
            return stmt.executeQuery(sql); // Column 1 = email, Column 2 = password
        }
        catch (Exception e) {
            return null;
        }
    }

    // Create Function
    public boolean CreateRecord(String email, String password) throws SQLException {
        Boolean isSuccess = false;
        ResultSet resultSet = null;
        String sql = "INSERT INTO LoginInfo(AccEmail, AccPwd) VALUES ('"+email+"','"+password+"')";

        try{
            // Check if record is already inside LoginInfo
            resultSet = GetRecord(email,dbCon);
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0){
                // Create and execute the SQL statement to Database
                Log.d("CreateRecord","IM IN");
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);
                isSuccess = true;
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

    // Update Password, Email update is not allowed
    public boolean UpdateRecord(String email, String password) throws SQLException {
        boolean isUpdateSuccessful = false;
        ResultSet resultSet = null;
        String sql = "UPDATE LoginInfo SET AccPwd = '"+password+"' WHERE AccEmail = '"+email+"'";

        try{
            resultSet = GetRecord(email,dbCon);
            if(resultSet.next()){
                // Create and execute the SQL statement to Database
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);

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

    // For Login validation
    public Boolean[] ValidateRecord(String email, String password) throws SQLException {
        boolean isValid = false;
        boolean isExist = false;

        ResultSet resultSet = null;

        try{
            resultSet = GetRecord(email,dbCon);
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0){
                // Account is not created
                isExist = false;
                Log.d("In ValidateRecord","Record doesnt exist");
            }
            else {
                resultSet.next();
                isExist = true; //user record exist

                if (Objects.equals(resultSet.getString("AccPwd"), password)) {
                    // Password Correct
                    isValid = true;
                }else{
                    isValid = false;
                }
            }

        } catch(Exception e) {
            Log.d("ValidateRecord", "An issue occured: " + e.getMessage());
        }


        Boolean[] checkedBool = new Boolean[2];
        checkedBool[0] = isValid;
        checkedBool[1] = isExist;

        return checkedBool;
    }
}
