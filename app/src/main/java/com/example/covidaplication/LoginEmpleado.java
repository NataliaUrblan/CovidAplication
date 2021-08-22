package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginEmpleado extends AppCompatActivity {

    TextView tv1;
    EditText EdtIdEmpleado;
    Button BtnIngresarEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);
        EdtIdEmpleado=findViewById(R.id.et1);
        BtnIngresarEmpleado=findViewById(R.id.BtnLoginInvitado);

    }
    public void BtnIngresarEmp(View view){
        Intent i = new Intent(this,EmpleadoIngresado.class);
        i.putExtra("IdIngresado", EdtIdEmpleado.getText().toString());
        startActivity(i);
    }
}

