package ir.matarata.bookreader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
	
	private final Context mycontext;
	public final String path = "data/data/ir.matarata.bookreader/databases/";
	public final String dbname = "bookreader_db";
	public SQLiteDatabase mydb;
	
	public database(Context context) {
		super(context, "bookreader_db", null, 1);
		mycontext = context;
	}
	public void onCreate(SQLiteDatabase db) {
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public boolean checkdb(){
		SQLiteDatabase db = null;
		try{
		db = SQLiteDatabase.openDatabase(path + dbname, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLException e)
		{
			
		}
		return db != null ? true:false;
	}
	public void databasecreate(){
		boolean checkdb = checkdb();
		if(checkdb){
			
		}else{
			this.getReadableDatabase();
			try{
			copydatabase();
			}catch(IOException e){
				
			}
		}
	}
	public void copydatabase() throws IOException{
		OutputStream myOutput = new FileOutputStream(path + dbname);
		byte[] buffer = new byte[1024];
		int length;
		InputStream myInput = mycontext.getAssets().open(dbname);
		while((length = myInput.read(buffer)) > 0){
			myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();
	}
	public void open(){
		mydb = SQLiteDatabase.openDatabase(path + dbname, null, SQLiteDatabase.OPEN_READWRITE);
	}
	public void close(){
		mydb.close();
	}

	public String Query(int id,int fild){
		Cursor cu = mydb.rawQuery("SELECT * FROM readdata WHERE id"+"='"+id+"'", null);
		cu.moveToFirst();
		String name = cu.getString(fild);
		cu.close();
		return name;
	}
	public void Update(String value, int id,String row){
		ContentValues cv = new ContentValues();
		cv.put(row, value);
		mydb.update("readdata", cv, "id=" + id, null);
	}
	public void insert(String hour,String minute,String second,String date,String daytime){
		ContentValues cv = new ContentValues();
		cv.put("hour", hour);
        cv.put("minute", minute);
        cv.put("second", second);
        cv.put("date", date);
        cv.put("daytime", daytime);
		mydb.insert("readdata", null, cv);
	}
	public Integer count(){
		Cursor cu = mydb.query("readdata", null, null, null, null, null, null);
		int s = cu.getCount();
		return s;
	}
	public void delete(int id){
		mydb.delete("readdata", "id='" + id+"'", null);
	}
}