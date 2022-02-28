package ru.dopegeek.currex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.dopegeek.currex.database.DbHelper;
import ru.dopegeek.currex.database.DbSchema;
import ru.dopegeek.currex.database.MyCursorWrapper;


public class CurrencyFactory {

    public static List<Currency> sCurrencyList = new ArrayList<>();
    public static CurrencyFactory sCurrencyFactory;
    private static SQLiteDatabase mDatabase;
    private final Context mContext;


    private CurrencyFactory(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }

    public static CurrencyFactory get(Context context) {
        if (sCurrencyFactory == null) sCurrencyFactory = new CurrencyFactory(context);
        return sCurrencyFactory;
    }

    private static ContentValues getContentValues(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.CurrencyTable.Cols.CHARCODE, currency.getCharCode());
        values.put(DbSchema.CurrencyTable.Cols.NOMINAL, currency.getNominal() + "");
        values.put(DbSchema.CurrencyTable.Cols.NAME, currency.getName());
        values.put(DbSchema.CurrencyTable.Cols.VALUE, currency.getValue() + "");
        return values;
    }

    public static void addCurrency() {
        for (Currency currency : sCurrencyList) {
            ContentValues values = getContentValues(currency);
            int i = mDatabase.update(DbSchema.CurrencyTable.NAME, values, "CHARCODE = ?", new String[]{currency.getCharCode()});
            if (i < 1) mDatabase.insert(DbSchema.CurrencyTable.NAME, null, values);
        }
    }

    public List<Currency> getCurrencyList() {
        List<Currency> currencyList = new ArrayList<>();
        MyCursorWrapper cursor = queryValutes();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Currency currency = cursor.getData();

                currencyList.add(currency);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return currencyList;
    }

    private MyCursorWrapper queryValutes() {
        Cursor cursor = mDatabase.query(
                DbSchema.CurrencyTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new MyCursorWrapper(cursor);
    }
}
