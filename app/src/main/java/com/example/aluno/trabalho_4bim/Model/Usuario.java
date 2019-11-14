package com.example.aluno.trabalho_4bim.Model;

public class Usuario {
    private int codigo;
    private String nome;
    private String senha;

    public int getCodigo(){
        return codigo;
    }

    public void setCodigo(int codigoRecebido){
        codigo = codigoRecebido;
    }

    public void setCodigo(String codigoRecebido){
        codigo = Integer.parseInt(codigoRecebido);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeRecebido) {
        nome = nomeRecebido;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senhaRecebido) {
        senha = senhaRecebido;
    }
}
