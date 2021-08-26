package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginEmpleado extends AppCompatActivity {
    String usuario;
    TextView tv1;
    EditText EdtIdEmpleado;
    Button BtnIngresarEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);
        EdtIdEmpleado=findViewById(R.id.etCorreo);
        usuario=EdtIdEmpleado.getText().toString();
        BtnIngresarEmpleado=findViewById(R.id.BtnLoginRegistro);

        ValidarUsuario(usuario);
    }
    public void BtnIngresarEmp(View view){

        Intent i = new Intent(this,EmpleadoIngresado.class);
        i.putExtra("IdIngresado", EdtIdEmpleado.getText().toString());
        startActivity(i);
    }
    private void ValidarUsuario(String Usuario){

        //String url = "http://services.uteq.edu.mx/api/covid19/personas/Empleados/125353";
        // Crear nueva cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://services.uteq.edu.mx/api/covid19/personas/Empleados/"+Usuario,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("XD","ResponseXD: " + response.toString());

                            try {
                            JSONObject obj = new JSONObject(response.toString());

                                Log.i("XD","NO EXISTE WEY: " + response.toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("errrrrrrrrrrrrrrrr", "", e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("errorrr", error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


}

