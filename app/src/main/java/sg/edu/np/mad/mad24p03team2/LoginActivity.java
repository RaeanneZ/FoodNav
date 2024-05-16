package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.LoginUser;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class LoginActivity extends AppCompatActivity implements IDBProcessListener {
    EditText emailComponent, passwordComponent;
    String email, password;
    Button loginBtn;

    LoginUser loginUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loading), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailComponent = (EditText) findViewById(R.id.username);
        passwordComponent = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginUser = new LoginUser(getApplicationContext(),this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailComponent.getText().toString();
                password = passwordComponent.getText().toString();
                loginUser.execute(email, password);
            }
        });
    }

    //Methods from IDBProcessListener
    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
        // User Login process will return 2 boolean flag to indicate whether its wrong username or
        // wrong password that caused LOGIN UNSUCCESSFUL
        // Please update your UI here

        Log.d("afterProcess", "Execution status: " + (isValidPwd && isValidUser));
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // For 1 return value (e.g. whether registration is successful)

    }
}