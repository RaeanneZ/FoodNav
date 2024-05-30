package sg.edu.np.mad.mad24p03team2;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class ProfileActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // JOVAN TODO: Populate profile form details from SingletonSession.getInstance().GetCurrentUserProfile();
        // TODO: when user clicks in from Account in navigation bar

        // Find Buttons from the layout
        Button male = findViewById(R.id.male);
        Button female = findViewById(R.id.female);
        Button saveBtn = findViewById(R.id.save);

        // All texts
        EditText birthdateText = findViewById(R.id.birthdate);
        EditText weightText = findViewById(R.id.weight);
        EditText heightText = findViewById(R.id.height);

        // Set click listeners for Buttons
        male.setOnClickListener(view -> {
            ColorStateList newColorStateList = getResources().getColorStateList(R.color.purple);
            male.setBackgroundTintList(newColorStateList);
            ColorStateList secondColorStateList = getResources().getColorStateList(R.color.lightpink);
            female.setBackgroundTintList(secondColorStateList);
        });

        female.setOnClickListener(view -> {
            ColorStateList newColorStateList = getResources().getColorStateList(R.color.altpink);
            female.setBackgroundTintList(newColorStateList);
            ColorStateList secondColorStateList = getResources().getColorStateList(R.color.lavender);
            male.setBackgroundTintList(secondColorStateList);
        });

        // This is to save the inputs by user to database
        saveBtn.setOnClickListener(view -> {
            // Put the details into an Account object from sign up page
            // JOVAN TODO: Fill in the method UpdateProfile() with required parameters
            // TO BE UNCOMMENTED: SingletonSession.getInstance().UpdateProfile();
        });
    }
}