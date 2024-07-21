package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.DietPlanClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietConstraint;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietPlanOption;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetMeal;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonDietPlanResult;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;

/**
 * Dashboard
 * UI-Fragment to display summary of current user's Food Journal
 * Display includes, progress bar to summaries total intake of macros and calories balance based
 * on all food logged today
 * Summary of each meal presented in card-form
 */
public class Dashboard extends Fragment implements IDBProcessListener {
    GetMeal getMeal = null;
    GetDietPlanOption getDietPlanOption = null;

    TextView title;
    TextView sugarBox;
    TextView carbsBox;
    TextView fatsBox;
    TextView calBox;

    TextView bsugarBox;
    TextView bcarbsBox;
    TextView bfatsBox;
    TextView bcalBox;

    TextView dSugarBox;
    TextView dcarbsBox;
    TextView dfatsBox;
    TextView dcalBox;

    ProgressBar carbBar;
    ProgressBar fatBar;
    ProgressBar sugarBar;
    ProgressBar cbar;
    TextView calProgressText;

    GetDietConstraint getDietConstraint;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");

    boolean setupProgressBarMax = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getMeal = new GetMeal(requireActivity().getApplicationContext(), this);
        getDietPlanOption = new GetDietPlanOption(requireActivity().getApplicationContext(), this);

