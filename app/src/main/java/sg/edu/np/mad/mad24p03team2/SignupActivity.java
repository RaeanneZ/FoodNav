package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.RegisterUser;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class SignupActivity extends AppCompatActivity implements IDBProcessListener {

    EditText nameComponent, emailComponent, pwdComponent, cfpwdComponent;
    String name, email, password, confirm;
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
        emailComponent = (EditText) findViewById(R.id.email);
        pwdComponent = (EditText) findViewById(R.id.password);
        cfpwdComponent = (EditText) findViewById(R.id.confirmPassword);
        checkboxBtn = (CheckBox) findViewById(R.id.checkBox);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        registerUser = new RegisterUser(getApplicationContext(), this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameComponent.getText().toString();
                email = emailComponent.getText().toString();
                password = pwdComponent.getText().toString();
                confirm = cfpwdComponent.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Password should not be empty!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirm)) {
                    // Display error message - passwords don't match
                    Toast.makeText(SignupActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Name or email cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser.execute(name, email, password);
                }
            }
        });
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // Your code to update UI here
        if (executeStatus) {
            Log.d("Sign Up", "Success");
        } else {
            Log.d("Sign Up", "Fail");
        }
        signUpBtn.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
    }
}