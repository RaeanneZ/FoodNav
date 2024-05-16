package sg.edu.np.mad.mad24p03team2;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

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

        // Find Buttons from the layout
        Button male = findViewById(R.id.male);
        Button female = findViewById(R.id.female);

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

    }
}