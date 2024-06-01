package sg.edu.np.mad.mad24p03team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserProfile;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class SelectionActivity2 extends AppCompatActivity implements IDBProcessListener {
    private Button continueButton;
    UpdateUserProfile updateUserProfile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_selectoption_lowsugar);
        updateUserProfile = new UpdateUserProfile(getApplicationContext(), this);

        RadioButton yes = findViewById(R.id.radioButton);
        RadioButton no = findViewById(R.id.radioButton2);

        continueButton = findViewById(R.id.button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean options = false;

                if (yes.isChecked()) {
                    options = true;
                }
                else if (no.isChecked()) {
                    options = false;
                }
                SingletonSession.getInstance().SetBloodSugarTracking(options);

                // Update the account in database
                AccountClass account = SingletonSession.getInstance().GetAccount();
                updateUserProfile.execute(account.getEmail(), account.getDietPlanOpt(), account.getGender(), account.getGender(),
                        account.getBirthDate().toString(), Integer.toString(account.getHeight()), Float.toString(account.getWeight()));

            }
        });
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
        // Move to next page
        Intent intent = new Intent(SelectionActivity2.this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public void afterProcess(Boolean executeStatus) {

    }
}
