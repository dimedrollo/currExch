package ru.dopegeek.currex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "currency.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + DbSchema.CurrencyTable.NAME + "(" +
                " _id integer primary key autoincrement, " +

                DbSchema.CurrencyTable.Cols.CHARCODE + ", " +
                DbSchema.CurrencyTable.Cols.NOMINAL + ", " +
                DbSchema.CurrencyTable.Cols.NAME + ", " +
                DbSchema.CurrencyTable.Cols.VALUE + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
