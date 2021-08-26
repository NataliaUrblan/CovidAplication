package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

    }
    public void BtnEstudiante (View view){
        Intent i = new Intent(this,LoginEstudiante.class);
        startActivity(i);
    }
    public void BtnEmpleado(View view){
        Intent i = new Intent(this,LoginEmpleado.class);
        startActivity(i);
    }
    public void BtnInvitado (View view){
        Intent i = new Intent(this, LoginInvitado.class);
        startActivity(i);
    }
}