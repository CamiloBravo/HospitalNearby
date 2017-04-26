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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistroActivity extends AppCompatActivity {
    EditText nombre, documento, telefono, contrasena, r_contrasena, alergia, enfermedad, correo;
    EditText acudiente, t_acudiente;
    Button enviar, cancelar;
    RadioButton masculino, femenino;
    String sangre, EPS;
    Spinner ListaDesple, ListaDesple2;
    String[] items, items2;
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

        nombre = (EditText) findViewById(R.id.enombre);
        documento = (EditText) findViewById(R.id.edocumento);
        telefono = (EditText) findViewById(R.id.etelefono);
        correo = (EditText) findViewById(R.id.ecorreo);
        contrasena = (EditText) findViewById(R.id.econtrasenar);
        r_contrasena = (EditText) findViewById(R.id.econtrasenarep);
        alergia = (EditText) findViewById(R.id.ealergias);
        enfermedad = (EditText) findViewById(R.id.eenfermedades);
        acudiente = (EditText) findViewById(R.id.enombre_acudiente);
        t_acudiente = (EditText) findViewById(R.id.etelefono_acudiente);
        enviar = (Button) findViewById(R.id.benviar);
        cancelar  = (Button) findViewById(R.id.bcancelar);
        masculino = (RadioButton) findViewById(R.id.rmasculino);
        femenino = (RadioButton) findViewById(R.id.rfemenino);

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                Intent intent = new Intent();
                if(nombre.getText().toString().equals("") || documento.getText().toString().equals("") ||
                        telefono.getText().toString().equals("") || contrasena.getText().toString().equals("") ||
                        r_contrasena.getText().toString().equals("") || correo.getText().toString().equals("") ||
                        t_acudiente.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(),"Llene los campos requeridos",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                } else if(!(contrasena.getText().toString().equals(r_contrasena.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"La contraseña no coincide",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                }else {

                    intent.putExtra("sangre", sangre);
                    intent.putExtra("eps", EPS);
                    intent.putExtra("nombre", nombre.getText().toString());
                    intent.putExtra("documento", documento.getText().toString());
                    intent.putExtra("correo", correo.getText().toString());
                    intent.putExtra("contrasena", contrasena.getText().toString());
                    intent.putExtra("alergias", alergia.getText().toString());
                    intent.putExtra("enfermedades", enfermedad.getText().toString());
                    intent.putExtra("tacudiente", t_acudiente.getText().toString());

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

//    private void llamarIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }

    private void llamarIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagenfoto.setImageBitmap(imageBitmap);
//            Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
//            intent.putExtra("data", imageBitmap);
        }
    }
}