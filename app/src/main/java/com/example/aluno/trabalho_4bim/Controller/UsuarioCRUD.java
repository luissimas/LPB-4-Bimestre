package com.example.aluno.trabalho_4bim.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.trabalho_4bim.Model.BancoSQLite;
import com.example.aluno.trabalho_4bim.Model.Usuario;

public class UsuarioCRUD {
    public void gravarSQLite(Context context, Usuario usuario) throws Exception {
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        ContentValues campos = new ContentValues();
        long codigo;
        try {
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getWritableDatabase();
            campos.put("nome", usuario.getNome());
            campos.put("senha", usuario.getSenha());
            codigo = bancoSQLLite.insert("usuario", null, campos); //devolve o código gerado pelo sql lite
            usuario.setCodigo((int) codigo);
            bancoSQLLite.close();
        } catch (Exception ex) {
            throw new Exception("Erro ao gravar no SQLite: " + ex.getMessage());
        }
    }

    public Usuario login(Context context, Usuario usuarioRecebido) throws Exception {
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        Usuario usuario = null;
        Cursor tabela= null;
        try {
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getReadableDatabase();
            tabela = bancoSQLLite.rawQuery ("Select codigo, nome, senha from usuario where nome = ? and senha = ?",new String[]{usuarioRecebido.getNome(), usuarioRecebido.getSenha()});

            if (tabela.moveToNext()) {
                usuario = new Usuario();

                usuario.setCodigo(tabela.getInt(0));
                usuario.setNome((tabela.getString(1)));
                usuario.setSenha((tabela.getString(2)));
            }

            return (usuario);
        } catch (Exception ex) {
            throw new Exception("Erro ao efetuar o login:" + ex.getMessage());
        }
    }

    public Cursor listarSQLite(Context context) throws Exception{
        BancoSQLite conexao;
        SQLiteDatabase bancoSQLLite;
        Cursor tabela= null;
        try{
            conexao = new BancoSQLite(context);
            bancoSQLLite = conexao.getReadableDatabase();
            tabela = bancoSQLLite.rawQuery ("Select codigo, nome, senha from usuario",null);
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar no SQLite: "+ex.getMessage());
        }
    }
}
