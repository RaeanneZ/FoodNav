package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountpage extends Fragment {
    private Switch darkmodeSwitch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountpage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountpage.
     */
    // TODO: Rename and change types and number of parameters
    public static accountpage newInstance(String param1, String param2) {
        accountpage fragment = new accountpage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accountpage, container, false);
        TextView changePassword = view.findViewById(R.id.changePassword);
        // Find the switch by its id
        darkmodeSwitch = view.findViewById(R.id.darkmode);

        // Set listener for switch changes
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkmodeSwitch.setChecked(true);
        } else {
            darkmodeSwitch.setChecked(false);
        }
        darkmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Update UI for Dark Mode
                    updateUiForDarkMode();
                } else {
                    // Update UI for Light Mode
                    updateUiForLightMode();
                }
            }
        });
// Set click listener for the button
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ChangePassword activity
                Intent changePasswordIntent = new Intent(getActivity(), ChangePassword.class);
                startActivity(changePasswordIntent);
            }
        });
        return view;
    }

    // Define methods to update UI elements based on theme (dark/light)
    private void updateUiForDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("Working dark mode", "It's working");
    }

    private void updateUiForLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
