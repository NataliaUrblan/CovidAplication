package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

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

    TextView  t2, t3, t4;

    String et2_Ingresado="";
    String et3_Ingresado="";
    String et4_Ingresado="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado_ingresado);

        et2_Ingresado=getIntent().getStringExtra("NombreInvitado");
        et3_Ingresado=getIntent().getStringExtra("ApellidoPInvitado");
        et4_Ingresado=getIntent().getStringExtra("ApellidoMInvitado");

        t2= findViewById(R.id.t2);

        sendDataInvitado(et2_Ingresado, et3_Ingresado, et4_Ingresado);
    }

    public void sendDataInvitado(String nombre, String appellido1, String appellido2){
        t2.setText("Bienvenido "+ nombre+appellido1+appellido2);
    }
}





