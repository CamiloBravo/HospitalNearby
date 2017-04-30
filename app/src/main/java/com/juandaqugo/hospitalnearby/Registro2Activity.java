package com.juandaqugo.hospitalnearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Registro2Activity extends AppCompatActivity {

    TextView tnombre_perfil2, tdocumento_perfil2, tsangre_perfil2, tsexo_perfil2;
    String snombre, sdocumento, ssangre, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        Bundle extras = getIntent().getExtras();

        snombre = extras.getString("nombre");
        sdocumento = extras.getString("documento");
        ssangre = extras.getString("sangre");
        sexo = extras.getString("sexo");

        tnombre_perfil2 = (TextView) findViewById(R.id.tnombre2);
        tdocumento_perfil2 = (TextView) findViewById(R.id.tdocumento2);
        tsexo_perfil2 = (TextView) findViewById(R.id.tSexo2);
        tsangre_perfil2 = (TextView) findViewById(R.id.tSangre2);
        tnombre_perfil2.setText(snombre);
        tdocumento_perfil2.setText(sdocumento);
        tsexo_perfil2.setText(sexo);
        tsangre_perfil2.setText(ssangre);

    }
}
