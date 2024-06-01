package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class editProfile extends AppCompatActivity {

    private EditText birthdateText;
    private EditText weightText;
    private EditText heightText;
    private Button male;
    private Button female;

    private ImageView femaleIconView;

    private ImageView maleIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find Buttons from the layout
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        maleIconView = findViewById(R.id.male_icon);
        femaleIconView = findViewById(R.id.female_icon);
        Button saveBtn = findViewById(R.id.save);

        // All texts
        birthdateText = findViewById(R.id.birthdate);
        weightText = findViewById(R.id.weight);
        heightText = findViewById(R.id.height);

        // Populate profile form details
        populateProfileDetails();

        // Set click listeners for Buttons
        male.setOnClickListener(view -> {
            setGenderSelection("male");
        });

        female.setOnClickListener(view -> {
            setGenderSelection("female");
        });

        // Save the inputs by user to database
        saveBtn.setOnClickListener(view -> {
            try {
                updateProfile();
                Intent intent = new Intent(editProfile.this, MainActivity2.class);
                startActivity(intent);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void populateProfileDetails() {
        AccountClass currentUserProfile = SingletonSession.getInstance().GetAccount();
        if (currentUserProfile != null) {
            birthdateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentUserProfile.getBirthDate()));
            weightText.setText(String.valueOf(currentUserProfile.getWeight()));
            heightText.setText(String.valueOf(currentUserProfile.getHeight()));
            setGenderSelection(currentUserProfile.getGender());
        } else {
            Toast.makeText(this, "Failed to load profile details", Toast.LENGTH_SHORT).show();
        }
    }

    private void setGenderSelection(String gender) {
        ColorStateList maleDefaultColorStateList = getResources().getColorStateList(R.color.lavender);
        ColorStateList femaleDefaultColorStateList = getResources().getColorStateList(R.color.lightpink);

        if (gender.equalsIgnoreCase("male")) {
            ColorStateList newColorStateList = getResources().getColorStateList(R.color.purple);
            male.setBackgroundTintList(newColorStateList);
            female.setBackgroundTintList(femaleDefaultColorStateList);
            maleIconView.setVisibility(View.VISIBLE);
            femaleIconView.setVisibility(View.GONE);
        } else if (gender.equalsIgnoreCase("female")) {
            ColorStateList newColorStateList = getResources().getColorStateList(R.color.altpink);
            male.setBackgroundTintList(maleDefaultColorStateList);
            female.setBackgroundTintList(newColorStateList);
            maleIconView.setVisibility(View.GONE);
            femaleIconView.setVisibility(View.VISIBLE);
        } else {
            male.setBackgroundTintList(maleDefaultColorStateList);
            female.setBackgroundTintList(femaleDefaultColorStateList);
            maleIconView.setVisibility(View.GONE);
            femaleIconView.setVisibility(View.GONE);
        }
    }
    private void updateProfile() throws ParseException, NumberFormatException {
        String birthdate = birthdateText.getText().toString().trim();
        int weight = Integer.parseInt(weightText.getText().toString());
        int height = Integer.parseInt(heightText.getText().toString());
        String gender = male.getBackgroundTintList() == getResources().getColorStateList(R.color.purple) ? "male" : "female";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(birthdate);

        if (birthdate.isEmpty() || weightText.getText().toString().isEmpty() || heightText.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SingletonSession.getInstance().UpdateProfile(gender, birthDate, height, weight);

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}

