package sg.edu.np.mad.mad24p03team2;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.DietPlanClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietPlanOption;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetMeal;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class Dashboard extends Fragment implements IDBProcessListener {
    GetMeal getMeal = null;

    GetDietPlanOption getDietPlanOption = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getMeal = new GetMeal(requireActivity().getApplicationContext(), this);
        getDietPlanOption = new GetDietPlanOption(requireActivity().getApplicationContext(), this);


        // HONG RONG TODO: Get Card UI Elements
       //getDietPlanOption.execute(SingletonSession.getInstance().GetAccount().getGender());
        // GET ALL MEAL DATA
        // HONG RONG TODO: Use the below code to get the details of each meal

        // getMeal.execute( "Lunch", SingletonSession.getInstance().GetAccount().getId());
         //getMeal.execute( "Dinner", SingletonSession.getInstance().GetAccount().getId());
        getDietPlanOption.execute("Diabetic Friendly",SingletonSession.getInstance().GetAccount().getGender());

         double BMR=447.593+(9.247*70)+(3.098*175)-(4.330*30);
         double TDEE= BMR*1.55;
         double protein =75*4;
         double fat=(TDEE*0.25)/9;
         double carb=(BMR-(280+657.07))/4;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView progress =view.findViewById(R.id.tvProgress);
        ProgressBar carbBar = view.findViewById(R.id.progressBarcarbs);
        ProgressBar fatBar = view.findViewById(R.id.progressBarfats);
        ProgressBar proteinBar = view.findViewById(R.id.progressBarprotein);
        ProgressBar cbar = view.findViewById(R.id.Cbar);
        carbBar.setMax((int)carb);
        fatBar.setMax((int) fat);
        proteinBar.setMax((int) protein);
        progress.setText(String.format("%.0f",BMR));
        cbar.setMax((int)BMR);


       // if () {
            getMeal.execute("Breakfast", Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //}
        //else if () {
            //getMeal.execute( "Lunch",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //}
        //else if (){
            //getMeal.execute( "Dinner",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //}
        //else{
            //getMeal.execute( "Others",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //}

        return view;

    }

    @Override
    public void afterProcess(Boolean executeStatus) {

        //HONG RONG
        //TODO: After database fetch all the meals from the execution statement above,
        //TODO: all meals are stored in SingletonTodayMeal (global class)
        //TODO: To get each meal details to update the UI, write the below code
        // -->> MealClass breakfastMeal = SingletonTodayMeal.getInstance().getMeal("Breakfast");
        if (executeStatus) {
            MealClass lunch = SingletonTodayMeal.getInstance().GetMeal("Lunch");
            MealClass breakfastMeal = SingletonTodayMeal.getInstance().GetMeal("Breakfast");
            TextView textView = getView().findViewById(R.id.tvkcal_b);
          breakfastMeal.getSelectedFoodList().values();
            Log.i( "afterProcess: ",breakfastMeal.getSelectedFoodList().values().toString());

            if (breakfastMeal != null) {
                //textView.setText() // assuming getName() method returns the meal name
            } else {
               // textView.setText("No Breakfast Data");
            }
        } else {
            // Handle failure
            Toast.makeText(getContext(), "Failed to fetch meal data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}