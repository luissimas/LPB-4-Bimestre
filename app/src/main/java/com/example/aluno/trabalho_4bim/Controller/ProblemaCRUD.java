package com.example.aluno.trabalho_4bim.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.trabalho_4bim.Model.Banco;
import com.example.aluno.trabalho_4bim.Model.BancoExecuteUpdate;
import com.example.aluno.trabalho_4bim.Model.BancoSQLite;
import com.example.aluno.trabalho_4bim.Model.Problema;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProblemaCRUD {
    public int sincronizarPostgres(Context context, Problema problema) throws Exception {
        Banco banco = null;
        BancoExecuteUpdate bancoExecuteUpdate = null;

        BancoSQLite conexao;
        SQLiteDatabase bb;

        int r= -1;
        String erro="";

        try{
            banco = new Banco();
            conexao = new BancoSQLite(context);
            bb=conexao.getWritableDatabase();
            banco.comando=Banco.conexao.prepareStatement("Insert into problema(usuario, descr, latitude, longitude, dia) values(?,?,?,?,?)");
            banco.comando.setString(1, problema.getUsuario());
            banco.comando.setString(2, problema.getDescr());
            banco.comando.setFloat(3, problema.getLatitude());
            banco.comando.setFloat(4, problema.getLongitude());
            banco.comando.setDate(5, problema.getData());

            bancoExecuteUpdate = new BancoExecuteUpdate(banco.comando);
            r = bancoExecuteUpdate.execute().get(); //Executa o sql em paralelo

            banco.desconectar();

            bb.execSQL("update problema set sincronizado=? where codigo=?",new String[]{String.valueOf(1), String.valueOf(problema.getCodigo())});
            bb.close();

            return(r);
        }catch(Exception ex){
            if(bancoExecuteUpdate.erro.length() > 0){
                erro += "  ||  " + bancoExecuteUpdate.erro;
            }

            throw new Exception("Erro ao sincronizar: " + ex.getMessage() + erro);
        }
    }

    public void gravarSQLite(Context context, Problema problema) throws Exception {
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        ContentValues campos = new ContentValues();
        long codigo;
        try {
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getWritableDatabase();
            campos.put("usuario", problema.getUsuario());
            campos.put("descr", problema.getDescr());
            campos.put("sincronizado", problema.getSincronizado());
            campos.put("latitude", problema.getLatitude());
            campos.put("longitude", problema.getLongitude());
            codigo = bancoSQLLite.insert("problema", null, campos); //devolve o código gerado pelo sql lite
            problema.setCodigo((int) codigo);
            bancoSQLLite.close();
        } catch (Exception ex) {
            throw new Exception("Erro ao gravar no SQLite: " + ex.getMessage());
        }
    }

    public void alterarSQLite(Context context, Problema problema) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new BancoSQLite(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("update problema set descr=? where codigo=?",new String[]{problema.getDescr(), String.valueOf(problema.getCodigo())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao alterar: " + ex.getMessage());
        }
    }

    public void removerSQLite(Context context, Problema problema) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new BancoSQLite(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("delete from problema where codigo=?",new String[]{String.valueOf(problema.getCodigo())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao alterar: " + ex.getMessage());
        }
    }

    public Cursor listarSQLite(Context context, Integer sincronizado) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        Cursor tabela= null;
        try{
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getReadableDatabase();

            tabela = bancoSQLLite.rawQuery ("Select codigo, usuario, descr, sincronizado, latitude, longitude, data from problema where sincronizado = ?",new String[]{String.valueOf(sincronizado)});

            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar no SQLite: " + ex.getMessage());
        }
    }

    public Problema preencher(Context context, Integer codigoRecebido) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        Cursor tabela= null;
        Problema problema = null;
        try{
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getReadableDatabase();

            tabela = bancoSQLLite.rawQuery ("Select codigo, usuario, descr, sincronizado, latitude, longitude, data from problema where codigo=?" ,new String[]{String.valueOf(codigoRecebido)});

            if((tabela!=null)&&(tabela.moveToNext())){
                problema = new Problema();
                problema.setCodigo(tabela.getInt(0));
                problema.setUsuario(tabela.getString(1));
                problema.setDescr(tabela.getString(2));
                problema.setSincronizado(tabela.getInt(3));
                problema.setLatitude(tabela.getFloat(4));
                problema.setLongitude(tabela.getFloat(5));
                problema.setData((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(tabela.getString(6)));
            }

            return(problema);
        }
        catch (Exception ex){
            throw new Exception("Erro ao preencher: " + ex.getMessage());
        }
    }
}
