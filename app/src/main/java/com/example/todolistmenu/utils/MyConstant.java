package com.example.todolistmenu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyConstant {
    public static final String DB_NAME = "RecordDB.db";
    public static final String TABLE_NAME = "recordInfos";
    public static final String CLOME_ID = "id";
    public static final String CLOME_RECORD = "record";
    public static final String CLOME_TIME = "time";

    public static final String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM,dd,yyyy HH.mm.ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
