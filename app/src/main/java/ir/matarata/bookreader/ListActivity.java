package ir.matarata.bookreader;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends Activity {

    private String[] values = new String[100];
    private ArrayList<String> list;
    private database db;
    private ArrayAdapter adapter;
    private ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        mylistview = (ListView) findViewById(R.id.list);
        list = new ArrayList<String>();
        db = new database(this);
        db.open();
        for(int i=2;i<=db.count();i++){
            String id = String.valueOf(Integer.parseInt(db.Query(i,0))-1);
            list.add(id + ".  " + db.Query(i,1) + ":" + db.Query(i,2) + ":" + db.Query(i,3) + "  -->  " + db.Query(i,4) + "   " + db.Query(i,5));
        }
        db.close();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        mylistview.setAdapter(adapter);

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String temp = mylistview.getItemAtPosition(position).toString();
                if(temp.substring(1,2).equals(".")){
                    if(Integer.parseInt(temp.substring(5,6)) == 1){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Well Done!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(5,6)) == 2){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Good Job!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(5,6)) == 3){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Really Nice!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 1){
                        Toast.makeText(getApplicationContext(),"Only 10 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 2){
                        Toast.makeText(getApplicationContext(),"Only 20 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 3){
                        Toast.makeText(getApplicationContext(),"30 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 4){
                        Toast.makeText(getApplicationContext(),"40 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 5){
                        Toast.makeText(getApplicationContext(),"50 Minute Study!",Toast.LENGTH_LONG).show();
                    }
                }else if(temp.substring(2,3).equals(".")){
                    if(Integer.parseInt(temp.substring(6,7)) == 1){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Well Done!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(6,7)) == 2){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Good Job!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(6,7)) == 3){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Really Nice!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(8,9)) == 1){
                        Toast.makeText(getApplicationContext(),"Only 10 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(8,9)) == 2){
                        Toast.makeText(getApplicationContext(),"Only 20 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(8,9)) == 3){
                        Toast.makeText(getApplicationContext(),"30 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(8,9)) == 4){
                        Toast.makeText(getApplicationContext(),"40 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(8,9)) == 5){
                        Toast.makeText(getApplicationContext(),"50 Minute Study!",Toast.LENGTH_LONG).show();
                    }
                }else if(temp.substring(3,4).equals(".")){
                    if(Integer.parseInt(temp.substring(7,8)) == 1){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Well Done!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 2){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Good Job!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(7,8)) == 3){
                        Toast.makeText(getApplicationContext(),"1 Hour Study. Really Nice!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(9,10)) == 1){
                        Toast.makeText(getApplicationContext(),"Only 10 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(9,10)) == 2){
                        Toast.makeText(getApplicationContext(),"Only 20 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(9,10)) == 3){
                        Toast.makeText(getApplicationContext(),"30 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(9,10)) == 4){
                        Toast.makeText(getApplicationContext(),"40 Minute Study!",Toast.LENGTH_LONG).show();
                    }else if(Integer.parseInt(temp.substring(9,10)) == 5){
                        Toast.makeText(getApplicationContext(),"50 Minute Study!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(ListActivity.this,MainActivity.class);
        finish();
        startActivity(in);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
