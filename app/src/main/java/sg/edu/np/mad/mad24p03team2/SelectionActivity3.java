package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietPlanOption;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.RegisterUser;

public class SelectionActivity3 extends AppCompatActivity implements IDBProcessListener {
    GetDietPlanOption getDietPlanOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_selectoption_lowsugar2);
        getDietPlanOption = new GetDietPlanOption(getApplicationContext(), this);

        // Arguments: Name of Plan, gender of user
        getDietPlanOption.execute("Diabetic Friendly", SingletonSession.getInstance().GetAccount().getGender());
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // SIAN KIM TODO: Update UI with the return values

        /* MacroView macroView = findViewById(R.id.macro_view);
        macroView.setRecommendedMacros("Recommended Macros");
        macroView.setCarbsAmount("23g");
        macroView.setProteinAmount("23g");
        macroView.setFatAmount("23g");
        macroView.setCaloriesAmount("505 Kcal"); */
    }

    // IGNORE --------------------------------------------------------------------------------------
    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}
