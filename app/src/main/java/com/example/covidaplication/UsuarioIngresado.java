package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioIngresado extends AppCompatActivity {
        TextView tvUsuario;
    String Matriculaingresada="";
        RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ingresado);

        tvUsuario=findViewById(R.id.tvUsuario);

      Matriculaingresada=getIntent().getStringExtra("MatriculaIngresada");
        getUsuario(Matriculaingresada);
        Log.d("",Matriculaingresada);

    }
    private void getUsuario(String MAT){
        JsonArrayRequest jsonarrayRequest= new JsonArrayRequest(Request.Method.GET,"http://services.uteq.edu.mx/api/covid19/personas/Alumnos/"+MAT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject obj= response.getJSONObject(0);
                                String matricula = obj.getString("matricula");
                                String nompersona = obj.getString("nompersona");
                                String appaterno = obj.getString("appaterno");
                                String apmaterno = obj.getString("apmaterno");
                                String idpersona = obj.getString("idpersona");
                                tvUsuario.append(matricula+"\n"+nompersona+"\n"+appaterno+""+apmaterno+"\n"+idpersona+"\n");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY",error.getMessage());
            }
        }
        );
        requestQueue.add(jsonarrayRequest);

    }

}