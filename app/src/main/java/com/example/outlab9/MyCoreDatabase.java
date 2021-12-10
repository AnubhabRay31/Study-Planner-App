package com.example.outlab9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public class MyCoreDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "Education";
    private static final String DB_TABLE = "Events_table";
    private static final int DB_VER = 1;

    Context ctx;
    SQLiteDatabase myDb;

    public MyCoreDatabase(Context ct) {
        super(ct, DB_NAME, null, DB_VER);
        ctx = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type INTEGER," +
                "title_name TEXT," +
                "course_name TEXT," +
                "description TEXT," +
                "date DATE," +
                "time TIME" +
                ");"
        );
        Log.i("Database", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(sqLiteDatabase);
        Log.i("Database", "Upgraded");
    }

    public boolean insertData(int type, String title, String course, String description, LocalDate date, LocalTime time) {
        if (title.length() != 0 && course.length() != 0 && description.length() != 0
                && type >= 1 && type <= 4
                && date !=null && time !=null ) {
            myDb = getWritableDatabase();
            myDb.execSQL("INSERT INTO " +
                    DB_TABLE +
                    " (type,title_name,course_name,description,date,time) VALUES('" +
                    type + "','" +
                    title + "','" +
                    course + "','" +
                    description + "','" +
                    date + "','" +
                    time + "');"
            );
            Toast.makeText(ctx, "Data saved Successfully", Toast.LENGTH_SHORT).show();
            Log.i("Database", "A row is inserted into table");
            return true;
        }
        else {Toast.makeText(ctx, "Incomplete Entries filled !!", Toast.LENGTH_SHORT).show();
            Log.i("Database", "Incomplete Entries filled !!");
            return false;
        }
    }

    public void getAll() {
        myDb = getReadableDatabase();
        Cursor cr = myDb.rawQuery("SELECT * FROM " + DB_TABLE, null);
        StringBuilder str = new StringBuilder();

        while (cr.moveToNext()) {
            String type = cr.getString(1);
            String title = cr.getString(2);
            String course = cr.getString(3);
            String description = cr.getString(4);
            String localDate = cr.getString(5);
            String localTime = cr.getString(6);

            str.append(type).append(", ")
                    .append(title).append(", ")
                    .append(course).append(", ")
                    .append(description).append(", ")
                    .append(localDate).append(", ")
                    .append(localTime).append(".")
                    .append(" \n");
        }
        Toast.makeText(ctx, str.toString(), Toast.LENGTH_LONG).show();
        cr.close();
        Log.i("Database", "Information loaded/displayed \n" + str.toString());
    }

    public Cursor readAllData(int type){
        String query = "SELECT * FROM "+ DB_TABLE+" WHERE type="+type;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public int getEventCount(int type, String date){
        String query = "SELECT * FROM "+ DB_TABLE+" WHERE (type="+type+" AND date='"+date+"')";
        //String query = "SELECT * FROM "+ DB_TABLE+" WHERE date=CONVERT(DATE,'"+date+"',126)";
        Log.i("Database", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("Database", "geteventcount2 \n");
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
            Log.i("Database", "geteventcount3 \n");
        }
        Log.i("Database", "geteventcount4 \n");
        assert cursor != null;
        int ans = cursor.getCount();
        cursor.close();
        Log.i("Database", "abcd"+ans);
        return ans;
    }

    void updateData(String row_id, int type, String title, String course, String description, LocalDate date, LocalTime time ) {
        if (title.length() != 0 && course.length() != 0 && description.length() != 0
                && type >= 1 && type <= 4
                && date != null && time != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("type", type);
            cv.put("title_name", title);
            cv.put("course_name", course);
            cv.put("description", description);
            cv.put("date", date.toString());
            cv.put("time", time.toString());

            long result = db.update(DB_TABLE, cv, "_id=?", new String[]{row_id});
            if (result == -1) {
                Toast.makeText(ctx, "Failed to Update", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "Successfully Updated!", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(ctx, "Incomplete Entries filled !!", Toast.LENGTH_SHORT).show();
            Log.i("Database", "Incomplete Entries filled !!");
//          return false;
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(DB_TABLE,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(ctx, "Failed to Delete",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ctx, "Successfully Deleted",Toast.LENGTH_LONG).show();
        }

    }
}
