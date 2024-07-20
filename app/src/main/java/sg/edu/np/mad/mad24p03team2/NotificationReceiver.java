package sg.edu.np.mad.mad24p03team2;

import static sg.edu.np.mad.mad24p03team2.Dashboard.Cal;
import static sg.edu.np.mad.mad24p03team2.Dashboard.CalLeft;
import static sg.edu.np.mad.mad24p03team2.Dashboard.Carb;
import static sg.edu.np.mad.mad24p03team2.Dashboard.Fat;
import static sg.edu.np.mad.mad24p03team2.Dashboard.sugarn;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class  NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "daily_notification_channel";
    private static final String PREFERENCES_FILE = "progress_prefs";
    private static final String PROGRESS_KEY = "progress";
    private static final int NOTIFICATION_ID = 1;
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create notification channel if necessary
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Daily Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        RemoteViews loadingLayout = new RemoteViews(context.getPackageName(), R.layout.notification_loading);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(loadingLayout)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

// Simulate loading time (e.g., 3 seconds)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Update notification with actual content
            RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
            RemoteViews notificationLayout2 = new RemoteViews(context.getPackageName(), R.layout.notification_layout2);
            notificationLayout.setProgressBar(R.id.progressBarcarbs, 100, Carb, false);
            notificationLayout.setProgressBar(R.id.progressBarSugar, 100, sugarn, false);
            notificationLayout.setProgressBar(R.id.progressBarfats, 100, Fat, false);
            notificationLayout.setProgressBar(R.id.Cbar, 1550, Cal, false);
            notificationLayout.setTextViewText(R.id.tvProgress, String.valueOf(CalLeft));

            NotificationCompat.Builder updatedBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_notifications_active_24)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(notificationLayout2)
                    .setCustomBigContentView(notificationLayout)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.notify(NOTIFICATION_ID, updatedBuilder.build());
        }, 3000); // Delay in milliseconds
    }
}
