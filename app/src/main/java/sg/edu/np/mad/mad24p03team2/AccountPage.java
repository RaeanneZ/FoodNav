package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateUserProfile;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

/**
 * accountPage
 * UI-Fragment that display menu for application and profile customisation
 */
public class AccountPage extends Fragment {

    //private Switch darkmodeSwitch;
    private ImageView maleIconView;
    private ImageView femaleIconView;
    private Button logoutBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accountpage, container, false);
        TextView changePassword = view.findViewById(R.id.changePassword);

        // Find the switch by its id
        //darkmodeSwitch = view.findViewById(R.id.darkmode);
        maleIconView = view.findViewById(R.id.male_icon);
        femaleIconView = view.findViewById(R.id.female_icon);
        logoutBtn = view.findViewById(R.id.logoutButton);

        logoutBtn.setOnClickListener(v -> {

            //Logout - remove shared preferences
            EncryptedSharedPreferences sharedPreferences =
                    GlobalUtil.getEncryptedSharedPreference(getActivity().getApplicationContext());

            if (sharedPreferences != null) {
                sharedPreferences.edit().putString(GlobalUtil.SHARED_PREFS_LOGIN_KEY, "").apply();
                sharedPreferences.edit().putString(GlobalUtil.SHARED_PREFS_LOGIN_PSWD, "").apply();
            }

            //This is to reset activity stack, ensure no past activities history
            Intent intent = new Intent(getActivity(), LogoutAnimate.class);
            startActivity(intent);
            getActivity().finishAffinity();

        });

        // Set listener for switch changes
      /*  if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            darkmodeSwitch.setChecked(true);
        } else{
            darkmodeSwitch.setChecked(false);
        }
        darkmodeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Update UI for Dark Mode
                updateUiForDarkMode();
            } else {
                // Update UI for Light Mode
                updateUiForLightMode();
            }
        });*/


        TextView editProfile = view.findViewById(R.id.editProfile);
        String username = SingletonSession.getInstance().GetAccount().getName();
        TextView tempName = view.findViewById(R.id.tempName);
        tempName.setText(username);

        // Set click listener for the buttons
        changePassword.setOnClickListener(v -> loadChangePasswordActivity());

        ImageView passwordArrow = view.findViewById(R.id.imageView9);
        passwordArrow.setOnClickListener(v -> loadChangePasswordActivity());

        editProfile.setOnClickListener(v -> loadEditProfileActivity());

        ImageView profileArrow = view.findViewById(R.id.arrow);
        profileArrow.setOnClickListener(v -> loadEditProfileActivity());

        TextView setDietConstraint = view.findViewById(R.id.dietPref);
        setDietConstraint.setOnClickListener(v -> loadDietConstraintActivity());

        ImageView dietArrow = view.findViewById(R.id.arrowDiet);
        dietArrow.setOnClickListener(v -> loadDietConstraintActivity());

        // Populate profile details including profile picture
        populateProfileDetails();

        return view;
    }

    private void loadDietConstraintActivity() {
        Intent dietConstraintsIntent = new Intent(getActivity(), DietConstraintActivity.class);
        startActivity(dietConstraintsIntent);
    }

    private void loadEditProfileActivity() {
        Intent editProfileIntent = new Intent(getActivity(), EditProfile.class);
        startActivity(editProfileIntent);
    }

    private void loadChangePasswordActivity() {
        Intent changePasswordIntent = new Intent(getActivity(), ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void populateProfileDetails() {
        AccountClass currentUserProfile = SingletonSession.getInstance().GetAccount();
        if (currentUserProfile != null) {
            setProfileImage(currentUserProfile.getGender());
        } else {
            Toast.makeText(getContext(), "Failed to load profile details", Toast.LENGTH_SHORT).show();
        }
    }

    private void setProfileImage(String gender) {
        if (gender.equalsIgnoreCase("M")) {
            maleIconView.setVisibility(View.VISIBLE);
            femaleIconView.setVisibility(View.GONE);
        } else if (gender.equalsIgnoreCase("F")) {
            maleIconView.setVisibility(View.GONE);
            femaleIconView.setVisibility(View.VISIBLE);
        } else {
            maleIconView.setVisibility(View.GONE);
            femaleIconView.setVisibility(View.GONE);
        }
    }

    // Define methods to update UI elements based on theme (dark/light)
    //private void updateUiForDarkMode() {
    //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    // }

    // private void updateUiForLightMode() {
    //     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    // }

}
