package com.example.taras.homeworkfragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by taras on 28.03.16.
 */
final public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Constants.DATA_BASE_NAME + " ("
                + Constants.ID_TAG + " integer primary key autoincrement,"
                + Constants.LOGIN_TAG + " text,"
                + Constants.PASSWORD_TAG + " text,"
                + Constants.FIRST_NAME_TAG + " text,"
                + Constants.LAST_NAME_TAG + " text,"
                + Constants.GENDER_TAG + " text" + ");");
    }

    public ArrayList<DataModel> loadData(String orderTag) {
        ArrayList<DataModel> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Constants.DATA_BASE_NAME, null, null, null, null, null, orderTag);

        if (!c.moveToFirst()) {
            c.close();
            close();
            return data;
        }

        int loginColumn, passwordColumn, firstNameColumn, lastNameColumn, genderColumn;

        loginColumn = c.getColumnIndex(Constants.LOGIN_TAG);
        passwordColumn = c.getColumnIndex(Constants.PASSWORD_TAG);
        firstNameColumn = c.getColumnIndex(Constants.FIRST_NAME_TAG);
        lastNameColumn = c.getColumnIndex(Constants.LAST_NAME_TAG);
        genderColumn = c.getColumnIndex(Constants.GENDER_TAG);

        do {
            DataModel person = new DataModel(c.getString(loginColumn),
                    c.getString(passwordColumn),
                    c.getString(firstNameColumn),
                    c.getString(lastNameColumn),
                    c.getString(genderColumn));
            data.add(person);
        } while (c.moveToNext());

        c.close();
        close();
        return data;
    }

    public void removePerson(DataModel person) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(Constants.DATA_BASE_NAME, null, null, null, null, null, null);

        if (!c.moveToFirst()) {
            c.close();
            close();
        }

        int loginColumn, idColumn;
        String id = null;

        loginColumn = c.getColumnIndex(Constants.LOGIN_TAG);
        idColumn = c.getColumnIndex(Constants.ID_TAG);

        do {
            if (c.getString(loginColumn).equals(person.getLogin())) {
                id = c.getString(idColumn);
            }
        } while (c.moveToNext());

        db.delete(Constants.DATA_BASE_NAME, Constants.ID_TAG + "=" + id, null);
        c.close();
        close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
