package com.example.covidaplication;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EstudianteIngresado extends AppCompatActivity
{
    /*Variables para el metodo getUsuario */
    TextView tvUsuario;
    TextView pruebax;
    String Matriculaingresada="";
    RequestQueue requestQueue;
    /*Variables para el metodo getPreguntas */
    RequestQueue requestQueuePreguntas;
    TextView tvp;
    String url = "http://services.uteq.edu.mx/api/covid19/context/CovidCuestionarioPreguntas";
    /*Variables para el listview*/
    List<String> datos = new ArrayList<String>();
    ListView lstDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_ingresado);
        /* Variables inicializadas para el Metodo getUsuario*/
        tvUsuario=findViewById(R.id.prueba);
        Matriculaingresada=getIntent().getStringExtra("MatriculaIngresada");
        getUsuario(Matriculaingresada);
        getPreguntas();
        /*Inicializacion de listview*/
        lstDatos=(ListView)findViewById(R.id.lv);
    }
    private void getUsuario(String Matricula){
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonarrayRequest= new JsonArrayRequest(Request.Method.GET,"http://services.uteq.edu.mx/api/covid19/personas/Alumnos/"+Matricula,
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
                                    tvUsuario.append(matricula + "\n" + nompersona + "\n" + appaterno + "" + apmaterno + "\n" + idpersona + "\n");
                               // Toast.makeText(EstudianteIngresado.this, obj.length, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        requestQueue.add(jsonarrayRequest);
    }

    private void getPreguntas(){
        requestQueuePreguntas = Volley.newRequestQueue(this);
        JsonArrayRequest requestPreguntas = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    try{
                    for(int i=0; i<response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        String preguntas = obj.getString("pregunta");
                        datos.add(preguntas);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,datos);
                        lstDatos.setAdapter(adapter);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x=0;
            }
        });
      requestQueuePreguntas.add(requestPreguntas);
    }
    public void RespuestasCuestionarioE(){

    }
}