package sg.edu.np.mad.mad24p03team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.LoginInfoClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.RegisterUser;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.SaveSecurityInfo;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.SecurityInfoClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserProfile;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSignUp;

/**
 * SelectionActivity2
 * UI-Activity Page displayed during signup to prompt user if they want to track sugar level
 */
public class SelectionActivity2 extends AppCompatActivity implements IDBProcessListener {
    private Button continueButton;
    private RegisterUser registerUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_option_lsugar);
        registerUser = new RegisterUser(getApplicationContext(), this);

        RadioButton yesRB = findViewById(R.id.radioButton);
        RadioButton noRB = findViewById(R.id.radioButton2);

        continueButton = findViewById(R.id.continueButton);

        continueButton.setOnClickListener(v -> {
            SingletonSignUp.getInstance().setBloodTrackingOpt(yesRB.isChecked());

            //Signup forms completed
            //registerUser will create and update db records
            registerUser.execute("start");
        });
    }

    private void completeSignUpProcess() {
        Intent intent = new Intent(SelectionActivity2.this, SelectionActivity3.class);
        startActivity(intent);

        //signup process completed
        SingletonSignUp.getInstance().ended();
        finish(); //end page
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {}

    @Override
    public void afterProcess(Boolean executeStatus, Class<? extends AsyncTaskExecutorService> returnClass) {
        if (executeStatus) {
            runOnUiThread(() -> completeSignUpProcess());
        } else {
            Log.d("SelectionActivity2", "Fail to execute");
            runOnUiThread(() -> finishAffinity());
        }
    }

    @Override
    public void afterProcess(Boolean executeStatus, String msg, Class<? extends AsyncTaskExecutorService> returnClass) {}
}
