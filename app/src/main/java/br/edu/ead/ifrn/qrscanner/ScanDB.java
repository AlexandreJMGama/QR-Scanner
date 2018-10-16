package br.edu.ead.ifrn.qrscanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScanDB extends SQLiteOpenHelper {

    public static String NOME_BANCO = "dbCodigos";
    public static String TABELA = "qrcodigo";
    public static String COL_ID = "_id";
    public static String COL_TITULO = "titulo";
    public static String COL_CODIGOS = "codigo";

    public ScanDB(Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA +" (" +
                COL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_TITULO + " TEXT, "+
                COL_CODIGOS + " TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABELA);
        onCreate(db);
    }
}
