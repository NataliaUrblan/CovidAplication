package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitadoIngresado extends AppCompatActivity {

    private TextView nombreInv, apellido, correo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado_ingresado);
        nombreInv=findViewById(R.id.nomInvitado);
        apellido=findViewById(R.id.ApellidoInvitado);
        correo=findViewById(R.id.correoInvitado);
        invitadoRegistrado();

    }
public void invitadoRegistrado(){

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Correo=preferences.getString("Correo", "no hay informacion");
        String Nombre=preferences.getString("Nombre", "no hay informacion");
        String Apellido=preferences.getString("Apellido", "no hay informacion");
        nombreInv.setText(Nombre);
        apellido.setText(Apellido);
        correo.setText(Correo);

    }

}





