package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends AppCompatActivity {
    TextView tv1;
    EditText EdtMatricula ;
    Button BtnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdtMatricula=findViewById(R.id.EdtMatricula);
        BtnIngresar=findViewById(R.id.BtnIngresar);

    }
    public void BtnIngresar(View view){
        Intent i = new Intent(this,UsuarioIngresado.class);
        i.putExtra("MatriculaIngresada", EdtMatricula.getText().toString());
        startActivity(i);
    }

    public void BtnInvitado(View view){
        Intent invitado = new Intent(this,RegistroInvitado.class);
        startActivity(invitado);
    }

}