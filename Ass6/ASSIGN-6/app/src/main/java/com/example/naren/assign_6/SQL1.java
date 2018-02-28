package com.example.naren.assign_6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by naren on 04-05-2017.
 */

public class SQL1 extends SQLiteOpenHelper {
    int DB_VERSION = 1;
    String DB_NAME = "mydb.db";
    Context context;
    public SQL1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // Store the context for later use
        this.context = context;
        DB_VERSION = version;
        DB_NAME = name;
        //check if db exists, if not it calls onCreate If it does exists then it checks
        //the version. If the version is different then it calls the onUpgrade.
    }
    //if you want just basic, use this constructor instead of the above
    // public SQL (Context context)
    //{
    //	super(context, DB_NAME,null,DB_VERSION);
    // 	this.context = context;
    //}


    @Override
    public void onCreate(SQLiteDatabase db) {

       /* db.execSQL("CREATE TABLE employees ("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "ext TEXT NOT NULL, "
                + "mob TEXT NOT NULL, "
                + "age INTEGER NOT NULL DEFAULT '0')"
        );*/


        db.execSQL("CREATE TABLE employees ("
                        + " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "age TEXT NOT NULL, "
                + "colors TEXT NOT NULL, "
                + "diet TEXT NOT NULL, "
                + "food TEXT NOT NULL,"
                + "food2 TEXT NOT NULL,"
                + "gender TEXT NOT NULL,"
                + "names TEXT NOT NULL,"
                + "salary INTEGER NOT NULL DEFAULT '0')"
        );



        //better to create a txt file with all the scripts if they become complex
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //erase or upgrade database

    }

}

