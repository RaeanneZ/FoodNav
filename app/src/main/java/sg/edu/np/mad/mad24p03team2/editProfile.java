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

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserProfile;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class editProfile extends AppCompatActivity implements IDBProcessListener {
    UpdateUserProfile updateUserProfile = null;

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

        updateUserProfile = new UpdateUserProfile(getApplicationContext(), this);

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
            setGenderSelection("M");
        });

        female.setOnClickListener(view -> {
            setGenderSelection("F");
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

        if (gender.equalsIgnoreCase("M")) {
            male.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
            female.setBackgroundTintList(femaleDefaultColorStateList);
            maleIconView.setVisibility(View.VISIBLE);
            femaleIconView.setVisibility(View.GONE);
        } else if (gender.equalsIgnoreCase("F")) {
            male.setBackgroundTintList(maleDefaultColorStateList);
            female.setBackgroundTintList(getResources().getColorStateList(R.color.altpink));
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
        float weight = Float.parseFloat(weightText.getText().toString());
        float height = Float.parseFloat(heightText.getText().toString());
        String gender;
        if (male.getBackgroundTintList() == getResources().getColorStateList(R.color.purple)) {
            gender = "M"; // Male
        } else if (female.getBackgroundTintList() == getResources().getColorStateList(R.color.altpink)) {
            gender = "F"; // Female
        } else {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isDateValid(birthdate)){
            Toast.makeText(this, "Please enter Birthdate in YYYY-MM-DD", Toast.LENGTH_SHORT).show();
            return;
        }


        if (birthdate.isEmpty() || weightText.getText().toString().isEmpty() || heightText.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call database
        updateUserProfile.execute(SingletonSession.getInstance().GetAccount().getEmail(), SingletonSession.getInstance().GetAccount().getDietPlanOpt(), gender, birthdate, Float.toString(height), Float.toString(weight));
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean isDateValid(String stringDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);

        try{
            Date date = sdf.parse(stringDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }

    @Override
    public void afterProcess(Boolean executeStatus) {

    }
}

