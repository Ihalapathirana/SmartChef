package com.gdg.studyjam.smartchef;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Anu on 5/29/2015.
 */
public class RecipeHelper extends ContentProvider {

    static final String PROVIDER_NAME="com.gdg.studyjam.smartchef.RecipeHelper";
    static final String URL="content://"+PROVIDER_NAME+"/recipes";
    static final Uri CONTENT_URI=Uri.parse(URL);


    RecipeDB recipeDB;
    static final int RECIPES=1;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"recipes",RECIPES);
    }

    @Override
    public boolean onCreate() {
        recipeDB = new RecipeDB(getContext());

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=recipeDB.getReadableDatabase();
        Cursor cursor=db.query(RecipeDB.TABLE_RECIPE, new String[]{RecipeDB.TABLE_RECIPE_NAME},null,null,null,null,null);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case RECIPES:
                return "vnd.android.cursor.dir/vnd.com.gdg.studyjam.smartchef.recipes";
            default:
                throw new IllegalArgumentException("Unsupported URI: "+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
