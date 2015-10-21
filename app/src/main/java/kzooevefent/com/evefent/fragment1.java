package kzooevefent.com.evefent;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class fragment1 extends Activity {
    OnClickListener listener1 = null;
    OnClickListener listener2 = null;
    OnClickListener listener3 = null;
    OnClickListener listener4 = null;
    OnClickListener listener5 = null;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    DatabaseHelper mOpenHelper;

    private static final String DATABASE_NAME = "dbForTest.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "diary";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
                    + " text not null, " + BODY + " text not null " + ");";
            Log.i("haiyang:createDB=", sql);
            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prepareListener();
        initLayout();
        mOpenHelper = new DatabaseHelper(this);

    }

    private void initLayout() {
        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(listener1);
    }

    private void prepareListener() {
        listener1 = new OnClickListener() {
            public void onClick(View v) {
                CreateTable();
            }
        };
        listener2 = new OnClickListener() {
            public void onClick(View v) {
                dropTable();
            }
        };
        listener3 = new OnClickListener() {
            public void onClick(View v) {
                insertItem();
            }
        };
        listener4 = new OnClickListener() {
            public void onClick(View v) {
                deleteItem();
            }
        };
        listener5 = new OnClickListener() {
            public void onClick(View v) {
                showItems();
            }
        };
    }

    private void CreateTable() {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
                + " text not null, " + BODY + " text not null " + ");";
        Log.i("createDB=", sql);

        try {
            db.execSQL("DROP TABLE IF EXISTS diary");
            db.execSQL(sql);
            setTitle("drop");
        } catch (SQLException e) {
            setTitle("exception");
        }
    }

    private void dropTable() {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String sql = "drop table " + TABLE_NAME;
        try {
            db.execSQL(sql);
            setTitle(sql);
        } catch (SQLException e) {
            setTitle("exception");
        }
    }
    private void insertItem() {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String sql1 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
                + ") values('a', 'b');";
        String sql2 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
                + ") values('c', 'd');";
        try {
            Log.i("sql1=", sql1);
            Log.i("sql2=", sql2);
            db.execSQL(sql1);
            db.execSQL(sql2);
            setTitle("done");
        } catch (SQLException e) {
            setTitle("exception");
        }
    }

    private void deleteItem() {
        try {
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            db.delete(TABLE_NAME, " title = 'haiyang'", null);
            setTitle("title");
        } catch (SQLException e) {

        }

    }
    private void showItems() {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        String col[] = { TITLE, BODY };
        Cursor cur = db.query(TABLE_NAME, col, null, null, null, null, null);
        Integer num = cur.getCount();
        setTitle(Integer.toString(num));
    }
}