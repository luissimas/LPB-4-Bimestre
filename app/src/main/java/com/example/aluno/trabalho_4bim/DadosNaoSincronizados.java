package com.example.aluno.trabalho_4bim;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.trabalho_4bim.Controller.ImagemCRUD;
import com.example.aluno.trabalho_4bim.Controller.ProblemaCRUD;
import com.example.aluno.trabalho_4bim.Model.Imagem;
import com.example.aluno.trabalho_4bim.Model.Lista;
import com.example.aluno.trabalho_4bim.Model.Problema;
import com.example.aluno.trabalho_4bim.Model.UsuarioLogin;

import java.util.ArrayList;

public class DadosNaoSincronizados extends AppCompatActivity {

    ArrayList<String> listaLst;
    ArrayAdapter<String> adapterLst;

    private EditText txtDescr;
    private ListView lstProblemas;

    public Problema problemaGlobal = new Problema();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_nao_sincronizados);

        txtDescr =(EditText) findViewById(R.id.txtDescr);
        lstProblemas=(ListView) findViewById(R.id.lstView);

        listar();

        lstProblemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProblemaCRUD problemaCRUD = new ProblemaCRUD();

                Cursor tabela = null;

                try{
                    tabela = problemaCRUD.listarSQLite(getBaseContext(), 0);

                    if(tabela != null){
                        if(Lista.lstCodigosProblemas!=null){
                            Lista.lstCodigosProblemas.clear();
                        }else{
                            Lista.lstCodigosProblemas=new ArrayList<>();
                        }

                        while(tabela.moveToNext()){
                            Lista.lstCodigosProblemas.add(tabela.getInt(0));
                        }
                    }

                    if((i>=0)&&(i<Lista.lstCodigosProblemas.size())){
                        problemaGlobal = problemaCRUD.preencher(getBaseContext(), Lista.lstCodigosProblemas.get(i));

                        if(problemaGlobal.getUsuario().equals(UsuarioLogin.getUsuario().getNome())){
                            txtDescr.setText(problemaGlobal.getDescr());
                        }else{
                            Toast.makeText(getBaseContext(), "Apenas o usuario " + problemaGlobal.getUsuario() + " pode alterar ou remover o problema.",Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception ex){
                    Toast.makeText(getBaseContext(), "Erro: " + ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void listar(){
        ProblemaCRUD problemaCRUD = new ProblemaCRUD();
        Cursor tabela = null;

        try{
            tabela = problemaCRUD.listarSQLite(getBaseContext(), 0);

            if(tabela != null){
                if(listaLst!=null){
                    listaLst.clear();
                }else{
                    listaLst=new ArrayList<>();
                }

                while(tabela.moveToNext()){
                    listaLst.add(tabela.getInt(0) + " || " + tabela.getString(1) + " || " + tabela.getString(2) + " || " + tabela.getInt(3) + " || " + tabela.getDouble(4) + " || " + tabela.getDouble(5) + " || " + tabela.getString(6));
                }

                adapterLst = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLst);

                lstProblemas.setAdapter(adapterLst);
            }
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void alterar(View v){
        Problema problema = new Problema();
        ProblemaCRUD problemaCRUD = new ProblemaCRUD();

        try{
            problema.setCodigo(problemaGlobal.getCodigo());
            problema.setDescr(txtDescr.getText().toString());

            problemaCRUD.alterarSQLite(getBaseContext(), problema);

            limpar();
            listar();

            Toast.makeText(getBaseContext(),"Problema: "+problema.getDescr()+" alterado com sucesso!",Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void remover(View v){
        Problema problema = new Problema();
        ProblemaCRUD problemaCRUD = new ProblemaCRUD();

        try{
            problema.setCodigo(problemaGlobal.getCodigo());

            problemaCRUD.removerSQLite(getBaseContext(), problema);

            limpar();
            listar();

            Toast.makeText(getBaseContext(),"Problema: "+problema.getDescr()+" removido com sucesso!",Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void sincronizar(View v){
        ProblemaCRUD problemaCRUD = new ProblemaCRUD();
        ImagemCRUD imagemCRUD = new ImagemCRUD();

        try{
            problemaCRUD.sincronizarPostgres(getBaseContext(), problemaGlobal, imagemCRUD.preencher(getBaseContext(), problemaGlobal.getCodigo()));

            listar();
            limpar();
            Toast.makeText(getBaseContext(),"Problema: "+problemaGlobal.getDescr()+" sincronizado com sucesso!",Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro ao sincronizar os dados: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void limpar(){
        txtDescr.setText("");
    }
}
