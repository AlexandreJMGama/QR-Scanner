package br.edu.ead.ifrn.qrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ControlScanDB {

    private SQLiteDatabase db;
    private ScanDB scanDB;

    public ControlScanDB(Context context){
        scanDB = new ScanDB(context);
    }

    public void inserir(String titulo,String codigoScaneado){
        ContentValues dados = new ContentValues();

        db = scanDB.getWritableDatabase();
        dados.put(scanDB.COL_TITULO, titulo);
        dados.put(scanDB.COL_CODIGOS, codigoScaneado);

        long resultado = db.insert(scanDB.TABELA, null, dados);
        db.close();

        if (resultado == -1){
            Log.d("Alex", "Erro ao inserir");
        }else {
            Log.d("Alex", "Dado inserido");
        }
    }

//    Carrega listas com um item
//    public List<String> carregaItens(){
//        List<String> itens = new ArrayList<>();
//        db = scanDB.getReadableDatabase();
//        String[] campos = {scanDB.COL_CODIGOS};
//        Cursor cursor = db.query(scanDB.TABELA, campos, null, null, null, null, null);
//        while (cursor.moveToNext()){
//            itens.add(cursor.getString(0));
//        }
//        return itens;
//    }

    public List carregarLista(){
        List<QR> lista = new ArrayList();

        db = scanDB.getReadableDatabase();
        String[] campos = {scanDB.COL_ID, scanDB.COL_TITULO,scanDB.COL_CODIGOS};
        Cursor cursor = db.query(scanDB.TABELA, campos, null, null, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(scanDB.COL_ID));
            String titulo = cursor.getString(cursor.getColumnIndex(scanDB.COL_TITULO));
            String codigo = cursor.getString(cursor.getColumnIndex(scanDB.COL_CODIGOS));

            lista.add(new QR(id, titulo, codigo));
        }

        return lista;
    }
}
