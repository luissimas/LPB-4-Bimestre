package com.example.aluno.trabalho_4bim.Model;

public class Imagem {
    private int codigo;
    private int codProblema;
    private byte[] foto;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigoRecebido) {
        codigo = codigoRecebido;
    }

    public void setCodigo(String codigoRecebido) {
        codigo = Integer.parseInt(codigoRecebido);
    }

    public int getCodProblema(){
        return codProblema;
    }

    public void setCodProblema(int codProblemaRecebido){
        codProblema = codProblemaRecebido;
    }

    public void setCodProblema(String codProblemaRecebido){
        codProblema = Integer.parseInt(codProblemaRecebido);
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] fotoRecebido) {
        foto = fotoRecebido;
    }
}
