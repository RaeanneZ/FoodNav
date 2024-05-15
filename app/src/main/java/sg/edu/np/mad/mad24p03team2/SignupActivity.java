package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.RegisterUser;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class SignupActivity extends AppCompatActivity implements IDBProcessListener {

    EditText emailComponent, pwdComponent;
    String email, password;
    Button signUpBtn;
    RegisterUser registerUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loading), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailComponent = (EditText) findViewById(R.id.username);
        pwdComponent = (EditText) findViewById(R.id.password);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        registerUser = new RegisterUser(getApplicationContext(),this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailComponent.getText().toString();
                password = pwdComponent.getText().toString();
                registerUser.execute(email, password);
            }
        });
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // Your code to update UI here
        if(executeStatus) {
            Log.d("Sign Up", "Success");
        } else {
            Log.d("Sign Up", "Fail");
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}