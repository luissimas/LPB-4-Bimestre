package com.example.aluno.trabalho_4bim.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.trabalho_4bim.Model.Banco;
import com.example.aluno.trabalho_4bim.Model.BancoExecuteUpdate;
import com.example.aluno.trabalho_4bim.Model.BancoSQLite;
import com.example.aluno.trabalho_4bim.Model.Imagem;

public class ImagemCRUD {
    public int sincronizarPostgres(Imagem imagem) throws Exception {
        Banco banco = null;
        BancoExecuteUpdate bancoExecuteUpdate = null;
        int r= -1;
        String erro="";

        try{
            banco = new Banco();
            banco.comando=Banco.conexao.prepareStatement("Insert into imagem(codproblema, foto) values(?,?)");
            banco.comando.setInt(1, imagem.getCodProblema());
            banco.comando.setBytes(2, imagem.getFoto());

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

    public void gravarSQLite(Context context, Imagem imagem) throws Exception {
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        ContentValues campos = new ContentValues();
        long codigo;
        try {
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getWritableDatabase();
            campos.put("codProblema", imagem.getCodProblema());
            campos.put("foto", imagem.getFoto());
            codigo = bancoSQLLite.insert("imagem", null, campos); //devolve o código gerado pelo sql lite
            imagem.setCodigo((int) codigo);
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
            tabela = bancoSQLLite.rawQuery ("Select codigo, codproblema, foto from imagem",null);
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar no SQLite: "+ex.getMessage());
        }
    }
}
