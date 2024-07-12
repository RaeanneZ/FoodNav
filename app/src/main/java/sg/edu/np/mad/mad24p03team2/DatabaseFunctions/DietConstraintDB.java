package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;
import sg.edu.np.mad.mad24p03team2.ApplicationSetUp.StartUp;

/**
 * DietConstraintDB
 * Controller handles CRUD operations on Diet Constraint Database
 */
public class DietConstraintDB extends AbstractDBProcess {
    Statement stmt;
    private final Context context;
    private Connection dbCon = null;

    public DietConstraintDB(Context base) {
        super(base);
        this.context = base;
        getDBConnection();
    }

    public ResultSet GetRecord(int accountID) {
        String sql = "SELECT * FROM DietConstraint WHERE AccID = " + accountID;

        try {
            stmt = dbCon.createStatement();
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            return null;
        }
    }

    private void getDBConnection() {
        if (context instanceof StartUp) {
            final StartUp app = (StartUp) context;
            dbCon = app.getConnection();
        }
    }

    public boolean UpdateRecord(int accountID, ArrayList<String> constraints) throws SQLException {
        Boolean isSuccess = false;
        ResultSet resultSet = null;
        Log.d("DietConstraintDB", "Update Record Function");
        try {
            //check if there's record
            resultSet = GetRecord(accountID);
            String existConstraint="";
            while (resultSet.next()) {
                // If there is record
                // Get the diet type
                existConstraint = resultSet.getString("DietType");
                // Remove all diet constraints that are in the database
                if(constraints.contains(existConstraint)) {
                    constraints.remove(existConstraint);
                    break;
                } else {
                    // If diet constraint found in database is no longer found in the array list,
                    // Delete record of a specific diet constraint that was saved previously
                    DeleteRecord(accountID, existConstraint);
                }
            }
            Log.d("DietConstraintDB", "Constraint Size: " + constraints.size());
            for (String constraint : constraints) {
                Log.d("DietConstraintDB", "Account ID: " + accountID);
                Log.d("DietConstraintDB", "Constraint: " + constraint);
                String sql = "INSERT INTO DietConstraint(AccID, DietType) VALUES (" + accountID + ",'" + constraint + "')";
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            return isSuccess;
        } finally {
            // Close resultset
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return true;
    }

    public boolean DeleteRecord(int acctID, String constraint) throws SQLException {
        boolean isUpdateSuccessful = false;
        String sql = null;

        try {
            sql = "DELETE FROM DietConstraint WHERE AccID = " + acctID + " AND DietType = '"+ constraint +"'";
            stmt = dbCon.createStatement();
            stmt.executeUpdate(sql);
            isUpdateSuccessful = true;
        } catch (Exception e) {
            isUpdateSuccessful = false;
        }

        return isUpdateSuccessful;
    }
}
