package com.example.aluno.trabalho_4bim.Model;

import java.sql.Date;

public class Problema {
    private int codigo;
    private String usuario;
    private String descr;
    private int sincronizado = 0;
    private float latitude;
    private float longitude;
    private Date data;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigoRecebido) {
        codigo = codigoRecebido;
    }

    public void setCodigo(String codigoRecebido) {
        codigo = Integer.parseInt(codigoRecebido);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuarioRecebido) {
        usuario = usuarioRecebido;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descrRecebido) {
        descr = descrRecebido;
    }

    public int getSincronizado(){
        return sincronizado;
    }

    public void setSincronizado(int sincronizadoRecebido){
        sincronizado = sincronizadoRecebido;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitudeRecebido) {
        latitude = latitudeRecebido;
    }

    public void setLatitude(String latitudeRecebido) {
        latitude = Float.parseFloat(latitudeRecebido);
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitudeRecebido) {
        longitude = longitudeRecebido;
    }

    public void setLongitude(String longitudeRecebido) {
        longitude = Float.parseFloat(longitudeRecebido);
    }

    public Date getData(){
        return data;
    }

    public void setData(Date dataRecebida){
        data = dataRecebida;
    }

}
