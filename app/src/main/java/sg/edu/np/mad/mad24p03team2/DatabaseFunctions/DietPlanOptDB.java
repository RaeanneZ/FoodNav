package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;

// This will only include read function from database
public class DietPlanOptDB extends AbstractDBProcess {
    Statement stmt;
    protected DietPlanOptDB(Context appContext) {
        super(appContext);
    }

    // Get DietPlan data through its ID
    public ResultSet GetRecord(String name, String trackBloodSugar){
        Connection con = dbCon;
        String sql = "SELECT * FROM DietPlan WHERE Name = '"+name+"' AND TrackBloodSugar = '"+trackBloodSugar+"'";

        try {
            stmt = con.createStatement();
            return stmt.executeQuery(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
