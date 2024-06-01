package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.DietPlanClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietPlanOption;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonDietPlanResult;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class SelectionActivity3 extends AppCompatActivity implements IDBProcessListener {
    GetDietPlanOption getDietPlanOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_selectoption_lowsugar2);
        getDietPlanOption = new GetDietPlanOption(getApplicationContext(), this);

        // Arguments: Name of Plan, gender of user
        getDietPlanOption.execute("Diabetic Friendly", SingletonSession.getInstance().GetAccount().getGender());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to next page
                Intent intent = new Intent(SelectionActivity3.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // SIAN KIM TODO: Update UI with the return values

        // for Carbs
        TextView carbs = findViewById(R.id.tvc1);
        String carbsAmt = getString(R.string.carbs_amount);
        carbs.setText(SingletonDietPlanResult.getInstance().getDietPlan().getReccCarbIntake());

        // for Protein
        TextView protein = findViewById(R.id.tvp1);
        String proteinAmt = getString(R.string.protein_amount);
        protein.setText(SingletonDietPlanResult.getInstance().getDietPlan().getReccProteinIntake());

        // for Fats
        TextView fats = findViewById(R.id.tvf1);
        String fatsAmt = getString(R.string.fats_amount);
        fats.setText(SingletonDietPlanResult.getInstance().getDietPlan().getReccFatsIntake());

        // for cal
        TextView cal = findViewById(R.id.tvk1);
        String kCalAmt = getString(R.string.kCal_amount);
        if (SingletonDietPlanResult.getInstance().getDietPlan().getGender() == "F") {
            kCalAmt = String.valueOf(1200);
        } else if (SingletonDietPlanResult.getInstance().getDietPlan().getGender() == "M") {
            kCalAmt = String.valueOf(1800);

        }
        cal.setText(kCalAmt + "\nKcal");
    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}
