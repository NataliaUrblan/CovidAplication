package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroEncuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_encuesta);
    }
    public void BtnRegistrarFormulario(View view){
        Intent formregistro = new Intent(this,AccesoQR.class);
        startActivity(formregistro);
    }
}