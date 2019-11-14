package com.example.aluno.trabalho_4bim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void abrirRegistro(View v){
        Intent intent = new Intent(Menu.this, RegistroDeProblemas.class);
        startActivity(intent);
    }

    public void abrirDadosSincronizados(View v){
        Intent intent = new Intent(Menu.this, DadosSincronizados.class);
        startActivity(intent);
    }

    public void abrirDadosNaoSincronizados(View v){
        Intent intent = new Intent(Menu.this, DadosNaoSincronizados.class);
        startActivity(intent);
    }
}
