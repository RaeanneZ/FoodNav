package sg.edu.np.mad.mad24p03team2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

/**
 * MainActivity2
 * UI-Activity Main page to display all the different fragments after user login,
 */
public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Set the content view to the specified layout
        setContentView(R.layout.activity_main2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity2.this,
                    Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity2.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        registerCallback();
        // Set default fragment to Dashboard on activity launch
        replaceFragment(new Dashboard(), "dashboard");
        // Get reference to the BottomNavigationView from the layout
        bottomNavigationView = findViewById(R.id.nav);
        // Set listener for bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            // Get the ID of the selected menu item
            int itemId = menuItem.getItemId();
            // Replace the fragment based on the selected menu item
            if (itemId == R.id.dashboard) {
                replaceFragment(new Dashboard(), "dashboard");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.logfood) {
                replaceFragment(new LogFoodProduct(), "logfood");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.account) {
                replaceFragment(new AccountPage(), "account");
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.food2Nom) {
                replaceFragment(new FoodToNom(), "food2Nom");
                return true;
            }
            return false; // Indicate that the event was not handled
        });

        int q=1;
        if(q==1){
            makeNotification();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });


    }

    // Method to replace the current fragment with a new one
    public void replaceFragment(Fragment fragment, String fragName) {
        // Get the fragment manager to handle fragment transactions
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Begin a new fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the current fragment in the container with the new fragment
        fragmentTransaction.replace(R.id.container2, fragment);
        // Manually adding previous fragment to history stack
        fragmentTransaction.addToBackStack(fragName);
        // Commit the transaction to make the change
        fragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101){
            if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),
                        "permission granted",Toast.LENGTH_SHORT).show();
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Permission to post notification is  required ");
                builder.setTitle("Permission Required").setCancelable(false)
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri =Uri.fromParts("package",getPackageName(),null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else {
                RequestRunTimePermissions();
            }
        }
    }

    private void RequestRunTimePermissions(){

        if (ContextCompat.checkSelfPermission(MainActivity2.this,
                Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),
                    "permission granted",Toast.LENGTH_SHORT).show();

        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.POST_NOTIFICATIONS)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Permission to post notification is  required ");
            builder.setTitle("Permission Required").setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity2.this,
                                    new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
                            dialog.dismiss();
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity2.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }


    }

    public void makeNotification() {
        String chanelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), chanelID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("warr")
                .setContentText("hfhf")
                .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", "Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(chanelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(chanelID,
                        "jhjkhdjk", importance);
                notificationChannel.setLightColor(android.R.color.darker_gray);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);

            }
        }
        notificationManager.notify(0, builder.build());
    }

    public void registerCallback() {

        //If fragment change is invoked via backbutton
        //change in fragment should be updated on Bottom Navigation Bar
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);

                int selItemID = bottomNavigationView.getSelectedItemId();
                if (f.getClass().getSimpleName().contains("Dashboard") && selItemID != R.id.dashboard) {
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                } else if (f.getClass().getSimpleName().contains("LogFoodProduct") && selItemID != R.id.logfood){
                    bottomNavigationView.setSelectedItemId(R.id.logfood);
                } else if (f.getClass().getSimpleName().contains("AccountPage") && selItemID != R.id.account) {
                    bottomNavigationView.setSelectedItemId(R.id.account);
                } else if (f.getClass().getSimpleName().contains("FoodToNom") && selItemID!=R.id.food2Nom) {
                    bottomNavigationView.setSelectedItemId(R.id.food2Nom);
                }
            }

        }, true);
    }

}