package com.example.aluno.trabalho_4bim.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.trabalho_4bim.Model.Banco;
import com.example.aluno.trabalho_4bim.Model.BancoExecuteUpdate;
import com.example.aluno.trabalho_4bim.Model.BancoSQLite;
import com.example.aluno.trabalho_4bim.Model.Problema;

public class ProblemaCRUD {
    public int sincronizarPostgres(Problema problema) throws Exception {
        Banco banco = null;
        BancoExecuteUpdate bancoExecuteUpdate = null;
        int r= -1;
        String erro="";

        try{
            banco = new Banco();
            banco.comando=Banco.conexao.prepareStatement("Insert into problema(usuario, descr, latitude, longitude, dia) values(?,?,?,?,?)");
            banco.comando.setString(1, problema.getUsuario());
            banco.comando.setFloat(2, problema.getLatitude());
            banco.comando.setFloat(3, problema.getLongitude());
            banco.comando.setDate(4, problema.getData());

            bancoExecuteUpdate = new BancoExecuteUpdate(banco.comando);
            r = bancoExecuteUpdate.execute().get(); //Executa o sql em paralelo

            banco.desconectar();

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
            campos.put("latitude", problema.getLatitude());
            campos.put("longitude", problema.getLongitude());
            codigo = bancoSQLLite.insert("problema", null, campos); //devolve o código gerado pelo sql lite
            problema.setCodigo((int) codigo);
            bancoSQLLite.close();
        } catch (Exception ex) {
            throw new Exception("Erro ao gravar no SQLite: " + ex.getMessage());
        }
    }

    public Cursor listarSQLite(Context context) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        Cursor tabela= null;
        try{
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getReadableDatabase();
            tabela = bancoSQLLite.rawQuery ("Select codigo, usuario, descr, latitude, longitude, date from problema",null);
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar no SQLite: "+ex.getMessage());
        }
    }
}
