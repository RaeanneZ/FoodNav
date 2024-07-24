package sg.edu.np.mad.mad24p03team2;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;


import android.os.Bundle;
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

import androidx.core.app.NotificationCompat;

import android.os.SystemClock;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

import java.util.Date;

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

    public static final String CHANNEL_ID = "daily_notification_channel";

    public static  int  Carb=0;
    public static int Fat=0;
    public static  int sugarn=0;
    public static int Cal=0;
    public static int CalLeft=0;

    public static int Rcarb=0;
    public static int Rfat=0;
    public static int Rsugar=0;
    public static int Rcal=0;
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

        scheduleDailyNotification();


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


        Log.d("Data", "Data received from fragment: " + (int)tCarbs);
        Carb=(int)tCarbs;
        Fat=(int) tFats;
        sugarn=(int) tSugar;
        Cal=(int) tCal;

        CalLeft= (int) (Rcal-tCal);

    }


    public void makeNotification() {
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelID)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Loading")
                .setContentText("Please wait while we load the content...")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)  // Keep the notification visible until loading is complete
                .setProgress(0, 0, true);  // Indeterminate progress bar

// Create an Intent to open an activity
        Intent intent = new Intent(getContext(), PopupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // Ensure activity is not recreated

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

// Create notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID, "Channel Name", importance);
                notificationChannel.setLightColor(android.R.color.darker_gray);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

// Issue the initial loading notification
        notificationManager.notify(0, builder.build());

// Simulate loading process in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simulate loading process (e.g., network request or background task)
                    Thread.sleep(5000);  // Simulate 5 seconds of loading

                    // After loading is complete, update the notification
                    NotificationCompat.Builder updatedBuilder = new NotificationCompat.Builder(getContext(), channelID)
                            .setSmallIcon(R.drawable.baseline_notifications_active_24)
                            .setContentTitle("Warning")
                            .setContentText("You have used over half of your daily limit.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setProgress(0, 0, false)  // Remove the progress bar
                            .setOngoing(false)  // Allow user interaction
                            .setContentIntent(pendingIntent)  // Set the same PendingIntent
                            .setAutoCancel(true);  // Dismiss the notification when clicked

                    // Create an Intent for the dismiss action
                    Intent dismissIntent = new Intent(getContext(), NotificationDismissedReceiver.class);
                    PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(getContext(),
                            1, dismissIntent, PendingIntent.FLAG_MUTABLE);

                    // Add the dismiss action to the updated notification
                    updatedBuilder.addAction(R.drawable.baseline_disabled_by_default_24, "Dismiss", dismissPendingIntent);

                    // Notify with the updated content
                    notificationManager.notify(0, updatedBuilder.build());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void scheduleDailyNotification() {
        Context context = getContext();
        if (context == null) return;

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15); // Set the time you want the notification to be triggered
        calendar.set(Calendar.MINUTE, 51);     // 26 minutes past the hour
        calendar.set(Calendar.SECOND, 0);      // Optional: Set seconds to zero if you want

       if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
           Log.d("Data", "rrr" );
        }

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    @Override
    public void afterProcess(Boolean executeStatus, Class<? extends AsyncTaskExecutorService> returnClass) {
        if (executeStatus) {
            if (returnClass.isInstance(getDietPlanOption)) {
                //to ensure max setup only done once when view is created
                if (!setupProgressBarMax) {
                    DietPlanClass dietPlan = SingletonDietPlanResult.getInstance().getDietPlan();
                    if (dietPlan != null) {
                       /* carbBar.setMax(dietPlan.getReccCarbIntake() + 10);
                        fatBar.setMax(dietPlan.getReccFatsIntake() + 10);
                        sugarBar.setMax(dietPlan.getReccSugarIntake() + 10);

                        int calories = dietPlan.getReccCaloriesIntake();
                        cbar.setMax(calories);
                        calProgressText.setText(String.valueOf(calories));*/
                        Date dateString = SingletonSession.getInstance().GetAccount().getBirthDate();
                        LocalDate birthDate = dateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate currentDate = LocalDate.now();

                        Period period = Period.between(birthDate, currentDate);
                        int years = period.getYears();
                        float Height = SingletonSession.getInstance().GetAccount().getHeight();
                        float Weight = SingletonSession.getInstance().GetAccount().getWeight();
                        String Gender = SingletonSession.getInstance().GetAccount().getGender();
                        double rfat = 0;
                        double rcarb = 0;
                        double rpro = 0;
                        double rsugar = 0;
                        Log.d("Data", Gender);
                        if (Gender.equals("F")) {
                            double BMR = (10 * Weight) + (6.25 * Height) - (5 * years) - 161;
                            double TDEE = BMR * 1.55;
                            rcarb = (TDEE * 0.5) / 4;
                            rfat = (TDEE * 0.3) / 9;
                            rpro = (TDEE * 0.2) / 4;
                            rsugar = (TDEE * 0.1) / 4;
                            Rcal = (int) BMR;
                            Rfat = (int) rfat;
                            Rcarb = (int) rcarb;
                            Rsugar = (int) rsugar;
                            carbBar.setMax(Rcarb);
                            fatBar.setMax(Rfat);
                            sugarBar.setMax(Rsugar);
                            cbar.setMax(Rcal);
                            calProgressText.setText(String.valueOf(Rcal));

                        } else {
                            double BMR = (10 * Weight) + (6.25 * Height) - (5 * years) + 5;
                            double TDEE = BMR * 1.55;
                            rcarb = (TDEE * 0.5) / 4;
                            rfat = (TDEE * 0.3) / 9;
                            rpro = (TDEE * 0.2) / 4;
                            rsugar = (TDEE * 0.1) / 4;
                            Rcal = (int) BMR;
                            Rfat = (int) rfat;
                            Rcarb = (int) rcarb;
                            Rsugar = (int) rsugar;
                            carbBar.setMax(Rcarb);
                            fatBar.setMax(Rfat);
                            sugarBar.setMax(Rsugar);
                            cbar.setMax(Rcal);
                            calProgressText.setText(String.valueOf(Rcal));
                        }

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
                double c=Rcarb*0.75;
                double s =Rsugar*0.75;
                double f =Rfat*0.75;
                double kcal=Rcal*0.75;
                if (Carb>=c || sugarn>=s|| Fat>=f||Cal>=kcal){
                    makeNotification();
                }

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
