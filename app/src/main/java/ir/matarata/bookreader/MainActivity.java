package ir.matarata.bookreader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShimmerTextView shimmertvhour,shimmertvminute,shimmertvsecond;
    private Shimmer shimmer,shimmer2,shimmer3;
    private FloatingActionMenu fabmenu;
    private FloatingActionButton fabstart,fabpause,fabstop,fablist,fabexit;
    private Typeface font;
    private int counterActive=0;
    private Timer tm;
    private int counterSecond=0,counterMinute=0,counterHour=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initiate();

        fabstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(counterActive == 0){
                counterActive = 1;
                shimmer.start(shimmertvsecond);
                shimmer2.start(shimmertvminute);
                shimmer3.start(shimmertvhour);
                shimmertvhour.setTextColor(getResources().getColor(R.color.blue));
                shimmertvminute.setTextColor(getResources().getColor(R.color.blue));
                shimmertvsecond.setTextColor(getResources().getColor(R.color.blue));
                tm =new Timer();
                tm.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                counterSecond++;
                                if(counterSecond == 60){
                                    counterSecond = 0;
                                    setSecond();
                                    counterMinute++;
                                    if(counterMinute == 60){
                                        counterMinute = 0;
                                        setMinute();
                                        counterHour++;
                                        setHour();
                                    }else{
                                        setMinute();
                                    }
                                }else{
                                    setSecond();
                                }
                            }
                        });
                    }
                }, 0, 1000);
            }
            }
        });
        fabpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterActive == 1){
                    tm.cancel();
                    counterActive = 0;
                    shimmer.cancel();
                    shimmer2.cancel();
                    shimmer3.cancel();
                    shimmertvhour.setTextColor(getResources().getColor(R.color.black));
                    shimmertvminute.setTextColor(getResources().getColor(R.color.black));
                    shimmertvsecond.setTextColor(getResources().getColor(R.color.black));
                }

            }
        });
        fabstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fablist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fabexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void setHour(){
        if(counterHour < 10){
            shimmertvhour.setText("0" + String.valueOf(counterHour) + " :");
        }else{
            shimmertvhour.setText(String.valueOf(counterHour) + " :");
        }
    }
    private void setMinute(){
        if(counterMinute < 10){
            shimmertvminute.setText(" 0" + String.valueOf(counterMinute) + " ");
        }else{
            shimmertvminute.setText(" " + String.valueOf(counterMinute) + " ");
        }
    }
    private void setSecond(){
        if(counterSecond < 10){
            shimmertvsecond.setText("  0" + String.valueOf(counterSecond));
        }else{
            shimmertvsecond.setText("  " + String.valueOf(counterSecond));
        }
    }

    private void Initiate(){
        shimmer = new Shimmer();
        shimmer2 = new Shimmer();
        shimmer3 = new Shimmer();
        shimmer.setDuration(1000);
        shimmer2.setDuration(1000);
        shimmer3.setDuration(1000);
        shimmertvhour = (ShimmerTextView) findViewById(R.id.shimmertv_hour);
        shimmertvminute = (ShimmerTextView) findViewById(R.id.shimmertv_minute);
        shimmertvsecond = (ShimmerTextView) findViewById(R.id.shimmertv_second);
        fabmenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabstart = (FloatingActionButton) findViewById(R.id.fabmenu_start);
        fabpause = (FloatingActionButton) findViewById(R.id.fabmenu_pause);
        fabstop = (FloatingActionButton) findViewById(R.id.fabmenu_stop);
        fablist = (FloatingActionButton) findViewById(R.id.fabmenu_list);
        fabexit = (FloatingActionButton) findViewById(R.id.fabmenu_exit);
        font = Typeface.createFromAsset(getAssets(),"caviardreams.ttf");
        shimmertvhour.setTypeface(font);
        shimmertvminute.setTypeface(font);
        shimmertvsecond.setTypeface(font);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
