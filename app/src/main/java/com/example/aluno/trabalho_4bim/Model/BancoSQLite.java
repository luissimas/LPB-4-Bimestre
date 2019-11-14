package com.example.aluno.trabalho_4bim.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSQLite extends SQLiteOpenHelper {

    public BancoSQLite(Context context){
        // contexto,nomedobanco,cursor, versão
        super(context, "BancoSQLite.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuario(codigo integer primary key autoincrement, nome text, senha text);";
        db.execSQL(sql);

        sql = "CREATE TABLE problema(codigo integer primary key autoincrement, usuario text, descr text, sincronizado boolean, latitude float, longitude float, data timestamp default current_timestamp);";
        db.execSQL(sql);

        sql = "CREATE TABLE imagem(codigo integer primary key autoincrement, codproblema integer, foto blob);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS problema");
        db.execSQL("DROP TABLE IF EXISTS imagem");
        onCreate(db);
    }
}
