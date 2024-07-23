package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        title = view.findViewById(R.id.tvdate);

        String todayDate = "Today, " + sdf.format(Calendar.getInstance().getTime());
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

        Button shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });

        return view;
    }

    private void shareImage() {
        View view = getView().findViewById(R.id.constraintLayout); // Adjust this to the correct view id
        if (view != null) {
            Bitmap bitmap = getBitmapFromView(view);

            // Save bitmap to file
            File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image.png");
            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();

                // Get the URI of the image
                Uri uri = FileProvider.getUriForFile(requireActivity(), "sg.edu.np.mad.mad24p03team2.provider", file);

                // Create the share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Ensure Instagram or other apps can read the file
                List<ResolveInfo> resInfoList = requireContext().getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    requireContext().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                shareIntent.setPackage("com.instagram.android");
                shareIntent.putExtra("Instagram_story", uri);
                startActivity(shareIntent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to share image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "View not found", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        // Ensure the view has the correct background color
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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
        bsugarBox.setText(String.format("%.1f", mMacros.gettSugar()));
        bcarbsBox.setText(String.format("%.1f", mMacros.gettCarbs()));
        bfatsBox.setText(String.format("%.1f", mMacros.gettFats()));
        bcalBox.setText(String.format("%.1f", mMacros.gettCalories()));
    }

    private void updateLunchCard(MealClass meal) {
        MealMacros mMacros = GlobalUtil.getMealTotalMacros(meal);
        sugarBox.setText(String.format("%.1f", mMacros.gettSugar()));
        carbsBox.setText(String.format("%.1f", mMacros.gettCarbs()));
        fatsBox.setText(String.format("%.1f", mMacros.gettFats()));
        calBox.setText(String.format("%.1f", mMacros.gettCalories()));
    }

    private void updateDinnerCard(MealClass meal) {
        MealMacros mMacros = GlobalUtil.getMealTotalMacros(meal);
        dSugarBox.setText(String.format("%.1f", mMacros.gettSugar()));
        dcarbsBox.setText(String.format("%.1f", mMacros.gettCarbs()));
        dfatsBox.setText(String.format("%.1f", mMacros.gettFats()));
        dcalBox.setText(String.format("%.1f", mMacros.gettCalories()));
    }

    private void updateTodayMacros() {
        //calculate total of 3 meals macros for the day
        float tSugar = Float.parseFloat(sugarBox.getText().toString()) +
                Float.parseFloat(bsugarBox.getText().toString()) +
                Float.parseFloat(dSugarBox.getText().toString());

        float tFats = Float.parseFloat(fatsBox.getText().toString()) +
                Float.parseFloat(bfatsBox.getText().toString()) +
                Float.parseFloat(dfatsBox.getText().toString());

        float tCarbs = Float.parseFloat(carbsBox.getText().toString()) +
                Float.parseFloat(bcarbsBox.getText().toString()) +
                Float.parseFloat(dcarbsBox.getText().toString());

        float tCal = Float.parseFloat(calBox.getText().toString()) +
                Float.parseFloat(bcalBox.getText().toString()) +
                Float.parseFloat(dcalBox.getText().toString());

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
            if (returnClass.isInstance(getDietPlanOption)) {
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
        if (executeStatus) {
            if (returnClass.isInstance(getMeal)) {
                if (msg.compareToIgnoreCase("breakfast") == 0) {
                    updateBreakfastCard(SingletonTodayMeal.getInstance().GetMeal("Breakfast"));
                } else if (msg.compareToIgnoreCase("lunch") == 0) {
                    updateLunchCard(SingletonTodayMeal.getInstance().GetMeal("Lunch"));
                } else {
                    updateDinnerCard(SingletonTodayMeal.getInstance().GetMeal("Dinner"));
                }

                updateTodayMacros();
            }
        } else {
            // Handle failure
            Log.d("Dashboard::afterProcess", "Fail to load meal details");
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
    }
}
