package com.example.covidaplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstudianteIngresado extends AppCompatActivity
{
    private int getMatriculaEstudiante;
    private String ape="", nom="";
    private TextView nombreEstudiante, apellidoEstudiante, correoEstudiante;
    private int idSolicitud, idSolicitudCuestionarioEstudiante;
    private int rbEstado1, rbEstado2, rbEstado3,  rbEstado4, rbEstado5;
    private boolean bb1=false,bb2=false,bb3=false,bb4=false,bb5=false, bb6=false, bb7=false, bb8=false, bb9=false, bb10=false;
    private int cboxEstado1, cboxEstado2, cboxEstado3, cboxEstado4, cboxEstado5, cboxEstado6, cboxEstado7, cboxEstado8, cboxEstado9;
    private static final String TAG = "EstudianteIngresadoo";

    //Declaracion checkbox
    private CheckBox s1,s2,s3,s4,s5,s6,s7,s8,s9;

    private TextView tv_preguntas;

    private RadioButton b1, b1_2, b2, b2_2, b3, b3_2, b4, b4_2, b5, b5_2;
    private JSONArray ejm = new JSONArray();

    private RequestQueue requestQueuePreguntas;
    private RequestQueue requestQueueSintomas;
    private String urlS ="http://services.uteq.edu.mx/api/covid19/context/CovidCuestionarioSintomas";
    private String urlpreguntas = "http://services.uteq.edu.mx/api/covid19/context/CovidCuestionarioPreguntas";
    /*Variables para el listview*/
    private List<String> datos = new ArrayList<String>();
    private ListView lstDatos;

    /*Variables para el metodo getUsuario */
    private TextView tvUsuario;
    private String Matriculaingresada="";
    private RequestQueue requestQueue;
    /*Variables para el metodo getPreguntas */
    private TextView tvp;


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_ingresado);

        AndroidThreeTen.init(this); //se utiliza para la fecha
        //checkbox
        s1=(CheckBox) findViewById(R.id.s1);
        s2=(CheckBox) findViewById(R.id.s2);
        s3=(CheckBox) findViewById(R.id.s3);
        s4=(CheckBox) findViewById(R.id.s4);
        s5=(CheckBox) findViewById(R.id.s5);
        s6=(CheckBox) findViewById(R.id.s6);
        s7=(CheckBox) findViewById(R.id.s7);
        s8=(CheckBox) findViewById(R.id.s8);
        s9=(CheckBox) findViewById(R.id.s9);
        //RadioButton
        b1=(RadioButton) findViewById(R.id.rb1_1);
        b1_2=(RadioButton) findViewById(R.id.rb1_2);
        b2=(RadioButton) findViewById(R.id.rb2_1);
        b2_2=(RadioButton) findViewById(R.id.rb2_2);
        b3=(RadioButton) findViewById(R.id.rb3_1);
        b3_2=(RadioButton) findViewById(R.id.rb3_2);
        b4=(RadioButton) findViewById(R.id.rb4_1);
        b4_2=(RadioButton) findViewById(R.id.rb4_2);
        b5=(RadioButton) findViewById(R.id.rb5_1);
        b5_2=(RadioButton) findViewById(R.id.rb5_2);

        lstDatos=(ListView) findViewById(R.id.lv_sintomas);
        tv_preguntas=findViewById(R.id.tv_getPreguntas);

        /* Variables inicializadas para el Metodo getUsuario*/
        tvUsuario=findViewById(R.id.informacionUsuario);
        Matriculaingresada=getIntent().getStringExtra("MatriculaIngresada");
        getUsuario(Matriculaingresada);

        /*Inicializacion de listview*/
        lstDatos=(ListView)findViewById(R.id.lv_sintomas);

        preguntasEstudiante();
        SitomasEstudiante();
    }


    public void getUsuario(String Matricula){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //int id = 125353;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://services.uteq.edu.mx/api/covid19/personas/Alumnos/"+ Matricula,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject obj = new JSONObject(response.toString());
                            int Status = obj.getInt("status");   //Obtiene el status
                            JSONArray jsonArray = response.getJSONArray("message"); //Accede a message
                            JSONObject jsonObject = jsonArray.getJSONObject(0); //0 indica el primer objeto dentro del array.

                                    String nompersona = jsonObject.getString("nompersona"); //trae nombre
                                    String appaterno = jsonObject.getString("appaterno"); //trae apellido paterno
                                    String apmaterno = jsonObject.getString("apmaterno"); //trae apellido materno
                                    String matricula = jsonObject.getString("matricula"); //trae matricula
                                    String idpersona = jsonObject.getString("idpersona"); //trae idpersona


                                         nom = nompersona;
                                         ape = appaterno;


                                         //COMIENZA CODIGO PARA GUARDAR DATOS DEL ESTUDIANTE
                                         SharedPreferences preferencias = getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
                                         String matriculaEstudiante = matricula;
                                         String NombreEstudiante = nompersona;
                                         String ApellidoEstudianteP = apmaterno;
                                         String ApellidoEstudianteM = apmaterno;
                                         SharedPreferences.Editor Objeditor = preferencias.edit();
                                         Objeditor.putString("matricula", matriculaEstudiante);
                                         Objeditor.putString("nombreEstudiante", NombreEstudiante);
                                         Objeditor.putString("Appaterno", ApellidoEstudianteP);
                                         Objeditor.putString("Apmaterno", ApellidoEstudianteM);
                                         Objeditor.commit(); //hace commit para guardar los datos
                                         //TERMINA CODIGO
                                         tvUsuario.append( nompersona + "\n" + appaterno + "" + apmaterno + "\n" );
                                         // Toast.makeText(EstudianteIngresado.this, obj.length, Toast.LENGTH_SHORT).show();


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
        requestQueue.add(jsonObjectRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public void preguntasEstudiante(){
        requestQueuePreguntas = Volley.newRequestQueue(this);
        JsonArrayRequest requestPreguntas = new JsonArrayRequest(Request.Method.GET, urlpreguntas, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    try{
                        for(int i=0; i<response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String preguntas = obj.getString("pregunta");
                            tv_preguntas.append(preguntas+"\n");
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


    public void SitomasEstudiante(){
        requestQueueSintomas= Volley.newRequestQueue(this);
        JsonArrayRequest requestSintomas = new JsonArrayRequest(Request.Method.GET, urlS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response.length()>0){
                    try{
                        for(int i=0; i<response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String sintomas = obj.getString("sintoma");
                            datos.add(sintomas);
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
        requestQueueSintomas.add(requestSintomas);
    }


    public void RespuestasQuestionarioEstudiante(View view) {

        SolicitudesRespuestas();
    }

    public void  SolicitudesRespuestas(){ //METODO QUE CREA UNA SOLICITUD Y GUARDA LAS RESPUESTAS DEL CUESTIONARIO
        //POST CREA LA SOLICITUD DE ACCESO PARA UN CUESTIONARIO
        LocalDateTime HoraAcceso= LocalDateTime.now();
        String urlSolicitudAcceso ="http://services.uteq.edu.mx/api/covid19/context/CovidSolicitudesAcceso";
        // Optional Parameters to pass as POST request
        JSONObject jsSolicitud = new JSONObject();
        try {
            jsSolicitud.put("fecha_solicitud", HoraAcceso);
            jsSolicitud.put("persona_solicitud","Estudiante");
            jsSolicitud.put("tipo_persona",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Make request for JSONObject
        JsonObjectRequest jsonObjReqSolicitud = new JsonObjectRequest(
                Request.Method.POST, urlSolicitudAcceso, jsSolicitud,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("","Response: " + response.toString());
                        try {
                            JSONObject objSolicitudA = new JSONObject(response.toString());
                            idSolicitud= objSolicitudA.getInt("id_solicitud");
                            RespuestasCuestionario(idSolicitud);

                            Log.i("", "ESTE ES EL ID: "+ idSolicitud);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjReqSolicitud);
        Log.i("", "ESTE ES EL ID: "+ idSolicitud);
    }


    public void RespuestasCuestionario (int idSolicitud){
        Log.i("", "ESTE ES EL ID: "+ idSolicitud);
        LocalDateTime HoraFechaActualE = LocalDateTime.now();
        if (b1.isChecked()){
            rbEstado1=1;
            bb1=true;
        }else
        if (b1_2.isChecked()) {
            rbEstado1=0;
            bb2=true;
        }
        if (b2.isChecked()){
            rbEstado2=1;
            bb3=true;

        }else  if (b2_2.isChecked()){
            rbEstado2=0;
            bb4=true;
        }
        if (b3.isChecked()){
            rbEstado3=1;
            bb5=true;
        }else  if (b3_2.isChecked()){
            rbEstado3=0;
            bb6=true;
        }
        if (b4.isChecked()){
            rbEstado4=1;
            bb7=true;

        }else  if (b4_2.isChecked()){
            rbEstado4=0;
            bb8=true;
        }
        if (b5.isChecked()){
            rbEstado5=1;
            bb9=true;
        }else  if (b5_2.isChecked()){
            rbEstado5=0;
            bb10=true;
        }
        //condiciones para checkbox
        if (s1.isChecked()){
            cboxEstado1=1;
        }

        if (s2.isChecked()){
            cboxEstado2=1;
        }else {
            cboxEstado2=0;
        }

        if (s3.isChecked()){
            cboxEstado3=1;
        }else {
            cboxEstado3=0;
        }
        if (s4.isChecked()){
            cboxEstado4=1;
        }else {
            cboxEstado4=0;
        }
        if (s5.isChecked()){
            cboxEstado5=1;
        }else {
            cboxEstado5=0;
        }
        if (s6.isChecked()){
            cboxEstado6=1;
        }else {
            cboxEstado6=0;
        }
        if (s7.isChecked()){
            cboxEstado7=1;
        }else {
            cboxEstado7=0;
        }
        if (s8.isChecked()){
            cboxEstado8=1;
        }else {
            cboxEstado8=0;
        }
        if (s9.isChecked()){
            cboxEstado9=1;
        }else {
            cboxEstado9=0;
        }

            //REALIZA EL POST CON LAS RESPUESTAS DE UN CUESTIONARIO
            String url = "http://services.uteq.edu.mx/api/covid19/app/CovidCuestionarios";
            // Optional Parameters to pass as POST request
            JSONObject js = new JSONObject();
            try {
                js.put("p1", rbEstado1);
                js.put("p2", rbEstado2);
                js.put("p3", rbEstado3);
                js.put("p4", rbEstado4);
                js.put("p5", rbEstado5);
                js.put("s1", cboxEstado1);
                js.put("s2", cboxEstado2);
                js.put("s3", cboxEstado3);
                js.put("s4", cboxEstado4);
                js.put("s5", cboxEstado5);
                js.put("s6", cboxEstado6);
                js.put("s7", cboxEstado7);
                js.put("s8", cboxEstado8);
                js.put("s9", cboxEstado9);
                js.put("fechamod", HoraFechaActualE);
                js.put("id_solicitud", idSolicitud);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Make request for JSONObject
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("", "Response: " + response.toString());
                            try {
                                JSONObject obj = new JSONObject(response.toString());

                                int idSolicitudCuestionario = obj.getInt("id_solicitud");
                                DictamenSolicitud(idSolicitudCuestionario);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

            };
            Volley.newRequestQueue(this).add(jsonObjReq);
            //COMIENZA CODIGO PARA GUARDAR FECHA DEL CUESTIONARIO
            SharedPreferences preferencias = getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
            String fechaString = HoraFechaActualE.toString(); //convierte hora y fecha en string para poder almacenarla en shared preferences
            SharedPreferences.Editor Objeditor = preferencias.edit();
            Objeditor.putString("fecha", fechaString); // Guarda fecha
            Objeditor.commit(); //hace commit para guardar los datos
            //TERMINA CODIGO DE FECHA CUESTIONARIO
        }

    public void DictamenSolicitud(int idDictamenSolicitud){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://services.uteq.edu.mx/api/covidDictamenSolicituds/DictaminaSolicitud/"+idDictamenSolicitud,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("XD","ResponseXD: " + response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            int status= obj.getInt("status");
                            String message= obj.getString("message");
                            EnvioDictamenaQREstudiante(status, message);
                            //EnvioDictamenaQREstudianteLogin(status, message);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
    public void EnvioDictamenaQREstudiante(int status, String message){
        ///inicia codigo apra guardar el message y status del dictamen
        SharedPreferences preferencias = getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
        SharedPreferences.Editor Objeditor = preferencias.edit();
        Objeditor.putString("messageGuardadoEstudiante", message); // guarda correo
        Objeditor.putInt("statusGuardadoEstudiante", status); // guarda nombre
        Objeditor.commit(); //hace commit para guardar los datos
        //termina codigo apra guardar el message y status del dictamen
        Intent i = new Intent(this, AccesoQREstudiante.class);
        i.putExtra("NomEstudiante",  nom);
        i.putExtra("ApeEstudiante",  ape);
        i.putExtra("Status",  status);
        i.putExtra("Message",  message);
        startActivity(i);
    }

}







