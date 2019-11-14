package com.example.aluno.trabalho_4bim.Model;

import android.os.AsyncTask;

import java.sql.PreparedStatement;

//O SQL do banco deve rodar de forma assincrona
//AsyncTask(parâmetro, progresso, resultado

public class BancoExecuteUpdate  extends AsyncTask<String, Void, Integer> {
    private PreparedStatement comando;
    public String erro;

    public BancoExecuteUpdate(PreparedStatement com) {
        this.comando = com;
        erro = "";
    }

    @Override
    protected Integer doInBackground(String... strings) { //recebe um vetor com 0 ou mais strings
        int resp=-1;
        try {
            resp = comando.executeUpdate();
        } catch (Exception ex) {
            this.erro = ex.getMessage();
        }
        return resp;
    }


}

