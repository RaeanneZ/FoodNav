package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateMeal;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class AddFood extends Fragment implements IDBProcessListener {

    UpdateMeal updateMeal = null;

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
        return inflater.inflate(R.layout.fragment_add_food, container, false);
    }

    @Override
    public void afterProcess(Boolean executeStatus) {

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }


}