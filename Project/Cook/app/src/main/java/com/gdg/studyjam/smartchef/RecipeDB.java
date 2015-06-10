package com.gdg.studyjam.smartchef;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anu on 5/29/2015.
 */
public class RecipeDB extends SQLiteOpenHelper {

    private static String DB_NAME = "recipe_db";
    private static int DB_VERSION = 1;
    public static String TABLE_RECIPE = "recipes";
    public static String TABLE_RECIPE_NAME = "recipe_name";
    public RecipeDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+TABLE_RECIPE+"(_id integer primary key autoincrement,"+TABLE_RECIPE_NAME+"varchar(255)not null"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table recipes;");
        onCreate(db);
    }
}
