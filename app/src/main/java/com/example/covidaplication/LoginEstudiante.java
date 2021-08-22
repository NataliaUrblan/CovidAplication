package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginEstudiante extends AppCompatActivity {
    TextView tv1;
    EditText EdtMatricula ;
    Button BtnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estudiante);
        EdtMatricula=findViewById(R.id.et1);
      //  BtnIngresar=findViewById(R.id.BtnLoginInvitado);

    }

    public void BtnIngresar(View view){

        if (EdtMatricula.getText().toString().equals("")){
            Toast.makeText(this, "Tienes que ingresar una matricula válida",
                    Toast.LENGTH_LONG).show();

        }else if(EdtMatricula.getText().toString().length() <10){
            Toast.makeText(this, "Tamaño de matricula inválido",
                    Toast.LENGTH_LONG).show();
        }else {
            Intent i = new Intent(this, EstudianteIngresado.class);
            i.putExtra("MatriculaIngresada", EdtMatricula.getText().toString());
            startActivity(i);
        }

    }

}