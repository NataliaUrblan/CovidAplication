


package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginInvitado extends AppCompatActivity {
    EditText etCorreo;
    String valorCorreo;
    TextView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_invitado);
        etCorreo=findViewById(R.id.etCorreo);
        users=findViewById(R.id.usuarios);

    }

    public void BtnRegistrarInvitado(View view) {
        Intent i = new Intent(this,RegistrarInvitado.class);
        startActivity(i);
    }

    public void BtnIniciarSesionInvitado(View view) { //Valida que un usuario invitado este registrado con su correo
        valorCorreo=etCorreo.getText().toString();
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Correo=preferences.getString("Correo", "no hay informacion");
        String Nombre=preferences.getString("Nombre", "no hay informacion");
        String Apellido=preferences.getString("Apellido", "no hay informacion");

        users.setText(Correo);

        if(valorCorreo.equalsIgnoreCase(Correo)){
            Intent i = new Intent(this,SesionInvitado.class);
            i.putExtra("RegistroNombreInvitado", Nombre);
            i.putExtra("RegistroApellidoInvitado", Apellido );
            i.putExtra("RegistroCorreo", Correo);
            startActivity(i);
        }else {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show();
        }
    }




}