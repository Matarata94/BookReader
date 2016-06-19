package ir.matarata.bookreader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShimmerTextView shimmertvhour,shimmertvminute,shimmertvsecond;
    private Shimmer shimmer,shimmer2,shimmer3;
    private FloatingActionMenu fabmenu;
    private FloatingActionButton fabstart,fabpause,fabstop,fablist,fabexit;
    private Typeface font;
    private int counterActive;
    private Timer tm;
    private int counterSecond=0,counterMinute=0,counterHour=0;
    private database db;
    private ChangeDate changeDate;
    private String hourString,minuteString,secondString;
    private Notification n;
    private NotificationManager notificationManager;

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
                //Notification
                n  = new Notification.Builder(MainActivity.this)
                        .setContentTitle("Book Reader App")
                        .setContentText("Your book reading timer is active!")
                        .setSmallIcon(R.drawable.ic_play_arrow_white_24dp)
                        .setAutoCancel(false).build();
                n.flags = Notification.FLAG_ONGOING_EVENT;
                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1994, n);

                shimmer.start(shimmertvsecond);
                shimmer2.start(shimmertvminute);
                shimmer3.start(shimmertvhour);
                shimmertvhour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.blue));
                shimmertvminute.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.blue));
                shimmertvsecond.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.blue));
                fabmenu.close(true);
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
                pauseTimer();
            }
        });
        fabstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shimmertvhour.getText().equals("00 :") && shimmertvminute.getText().equals(" 00 ") && shimmertvsecond.getText().equals("  00")){

                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("آیا مایل به ذخیره هستید؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    stopTimer(0);
                                    fabmenu.close(true);
                                }
                            })
                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    stopTimer(1);
                                    fabmenu.close(true);
                                }
                            })
                            .setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });
        fablist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,ListActivity.class);
                finish();
                startActivity(in);
            }
        });
        fabexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shimmertvhour.getText().equals("00 :") && shimmertvminute.getText().equals(" 00 ") && shimmertvsecond.getText().equals("  00")){
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("آیا مایل به خروج هستید؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("آیا مایل به خروج هستید؟")
                            .setPositiveButton("خروج بدون ذخیره", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    stopTimer(1);
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("خروج با ذخیره", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    stopTimer(0);
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
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

    private String currentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        c.add(Calendar.DAY_OF_YEAR, 1);
        String formattedDate = df.format(c.getTime());
        String persianDate = changeDate.changeMiladiToFarsi(formattedDate);

        return persianDate;
    }

    private String currentTime(){
        Calendar cal = Calendar.getInstance();
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        String currentTime = String.valueOf(hourofday) + ":" + String.valueOf(minute);
        return currentTime;
    }

    private void pauseTimer(){
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

    private void stopTimer(int saveState){
        if(shimmertvhour.getText().equals("00 :") && shimmertvminute.getText().equals(" 00 ") && shimmertvsecond.getText().equals("  00")){

        }else {
            tm.cancel();
            if(saveState == 0){
                db.open();
                if(counterHour < 10){
                    hourString = "0" + String.valueOf(counterHour);
                }else{
                    hourString = String.valueOf(counterHour);
                }
                if(counterMinute < 10){
                    minuteString = "0" + String.valueOf(counterMinute);
                }else{
                    minuteString = String.valueOf(counterMinute);
                }
                if(counterSecond < 10){
                    secondString = "0" + String.valueOf(counterSecond);
                }else{
                    secondString = String.valueOf(counterSecond);
                }
                db.insert(hourString,minuteString,secondString,currentDate(),currentTime());
                db.close();
            }
            counterActive = 0;
            shimmer.cancel();
            shimmer2.cancel();
            shimmer3.cancel();
            shimmertvhour.setTextColor(getResources().getColor(R.color.black));
            shimmertvminute.setTextColor(getResources().getColor(R.color.black));
            shimmertvsecond.setTextColor(getResources().getColor(R.color.black));
            notificationManager.cancel(1994);
            counterHour = 0; counterMinute = 0; counterSecond = 0;
            setHour(); setMinute(); setSecond();
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
        fabmenu.setClosedOnTouchOutside(true);
        db = new database(this);
        db.databasecreate();
        changeDate = new ChangeDate();
    }

    @Override
    public void onBackPressed() {
        if(fabmenu.isOpened()){
            fabmenu.close(true);
        }else{
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(1994);
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
