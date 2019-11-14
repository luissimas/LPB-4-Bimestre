package com.example.aluno.trabalho_4bim;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.trabalho_4bim.Controller.UsuarioCRUD;
import com.example.aluno.trabalho_4bim.Model.Usuario;
import com.example.aluno.trabalho_4bim.Model.UsuarioLogin;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuarioCadastro;
    private EditText txtSenhaCadastro;
    private EditText txtConfirmarSenhaCadastro;
    private EditText txtUsuarioLogin;
    private EditText txtSenhaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuarioCadastro =(EditText) findViewById(R.id.txtUsuarioCadastro);
        txtSenhaCadastro =(EditText) findViewById(R.id.txtSenhaCadastro);
        txtConfirmarSenhaCadastro =(EditText) findViewById(R.id.txtConfirmarSenhaCadastro);
        txtUsuarioLogin =(EditText) findViewById(R.id.txtUsuarioLogin);
        txtSenhaLogin =(EditText) findViewById(R.id.txtSenhaLogin);
    }

    public void cadastro(View v){
        Usuario usuario = new Usuario();
        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
        Cursor tabelaUsuarios = null;
        Boolean usuarioCadastrado = false;

        try{
            tabelaUsuarios = usuarioCRUD.listarSQLite(getBaseContext());

            while(tabelaUsuarios.moveToNext()){
                if(tabelaUsuarios.getString(1).equals(txtUsuarioCadastro.getText().toString())){
                    usuarioCadastrado = true;
                    break;
                }
            }

            if(!usuarioCadastrado){
                if(txtSenhaCadastro.getText().toString().equals(txtConfirmarSenhaCadastro.getText().toString())){
                    usuario.setNome(txtUsuarioCadastro.getText().toString());
                    usuario.setSenha(txtSenhaCadastro.getText().toString());
                    usuarioCRUD.gravarSQLite(getBaseContext(), usuario);

                    Toast.makeText(getBaseContext(),"Usuário: "+usuario.getNome()+" cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"As senhas devem coincidir",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getBaseContext(),"Usuário: "+tabelaUsuarios.getString(1)+" já foi cadastrado.",Toast.LENGTH_SHORT).show();
            }

            limpar();
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View v){
        Usuario usuario = new Usuario();
        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();

        try{
            usuario.setNome(txtUsuarioLogin.getText().toString());
            usuario.setSenha(txtSenhaLogin.getText().toString());
            usuario = usuarioCRUD.login(getBaseContext(), usuario);

            if(usuario != null){
                UsuarioLogin.setLogin(usuario);

                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }else{
                limpar();
                Toast.makeText(getBaseContext(),"Login ou senha incorretos",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(getBaseContext(),"Erro: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void limpar(){
        txtUsuarioLogin.setText("");
        txtUsuarioCadastro.setText("");
        txtSenhaLogin.setText("");
        txtSenhaCadastro.setText("");
        txtConfirmarSenhaCadastro.setText("");
    }
}
