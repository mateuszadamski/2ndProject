package math.uni.lodz.pl.a2ndproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void InsertToDatabase(View view){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE,title);
        values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_SUBTITLE,subtitle);
        long newRowId = db.insert(FeedReaderDbHelper.FeedEntry.TABLE_NAME,null,values);
    }
    public void readFromDatabase(View view){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String [] projection = {
                FeedReaderDbHelper.FeedEntry._ID,
                FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderDbHelper.FeedEntry.COLUMN_NAME_SUBTITLE
        };
        String selection = FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE + " =?";
        String[] selectionArgs = { "My Title" };
        String sortOrder = FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(
                FeedReaderDbHelper.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()){
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.FeedEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();
    }
    @Override
    protected void onDestroy(){
        mDbHelper.close();
        super.onDestroy();
    }
}
