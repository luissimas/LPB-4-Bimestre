package com.example.aluno.trabalho_4bim.Model;

import java.util.ArrayList;

public class Lista {
    public static ArrayList<Problema> lstProblema;
    public static ArrayList<Integer> lstCodigosProblemas;

    static{

        if(lstCodigosProblemas == null){
            lstCodigosProblemas = new ArrayList<>();
        }

        if(lstProblema == null){
            lstProblema = new ArrayList<>();
        }
    }
}
