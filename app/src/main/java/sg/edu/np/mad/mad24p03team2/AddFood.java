package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class AddFood extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //HONG RONG TODO:
        // TODO: When user clicks addFood button, add food item to SingletonTodayMeal with function below
        // TODO: Replace "mealName" with the mealName that should be attached with the meal
        // SingletonTodayMeal.getInstance().AddMeal(mealName);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_food, container, false);
    }
}