package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetMeal;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class Dashboard extends Fragment implements IDBProcessListener {

    GetMeal getMeal = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProgressBar progressBar;
        getMeal = new GetMeal(requireActivity().getApplicationContext(), this);


        // HONG RONG TODO: Get Card UI Elements

        // GET ALL MEAL DATA
        // HONG RONG TODO: Use the below code to get the details of each meal
        // getMeal.execute( "Breakfast", SingletonSession.getInstance().GetAccount().getId());
        // getMeal.execute( "Lunch", SingletonSession.getInstance().GetAccount().getId());
        // getMeal.execute( "Dinner", SingletonSession.getInstance().GetAccount().getId());



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return view;

    }

    @Override
    public void afterProcess(Boolean executeStatus) {

        //HONG RONG
        //TODO: After database fetch all the meals from the execution statement above,
        //TODO: all meals are stored in SingletonTodayMeal (global class)
        //TODO: To get each meal details to update the UI, write the below code
        // -->> MealClass breakfastMeal = SingletonTodayMeal.getInstance().getMeal("Breakfast");
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}