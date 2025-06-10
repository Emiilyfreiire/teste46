package com.example.teste45;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CachorroDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cachorros.db";
    private static final int DB_VERSION = 1;

    public CachorroDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE cachorro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "raca TEXT," +
                "idadeHumana INTEGER," +
                "idadeCachorro INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cachorro");
        onCreate(db);
    }
}
