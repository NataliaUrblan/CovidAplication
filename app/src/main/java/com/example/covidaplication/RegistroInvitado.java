package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistroInvitado extends AppCompatActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_invitado);
        spinner = (Spinner)findViewById(R.id.spinner);
        String [] opciones = {"Si", "No"};
        //Mostramos con adapter las opciones
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);
    }

    //Metodo del botn
    public void MostrarOpciones(View view){
        String seleccion = spinner.getSelectedItem().toString();
        if(seleccion.equals("Si")){
            //mostrar cajon de texto
        }
    }
    public void BtnRegistrar(View view){
        Intent regisEncuesta = new Intent(this,RegistroEncuesta.class);
        startActivity(regisEncuesta);
    }
}