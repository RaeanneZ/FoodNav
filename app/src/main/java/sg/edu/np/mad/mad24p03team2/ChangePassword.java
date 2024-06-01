package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserPassword;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class ChangePassword extends AppCompatActivity implements IDBProcessListener {

    private UpdateUserPassword updateUserPassword;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        // Find UI elements for password input based on IDs from your layout
        newPasswordEditText = findViewById(R.id.password); // New password field as per your layout
        confirmPasswordEditText = findViewById(R.id.confirmPassword); // Confirm password field as per your layout

        updateUserPassword = new UpdateUserPassword(getApplication().getApplicationContext(), this);

        // Button click listener to update password
        Button updatePasswordButton = findViewById(R.id.confirmPasswordButton); // Update password button ID
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Input validation
                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else if (newPassword.equals(confirmPassword)) {
                    // Update database with new password
                    updateUserPassword.execute(SingletonSession.getInstance().GetAccount().getEmail(), newPassword);
                    Log.d("ChangePassword", "Email = " + SingletonSession.getInstance().GetAccount().getEmail().toString());
                    Log.d("ChangePassword", "Password update process started");
                } else {
                    Toast.makeText(ChangePassword.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        if (executeStatus) {
            Toast.makeText(ChangePassword.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
            // Clear password fields
            newPasswordEditText.setText("");
            confirmPasswordEditText.setText("");
        } else {
            Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}
