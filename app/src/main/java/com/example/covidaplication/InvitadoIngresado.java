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

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitadoIngresado extends AppCompatActivity {

    private TextView nombreInvitado, apellidoInvitado, correoInvitado;
    public int idSolicitud, idSolicitudCuestionarioInvitado;
    int rbEstado1, rbEstado2, rbEstado3,  rbEstado4, rbEstado5;
    int cboxEstado1, cboxEstado2, cboxEstado3, cboxEstado4, cboxEstado5, cboxEstado6, cboxEstado7, cboxEstado8, cboxEstado9;
    private static final String TAG = "EmpleadoIngresadoo";
    //Declaracion checkbox
    CheckBox s1,s2,s3,s4,s5,s6,s7,s8,s9;
    /*Variables para el metodo getUsuario */
    String idejemplo="1526";
    TextView tv_preguntas,tvInvit,tvInvitadoNom, tvInvitadoApe, tvInvitadoCorr;
    int IdSolicitudInvitado;

    RadioButton b1, b1_2, b2, b2_2, b3, b3_2, b4, b4_2, b5, b5_2;
    JSONArray ejm = new JSONArray();

    RequestQueue requestQueuePreguntas;
    RequestQueue requestQueueSintomas;
    String urlS ="http://services.uteq.edu.mx/api/covid19/context/CovidCuestionarioSintomas";
    String urlpreguntas = "http://services.uteq.edu.mx/api/covid19/context/CovidCuestionarioPreguntas";
    /*Variables para el listview*/
    List<String> datos = new ArrayList<String>();
    ListView lstDatos;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado_ingresado);
        //button
        nombreInvitado=findViewById(R.id.NombreInvitado );
        apellidoInvitado=findViewById(R.id.ApellidoInvitado);
        correoInvitado=findViewById(R.id.CorreoInvitado);
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
        tv_preguntas=(TextView) findViewById(R.id.tv_getPreguntas);
        tvInvitadoNom=findViewById(R.id.NombreInvitado);
        tvInvitadoApe=findViewById(R.id.ApellidoInvitado);
        tvInvitadoCorr=findViewById(R.id.CorreoInvitado);
        tvInvit=findViewById(R.id.tvEmpleadoIngresado);

        preguntasInvitadoIngresado();
        SitomasEmp();
        invitadoRegistrado();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void invitadoRegistrado(){ //Consulta la informacion guardada en credenciales
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Correo=preferences.getString("Correo", "no hay informacion");
        String Nombre=preferences.getString("Nombre", "no hay informacion");
        String Apellido=preferences.getString("Apellido", "no hay informacion");
        String fecha=preferences.getString("fecha", "no hay informacion");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(fecha, dateTimeFormatter); //cambia el valor de la variable fecha string a date.
        nombreInvitado.setText(Nombre);
        apellidoInvitado.setText(Apellido);
        correoInvitado.setText(Correo );
        Log.e("", String.valueOf(localDateTime)); //imprime en consola el valor de la fecha

    }

    private void preguntasInvitadoIngresado(){
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

    public void SitomasEmp(){
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

    public void RespuestasQuestionarioInvitado(View view) {
        LocalDateTime HoraFechaActual = LocalDateTime.now();
        if (b1.isChecked()){
            rbEstado1=1;
        }else
        if (b1_2.isChecked()) {
            rbEstado1=0;
        }
        if (b2.isChecked()){
            rbEstado2=1;

        }else  if (b2_2.isChecked()){
            rbEstado2=0;
        }
        if (b3.isChecked()){
            rbEstado3=1;

        }else  if (b3_2.isChecked()){
            rbEstado3=0;
        }
        if (b4.isChecked()){
            rbEstado4=1;

        }else  if (b4_2.isChecked()){
            rbEstado4=0;
        }
        if (b5.isChecked()){
            rbEstado5=1;

        }else  if (b5_2.isChecked()){
            rbEstado5=0;
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

        String url ="http://services.uteq.edu.mx/api/covid19/app/CovidCuestionarios";
        IdSolicitudInvitado=getIntent().getIntExtra( "idSolicitudInvitado", 0); //recupera el id de la solicitud
        JSONObject js = new JSONObject();

        try {
            js.put("p1",rbEstado1);
            js.put("p2",rbEstado2);
            js.put("p3",rbEstado3);
            js.put("p4",rbEstado4);
            js.put("p5",rbEstado5);
            js.put("s1",cboxEstado1);
            js.put("s2",cboxEstado2);
            js.put("s3",cboxEstado3);
            js.put("s4",cboxEstado4);
            js.put("s5",cboxEstado5);
            js.put("s6",cboxEstado6);
            js.put("s7",cboxEstado7);
            js.put("s8",cboxEstado8);
            js.put("s9",cboxEstado9);
            js.put("fechamod", HoraFechaActual);
            js.put("id_solicitud", IdSolicitudInvitado );


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("","Response: " + response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            idSolicitudCuestionarioInvitado=obj.getInt("id_solicitud");
                            DictamenSolicitud(idSolicitudCuestionarioInvitado);

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
        Log.println(IdSolicitudInvitado,"","" );

         //COMIENZA CODIGO PARA GUARDAR FECHA DEL CUESTIONARIO
            SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
            String fechaString=HoraFechaActual.toString(); //convierte hora y fecha en string para poder almacenarla en shared preferences
            SharedPreferences.Editor Objeditor = preferencias.edit();
            Objeditor.putString("fecha",fechaString); // Guarda fecha
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
                            EnvioDictamenaQRInvitado(status, message);

                                ///inicia codigo apra guardar el message y status del dictamen
                                SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
                                SharedPreferences.Editor Objeditor = preferencias.edit();
                                Objeditor.putString("message", message); // guarda correo
                                Objeditor.putInt("status", status); // guarda nombre
                                Objeditor.commit(); //hace commit para guardar los datos
                                //termina codigo apra guardar el message y status del dictamen

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
    public void EnvioDictamenaQRInvitado(int status, String message){
        Intent i = new Intent(this, AccesoQRInvitado.class);
        i.putExtra("Status",  status);
        i.putExtra("Message",  message);
        startActivity(i);
    }

}







