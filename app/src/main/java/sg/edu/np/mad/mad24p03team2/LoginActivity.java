package sg.edu.np.mad.mad24p03team2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.LoginInfoClass;

public class LoginActivity extends AppCompatActivity {
    EditText emailComponent, passwordComponent;
    String email, password;
    Button loginBtn;

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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailComponent.getText().toString();
                password = passwordComponent.getText().toString();
                new LoginUser(getApplicationContext()).execute("");
            }
        });
    }

    // For View, only edit the postExecution function
    public class LoginUser extends AsyncTaskExecutorService<String, String , String> {

        String z = "";
        Boolean[] checks;
        Boolean isValid;
        Boolean isExist;
        Integer isValidIdx = 0;
        Integer isExistIdx = 1;

        @Override
        protected void onPostExecute(String s) {
            // E.g. Send a Toast message: Login Successful
            // Go to Home Dashboard Page
        }

        // BACKEND PART ----------------------------------------------------------------------------
        // DO NOT TOUCH
        // Login Data Class
        LoginInfoClass loginInfoClass = null;

        public LoginUser(Context appContext){
            this.loginInfoClass = new LoginInfoClass(getApplicationContext());
        }

        // This is to check password corresponds
        @Override
        protected String doInBackground(String s) {
            try{
                checks = loginInfoClass.ValidateRecord(email, password);
                isValid = checks[isValidIdx];
                isExist = checks[isExistIdx];
            }
            catch (Exception e){
                z = e.getMessage();
            }
            return z;
        }
        // BACKEND PART ----------------------------------------------------------------------------
    }
}