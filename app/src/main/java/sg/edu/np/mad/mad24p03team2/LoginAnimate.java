package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class LoginAnimate extends AppCompatActivity {

    TextView textViewMsg;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_animate);

        textViewMsg = findViewById(R.id.message);
        textViewMsg.setText("Welcome "+ SingletonSession.getInstance().GetAccount().getName());
        lottie = findViewById(R.id.animationView);

        textViewMsg.animate().translationY(-700).setDuration(500).setStartDelay(0);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //moving on to next page
                Intent login = new Intent(LoginAnimate.this, MainActivity2.class);
                startActivity(login);
                finish();  //offload login page
            }
        },2000); //delay 1 seconds
    }
}