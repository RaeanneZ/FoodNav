package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;

public class FoodDB extends AbstractDBProcess {
    Statement stmt;
    protected FoodDB(Context appContext) {
        super(appContext);
    }

    // Get Food data through its ID
    public ResultSet GetRecord(String name, Connection con){
        String sql = "SELECT * FROM Food WHERE Name = '"+name+"'";

        try {
            stmt = con.createStatement();
            return stmt.executeQuery(sql); // Column 1 = email, Column 2 = password
        }
        catch (Exception e) {
            return null;
        }
    }
}
