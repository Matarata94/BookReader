package ir.matarata.bookreader;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class MainActivity extends AppCompatActivity {

    ShimmerTextView shimmertvhour,shimmertvminute,shimmertvsecond;

    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmertvhour = (ShimmerTextView) findViewById(R.id.shimmertv_hour);
        shimmertvminute = (ShimmerTextView) findViewById(R.id.shimmertv_minute);
        shimmertvsecond = (ShimmerTextView) findViewById(R.id.shimmertv_second);

        Shimmer shimmer = new Shimmer();
        Shimmer shimmer2 = new Shimmer();
        Shimmer shimmer3 = new Shimmer();
        shimmer.setDuration(1000);
        shimmer2.setDuration(1000);
        shimmer3.setDuration(1000);
        shimmer.start(shimmertvsecond);
        shimmer2.start(shimmertvminute);
        shimmer3.start(shimmertvhour);

        font = Typeface.createFromAsset(getAssets(),"caviardreams.ttf");
        shimmertvhour.setTypeface(font);
        shimmertvminute.setTypeface(font);
        shimmertvsecond.setTypeface(font);

    }
}
