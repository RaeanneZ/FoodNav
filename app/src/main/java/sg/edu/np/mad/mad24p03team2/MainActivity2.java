package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

/**
 * MainActivity2
 * UI-Activity Main page to display all the different fragments after user login,
 */
public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Set the content view to the specified layout
        setContentView(R.layout.activity_main2);

        registerCallback();
        // Set default fragment to Dashboard on activity launch
        replaceFragment(new Dashboard(), "dashboard");
        // Get reference to the BottomNavigationView from the layout
        bottomNavigationView = findViewById(R.id.nav);
        // Set listener for bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            // Get the ID of the selected menu item
            int itemId = menuItem.getItemId();
            // Replace the fragment based on the selected menu item
            if (itemId == R.id.dashboard) {
                replaceFragment(new Dashboard(), "dashboard");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.logfood) {
                replaceFragment(new LogFoodProduct(), "logfood");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.account) {
                replaceFragment(new AccountPage(), "account");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.food2Nom) {
                replaceFragment(new FoodToNom(), "food2Nom");
                return true;
            }
            if(itemId == R.id.nomNotion){
                replaceFragment(new NomNotion(), "nomNotion");
                return true;
            }
            return false; // Indicate that the event was not handled
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });


    }

    // Method to replace the current fragment with a new one
    public void replaceFragment(Fragment fragment, String fragName) {
        // Get the fragment manager to handle fragment transactions
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Get current fragment
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container2);
        // Check if the current fragment is different from the target fragment
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return; // Do nothing if the current fragment is the same as the target fragment
        }
        // Begin a new fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the current fragment in the container with the new fragment
        fragmentTransaction.replace(R.id.container2, fragment);
        // Manually adding previous fragment to history stack
        fragmentTransaction.addToBackStack(fragName);
        // Commit the transaction to make the change
        fragmentTransaction.commit();
    }

    public void registerCallback() {

        //If fragment change is invoked via backbutton
        //change in fragment should be updated on Bottom Navigation Bar
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);

                int selItemID = bottomNavigationView.getSelectedItemId();
                if (f.getClass().getSimpleName().contains("Dashboard") && selItemID != R.id.dashboard) {
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                } else if (f.getClass().getSimpleName().contains("LogFoodProduct") && selItemID != R.id.logfood){
                    bottomNavigationView.setSelectedItemId(R.id.logfood);
                } else if (f.getClass().getSimpleName().contains("AccountPage") && selItemID != R.id.account) {
                    bottomNavigationView.setSelectedItemId(R.id.account);
                } else if (f.getClass().getSimpleName().contains("FoodToNom") && selItemID!=R.id.food2Nom) {
                    bottomNavigationView.setSelectedItemId(R.id.food2Nom);
                } else if (f.getClass().getSimpleName().contains("NomNotion") && selItemID != R.id.nomNotion) {
                    bottomNavigationView.setSelectedItemId(R.id.nomNotion);
                }
            }

        }, true);
    }

}