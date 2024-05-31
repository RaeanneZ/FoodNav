package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserPassword;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class ChangePassword extends AppCompatActivity implements IDBProcessListener {

    UpdateUserPassword updateUserPassword = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        updateUserPassword = new UpdateUserPassword(getApplication().getApplicationContext(), this);

        //JOVAN TODO: Get Password input onClick, update database with below method
        // updateUserPassword.execute(SingletonSession.getInstance().GetAccount().getEmail(), password);
    }

    @Override
    public void afterProcess(Boolean executeStatus) {

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}