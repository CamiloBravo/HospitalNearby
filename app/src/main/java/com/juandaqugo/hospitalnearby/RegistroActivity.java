package com.juandaqugo.hospitalnearby;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistroActivity extends AppCompatActivity {
    EditText enombre, edocumento, etelefono, econtrasena, er_contrasena, ealergias, eenfermedad, ecorreo;
    EditText eacudiente, etelacudiente;
    Button enviar, cancelar;
    RadioButton masculino, femenino;
    String sangre, EPS, sexo, nombre, documento, telefono, alergias, enfermedad, correo, acudiente, telacudiente;
    Spinner ListaDesple, ListaDesple2;
    String[] items, items2;
    FirebaseDatabase databaseUsuarios;
    DatabaseReference myRef;
    Usuarios usuarios;
//    String mCurrentPhotoPath;
//
    Button bfoto;
    ImageView imagenfoto;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ListaDesple = (Spinner) findViewById(R.id.ListaDesple);
        items = getResources().getStringArray(R.array.Tipo_de_Sangre);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ListaDesple.setAdapter(adaptador);

        ListaDesple2 = (Spinner) findViewById(R.id.ListaDesple2);
        items2 = getResources().getStringArray(R.array.EPS);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items2);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ListaDesple2.setAdapter(adaptador2);

        bfoto = (Button) findViewById(R.id.bselector);
        imagenfoto = (ImageView) findViewById(R.id.ifoto);

        bfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();
            }
        });

        sangre = ListaDesple.getSelectedItem().toString();
        EPS = ListaDesple2.getSelectedItem().toString();

        enombre = (EditText) findViewById(R.id.enombre);
        edocumento = (EditText) findViewById(R.id.edocumento);
        etelefono = (EditText) findViewById(R.id.etelefono);
        ecorreo = (EditText) findViewById(R.id.ecorreo);
        econtrasena = (EditText) findViewById(R.id.econtrasenar);
        er_contrasena = (EditText) findViewById(R.id.econtrasenarep);
        ealergias = (EditText) findViewById(R.id.ealergias);
        eenfermedad = (EditText) findViewById(R.id.eenfermedades);
        eacudiente = (EditText) findViewById(R.id.enombre_acudiente);
        etelacudiente = (EditText) findViewById(R.id.etelefono_acudiente);
        enviar = (Button) findViewById(R.id.benviar);
        cancelar  = (Button) findViewById(R.id.bcancelar);
        masculino = (RadioButton) findViewById(R.id.rmasculino);
        femenino = (RadioButton) findViewById(R.id.rfemenino);

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                Intent intent = new Intent();
                databaseUsuarios = FirebaseDatabase.getInstance();

                documento = edocumento.getText().toString();
                nombre = enombre.getText().toString();
                telefono = etelefono.getText().toString();
                correo = ecorreo.getText().toString();
                alergias = ealergias.getText().toString();
                enfermedad = eenfermedad.getText().toString();
                acudiente = eacudiente.getText().toString();
                telacudiente = etelacudiente.getText().toString();

                myRef = databaseUsuarios.getReference("Contactos").child(String.valueOf(documento));
                usuarios = new Usuarios(String.valueOf(documento),nombre, sexo, sangre, telefono, correo, EPS, alergias, enfermedad, acudiente, telacudiente);
                myRef.setValue(usuarios);

                if(enombre.getText().toString().equals("") || edocumento.getText().toString().equals("") ||
                        etelefono.getText().toString().equals("") || econtrasena.getText().toString().equals("") ||
                        er_contrasena.getText().toString().equals("") || ecorreo.getText().toString().equals("") ||
                        eenfermedad.getText().toString().equals("") || eacudiente.getText().toString().equals("") ||
                        sangre.equals("") || EPS.equals("") ||
                        ealergias.getText().toString().equals("") || etelacudiente.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                } else if(!(econtrasena.getText().toString().equals(er_contrasena.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"La contraseña no coincide",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                }else {
                    if(masculino.isChecked()){
                        sexo="Masculino";
                    }else if(femenino.isChecked()){
                        sexo="Femenino";
                    }

                    intent.putExtra("sexo", sexo);
                    intent.putExtra("sangre", sangre);
                    intent.putExtra("eps", EPS);
                    intent.putExtra("nombre", enombre.getText().toString());
                    intent.putExtra("documento", edocumento.getText().toString());
                    intent.putExtra("correo", ecorreo.getText().toString());
                    intent.putExtra("contrasena", econtrasena.getText().toString());
                    intent.putExtra("alergias", ealergias.getText().toString());
                    intent.putExtra("enfermedades", eenfermedad.getText().toString());
                    intent.putExtra("telacudiente", etelacudiente.getText().toString());



                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void llamarIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagenfoto.setImageBitmap(imageBitmap);
        }
    }
}