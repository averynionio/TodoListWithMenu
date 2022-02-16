package com.example.todolistmenu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolistmenu.bean.RecordBean;
import com.example.todolistmenu.utils.MyConstant;

import java.util.ArrayList;
import java.util.List;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table " + MyConstant.TABLE_NAME
            +"(" + MyConstant.CLOME_ID + " integer primary key autoincrement,"
            +MyConstant.CLOME_RECORD + " test,"
            +MyConstant.CLOME_TIME+ " text)";

    private SQLiteDatabase db;

    public MyOpenHelper(@Nullable Context context) {
        super(context, MyConstant.DB_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertRecord(String record, String time) {
        ContentValues values = new ContentValues();
        values.put(MyConstant.CLOME_RECORD, record);
        values.put(MyConstant.CLOME_TIME, time);
        return db.insert(MyConstant.TABLE_NAME,null, values);
    }

    public long updateRecord(String id, String record, String time) {
        ContentValues values = new ContentValues();
        values.put(MyConstant.CLOME_RECORD, record);
        values.put(MyConstant.CLOME_TIME, time);
        return db.update(MyConstant.TABLE_NAME, values, "id=?", new String[]{id});
    }

    public int deleteRecord(String id) {
        return db.delete(MyConstant.TABLE_NAME,"id=?", new String[]{id});
    }

    public List<RecordBean> queryRecord() {
        Cursor cursor = db.query(MyConstant.TABLE_NAME, null, null,null,
                null, null, MyConstant.CLOME_TIME +" desc");
        List<RecordBean> recordBeanList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = String.valueOf(cursor.getColumnIndex(MyConstant.CLOME_ID));

                int recordID = cursor.getColumnIndex(MyConstant.CLOME_RECORD);
                String record = cursor.getString(recordID);

                int timeID = cursor.getColumnIndex(MyConstant.CLOME_TIME);
                String time = cursor.getString(timeID);
                RecordBean recordBean = new RecordBean(id, record, time);
                recordBeanList.add(recordBean);
            }
            cursor.close();
        }
        return recordBeanList;
    }




}
