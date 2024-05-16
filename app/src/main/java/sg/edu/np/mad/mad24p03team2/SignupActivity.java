package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.RegisterUser;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class SignupActivity extends AppCompatActivity implements IDBProcessListener {

    EditText nameComponent, emailComponent, pwdComponent;
    String name, email, password;
    Button signUpBtn;
    CheckBox checkboxBtn;
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

        nameComponent = (EditText) findViewById(R.id.name);
        emailComponent = (EditText) findViewById(R.id.username);
        pwdComponent = (EditText) findViewById(R.id.password);
        checkboxBtn = (CheckBox) findViewById(R.id.checkBox);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        registerUser = new RegisterUser(getApplicationContext(),this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameComponent.getText().toString();
                email = emailComponent.getText().toString();
                password = pwdComponent.getText().toString();
                registerUser.execute(name, email, password);
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