package com.example.aluno.trabalho_4bim.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
create table problema(
codigo serial primary key,
usuario varchar(50),
descr varchar(2048),
latitude float,
longitude float,
dia timestamp);

create table imagem(
codigo serial primary key,
codproblema int not null references problema(codigo),
foto bytea);
 */

public class Banco implements Runnable {

    public static Connection conexao = null;
    public PreparedStatement comando = null;
    public ResultSet tabela = null;
    public String erro = "";

    public Banco() throws Exception {
        conectar();
        if (erro.length() > 0)
            throw new Exception(this.erro);

    }

    public void conectar() {
        Thread tConexao;
        try {
            if ((conexao == null) || (conexao.isClosed())) {
                tConexao = new Thread(this);
                tConexao.start(); //Inicia a execução do thread
                try {
                    tConexao.join(); //Espera a conclusão do thread
                } catch (Exception ex) {
                    this.erro = "Erro ao conectar: " + ex.getMessage();
                }
            }
        } catch (Exception ex) {
            this.erro = "Erro ao conectar: " + ex.getMessage();
        }
    }

    public void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (Exception ex) {
                this.erro = "Erro ao desconectar: " + ex.getMessage();
            } finally {
                conexao = null;
            }
        }
    }

    //Executado quando o thread.start() é chamado
    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            if ((conexao == null) || (conexao.isClosed())) {
                conexao = DriverManager.getConnection("jdbc:postgresql://10.114.78.33:5432/LPB", "postgres", "ifsp");
            }
        } catch (Exception ex) {
            this.erro = "Erro de conexao run:" + ex.getMessage();
        }
    }
}
