package com.example.aluno.trabalho_4bim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aluno.trabalho_4bim.Controller.ImagemCRUD;
import com.example.aluno.trabalho_4bim.Controller.ProblemaCRUD;
import com.example.aluno.trabalho_4bim.Model.Imagem;
import com.example.aluno.trabalho_4bim.Model.Problema;
import com.example.aluno.trabalho_4bim.Model.UsuarioLogin;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RegistroDeProblemas extends AppCompatActivity {

    private EditText txtDescr;
    private ImageView imgFoto;
    Bitmap fotoTirada;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_problemas);

        txtDescr =(EditText) findViewById(R.id.txtDescr);
        imgFoto =(ImageView) findViewById(R.id.imgFoto);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    public void tirarFoto(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1); // devolve 1 em caso de sucesso.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            Bundle extras = data.getExtras();
            fotoTirada = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(fotoTirada);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void gravar(View v){
        ProblemaCRUD problemaCRUD = new ProblemaCRUD();
        ImagemCRUD imagemCRUD = new ImagemCRUD();
        Problema problema = new Problema();
        Imagem imagem = new Imagem();

        Location local;

        ByteArrayOutputStream vetorByte = new ByteArrayOutputStream();
        try
        {
            local = getUltimaLocalizacaoConhecida();

            problema.setUsuario(UsuarioLogin.usuario.getNome());
            problema.setDescr(txtDescr.getText().toString());
            problema.setSincronizado(0);
            problema.setLatitude(String.valueOf(local.getLatitude()));
            problema.setLongitude(String.valueOf(local.getLongitude()));

            problemaCRUD.gravarSQLite(getBaseContext(), problema);

            //Código correto porém o aplicativo fecha ao abrir a câmera, portanto o bloco está comentado para permitir o cadastro dos problemas sem foto.
            /*
            fotoTirada.compress(Bitmap.CompressFormat.JPEG, 85, vetorByte);//public boolean compress (Bitmap.CompressFormat format, int quality (0-100)maxima qualidade, OutputStream stream)
            imagem.setFoto(vetorByte.toByteArray());
            imagem.setCodProblema(problema.getCodigo());
            imagemCRUD.gravarSQLite(getBaseContext(),imagem);
            */

            limpar();
            Toast.makeText(this, "Problema registrado com sucesso!",Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(this, "Erro: "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private Location getUltimaLocalizacaoConhecida() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> provedores;
        Location bestLocation = null;
        Location local;

        if( (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);//no lugar do um basta um maior que zero
        }

        provedores = locationManager.getProviders(true);
        for (String provider : provedores) {
            local = locationManager.getLastKnownLocation(provider);
            if (local != null) {
                if ((bestLocation == null) || (local.getAccuracy() < bestLocation.getAccuracy())) {
                    bestLocation = local;
                }
            }
        }
        return bestLocation;
    }

    public void limpar(){
        txtDescr.setText("");
        imgFoto.setImageBitmap(null);
    }
}
