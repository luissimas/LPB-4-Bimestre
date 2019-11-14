package com.example.aluno.trabalho_4bim.Model;

public class UsuarioLogin {
    public static Usuario usuario = null;

    public static Usuario getUsuario(){
        return usuario;
    }

    public static void setLogin(Usuario usuarioRecebido){
        usuario = usuarioRecebido;
    }
}