        //Grab user diet Constraints and store in SingletonDietConstraints
        getDietConstraint = new GetDietConstraint(requireActivity().getApplicationContext(), this);


        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {} //do nothing
        });
        

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        title = view.findViewById(R.id.tvdate);

        String todayDate = "Today, "+sdf.format(Calendar.getInstance().getTime());
        title.setText(todayDate);
        sugarBox = view.findViewById(R.id.tvp1);
        carbsBox = view.findViewById(R.id.tvc1);
        fatsBox = view.findViewById(R.id.tvf1);
        calBox = view.findViewById(R.id.kcalnum);

        bsugarBox = view.findViewById(R.id.tvp1_b);
        bcarbsBox = view.findViewById(R.id.tvc1_b);
        bfatsBox = view.findViewById(R.id.tvf1_b);
        bcalBox = view.findViewById(R.id.kcalnum_b);

        dSugarBox = view.findViewById(R.id.tvp1_d);
        dcarbsBox = view.findViewById(R.id.tvc1_d);
        dfatsBox = view.findViewById(R.id.tvf1_d);
        dcalBox = view.findViewById(R.id.kcalnum_d);

        calProgressText = view.findViewById(R.id.tvProgress);
        carbBar = view.findViewById(R.id.progressBarcarbs);
        fatBar = view.findViewById(R.id.progressBarfats);
        sugarBar = view.findViewById(R.id.progressBarSugar);
        cbar = view.findViewById(R.id.Cbar);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //grab diet plan ; hardcoded plan name because this is the only option for now
        getDietPlanOption.execute("Diabetic Friendly", SingletonSession.getInstance().GetAccount().getGender());

        //get details from model to display
        String acctId = Integer.toString(SingletonSession.getInstance().GetAccount().getId());
        getMeal.execute("Breakfast", acctId);
        getMeal.execute("Lunch", acctId);
        getMeal.execute("Dinner", acctId);

        //update dietPreference
        getDietConstraint.execute(acctId);
    }

    private void updateBreakfastCard(MealClass meal) {

        MealMacros mMacros = GlobalUtil.getMealTotalMacros(meal);
        bsugarBox.setText(String.format("%.1f",mMacros.gettSugar()));
        bcarbsBox.setText(String.format("%.1f",mMacros.gettCarbs()));
        bfatsBox.setText(String.format("%.1f",mMacros.gettFats()));
        bcalBox.setText(String.format("%.1f",mMacros.gettCalories()));
    }

    private void updateLunchCard(MealClass meal) {
        MealMacros mMacros = GlobalUtil.getMealTotalMacros(meal);
        sugarBox.setText(String.format("%.1f",mMacros.gettSugar()));
        carbsBox.setText(String.format("%.1f",mMacros.gettCarbs()));
        fatsBox.setText(String.format("%.1f",mMacros.gettFats()));
        calBox.setText(String.format("%.1f",mMacros.gettCalories()));

     }

    private void updateDinnerCard(MealClass meal) {
        MealMacros mMacros = GlobalUtil.getMealTotalMacros(meal);
        dSugarBox.setText(String.format("%.1f",mMacros.gettSugar()));
        dcarbsBox.setText(String.format("%.1f",mMacros.gettCarbs()));
        dfatsBox.setText(String.format("%.1f",mMacros.gettFats()));
        dcalBox.setText(String.format("%.1f",mMacros.gettCalories()));
    }

    private void updateTodayMacros() {
        //calculate total of 3 meals macros for the day
        float tSugar = Float.parseFloat((String) sugarBox.getText()) +
                Float.parseFloat((String) bsugarBox.getText()) +
                Float.parseFloat((String) dSugarBox.getText());

        float tFats = Float.parseFloat((String) fatsBox.getText()) +
                Float.parseFloat((String) bfatsBox.getText()) +
                Float.parseFloat((String) dfatsBox.getText());

        float tCarbs = Float.parseFloat((String) carbsBox.getText()) +
                Float.parseFloat((String) bcarbsBox.getText()) +
                Float.parseFloat((String) dcarbsBox.getText());

        float tCal = Float.parseFloat((String) calBox.getText()) +
                Float.parseFloat((String) bcalBox.getText()) +
                Float.parseFloat((String) dcalBox.getText());

        carbBar.setProgress((int) tCarbs);
        fatBar.setProgress((int) tFats);
        sugarBar.setProgress((int) tSugar);

        cbar.setProgress((int) tCal);
        int calLeft = cbar.getMax() - (int) tCal;
        calProgressText.setText(String.valueOf(calLeft));
    }

    @Override
    public void afterProcess(Boolean executeStatus, Class<? extends AsyncTaskExecutorService> returnClass) {

        if (executeStatus) {
             if(returnClass.isInstance(getDietPlanOption)) {
                //to ensure max setup only done once when view is created
                if (!setupProgressBarMax) {
                    DietPlanClass dietPlan = SingletonDietPlanResult.getInstance().getDietPlan();
                    if (dietPlan != null) {
                        carbBar.setMax(dietPlan.getReccCarbIntake() + 10);
                        fatBar.setMax(dietPlan.getReccFatsIntake() + 10);
                        sugarBar.setMax(dietPlan.getReccSugarIntake() + 10);

                        int calories = dietPlan.getReccCaloriesIntake();
                        cbar.setMax(calories);
                        calProgressText.setText(String.valueOf(calories));

                        //setupcomplete
                        setupProgressBarMax = true;

                    } else {
                        //hardcode value if Model is not ready
                        carbBar.setMax(200);
                        fatBar.setMax(200);
                        sugarBar.setMax(200);
                        cbar.setMax(1550);
                    }
                }
            } //update diet plan
        } else {
            // Handle failure
            Toast.makeText(getContext(), "Failed to fetch meal data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterProcess(Boolean executeStatus, String msg, Class<? extends AsyncTaskExecutorService> returnClass) {
        if(executeStatus){
            if(returnClass.isInstance(getMeal)){
                if(msg.compareToIgnoreCase("breakfast")==0){
                    updateBreakfastCard(SingletonTodayMeal.getInstance().GetMeal("Breakfast"));
                }else if(msg.compareToIgnoreCase("lunch") == 0){
                    updateLunchCard(SingletonTodayMeal.getInstance().GetMeal("Lunch"));
                }else{
                    updateDinnerCard(SingletonTodayMeal.getInstance().GetMeal("Dinner"));
                }

                updateTodayMacros();
            }
        }else {
            // Handle failure
            Log.d("Dashboard::afterProcess","Fail to load meal details");
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {}
}