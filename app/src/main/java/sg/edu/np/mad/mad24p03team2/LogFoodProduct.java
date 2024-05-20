package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class LogFoodProduct extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_food_product, container, false);
        Button button  =view.findViewById(R.id.button2);
        button.setOnClickListener(v -> switchFragment());
        return view;
    }

    private void switchFragment() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity2) {
            ((MainActivity2) activity).replaceFragment(new SearchForFood());
        }
    }
}