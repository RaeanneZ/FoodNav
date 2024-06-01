package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateMeal;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class AddFood extends Fragment implements IDBProcessListener {

    UpdateMeal updateMeal = null;
private int ns=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateMeal = new UpdateMeal(requireContext().getApplicationContext(), this);

        //HONG RONG TODO:
        // TODO: When user clicks addFood button, add food item to SingletonTodayMeal and to database with function below
        // TODO: Replace "mealName" and the Quantity with input ("Breakfast", "Lunch", "Dinner", "Other") that should be attached with the meal
        // updateMeal.execute(SingletonSession.getInstance().GetAccount().getId(), mealName, quantity);
        // SingletonTodayMeal.getInstance().AddMeal(mealName);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        Button button = view.findViewById(R.id.button2);
        TextView FoodName=view.findViewById(R.id.textView23);
        TextView N=view.findViewById(R.id.textView16);
        TextView nofplate = view.findViewById(R.id.textView26);
        TextView addn= view.findViewById(R.id.tvadd);
        TextView Mn= view.findViewById(R.id.textView2);

         addn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ns++;  // Increment the counter
                 // Update the TextView with the new value
                 nofplate.setText(ns+" Plate");
                 N.setText(ns);
             }
         });

        Mn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ns--;  // Increment the counter
                // Update the TextView with the new value
                nofplate.setText(ns+" Plate");
                N.setText(ns);
            }
        });

        String mealName  = FoodName.getText().toString();
        String quantity =N.getText().toString();
        MealClass mealClass= new MealClass(mealName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMeal.execute(Integer.toString(SingletonSession.getInstance().GetAccount().getId()), mealName, quantity);
                SingletonTodayMeal.getInstance().AddMeal(mealClass);
                switchFragment();
            }
        });
        return view;
    }

    private void switchFragment() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity2) {
            ((MainActivity2) activity).replaceFragment(new SearchForFood());
        }
    }
    @Override
    public void afterProcess(Boolean executeStatus) {

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }


}