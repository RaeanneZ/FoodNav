package sg.edu.np.mad.mad24p03team2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AlertDialog.Builder(this)
                .setTitle("Open Website")
                .setMessage("Do you want to see the popup screen?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Open website
                        Intent browserIntent = new Intent(PopupActivity.this,WebViewActivity.class);
                        browserIntent.putExtra("url", "https://www.healthhub.sg/live-healthy/cut-100-calories-from-each-meal-every-day--without-going-hungry");
                        startActivity(browserIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog and activity
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }
}