package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Map;


public class LoginEstudiante extends AppCompatActivity {
    TextView tv1;
    EditText EdtMatricula ;
    Button BtnIngresar;
    String Matricula="";
    int statusApi=0;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estudiante);
        EdtMatricula=findViewById(R.id.etCorreo);
        AndroidThreeTen.init(this); //se utiliza para la fecha
    }

        public void BtnIngresar(View view) {
            Matricula = EdtMatricula.getText().toString();
            if (EdtMatricula.getText().toString().equals("")) {
                Toast.makeText(this, "Tienes que ingresar una matricula válida",
                        Toast.LENGTH_LONG).show();

            } else if (EdtMatricula.getText().toString().length() < 10) {
                Toast.makeText(this, "Tamaño de matricula inválido",
                        Toast.LENGTH_LONG).show();

            }else {
            //idEmpleado=EdtIdEmpleado.getText().toString();
            // Crear nueva cola de peticiones
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //int id = 125353;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "http://services.uteq.edu.mx/api/covid19/personas/Alumnos/" + Matricula, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("XD", "ResponseXD: " + response.toString());
                            try {
                                JSONObject obj = new JSONObject(response.toString());
                                int Status = obj.getInt("status");   //Obtiene el status

                                JSONArray jsonArray = response.getJSONArray("message"); //Accede a message
                                JSONObject jsonObject = jsonArray.getJSONObject(0); //0 indica el primer objeto dentro del array.
                                String Nombre = jsonObject.getString("nompersona"); //trae nombre
                                String AppellidoPaterno = jsonObject.getString("appaterno"); //trae nombre
                                String ApellidoMaterno = jsonObject.getString("apmaterno"); //trae nombre

                                Log.i("Nombre de usuario", String.valueOf(Nombre));

                                if (Status == 200) {
                                    statusApi = 1;
                                    validarSesion(Matricula, statusApi);
                                    Log.e("ESTE ES EL STATUS RESPUESTA", String.valueOf(statusApi));
                                } else if (Status != 200) {
                                    statusApi = 0;
                                    validarSesion(Matricula, statusApi);
                                }

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
        }

    public void validarSesion( String matricula, int StatusApi) {
        if (StatusApi==1) {
            SharedPreferences preferences = getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE);
            String matriculaGuardada = preferences.getString("matricula", "No se encuentra");
            String matriculaIngresada = EdtMatricula.getText().toString();
            int solicitud = getIntent().getIntExtra("solicitud", 0);
            if (solicitud <=0) {
                Intent i = new Intent(this, EstudianteIngresado.class);
                i.putExtra("MatriculaIngresada", matriculaIngresada);
                Log.e("esta es la matricula", matriculaIngresada);
                startActivity(i);
            } else {
                acciones();
            }
        }
        else {Toast.makeText(this, "Estudiante no encontrado, registrese como invitado", Toast.LENGTH_LONG).show();}
    }
       private void acciones() {
           LocalDateTime HoraFecha = LocalDateTime.now();
           String FechaActualConvertida = HoraFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
           //conmienza codigo para consultar los valores guardados en el sharedpreferences
           SharedPreferences preferences = getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE);
           String nombreEstudiante = preferences.getString("nombreEstudiante", "No se encuentra");
           String ApellidoEstudiantep = preferences.getString("Appaterno", "No se encuentra");
           String ApellidoEstudiantem = preferences.getString("Apmaterno", "No se encuentra");
           String fecha = preferences.getString("fecha", "no hay informacion"); //BUSCA SOLO LA FECHA
           String messageGuardado = preferences.getString("messageGuardadoEstudiante", "no hay informacion");
           int statusGuardado = preferences.getInt("statusGuardadoEstudiante", 0);
           Log.e("RFECHAAA: ", fecha);
           Log.e("MENSAJE GUARDADO: ", messageGuardado);
           Log.e("STATUS GUARDADO : ", String.valueOf(statusGuardado));

           if (fecha == "no hay informacion" && messageGuardado == "no hay informacion" && statusGuardado == 0) {
               Intent i = new Intent(this, EstudianteIngresado.class);
               i.putExtra("MatriculaIngresada", EdtMatricula.getText().toString());
               startActivity(i);

           } else {
               DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
               LocalDateTime localDateTime = LocalDateTime.parse(fecha, dateTimeFormatter); //cambia el valor de la variable fecha string a date.
               //le da el formato solo fechha al localdatetime
               String FechaGuardada = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

               Log.e("Esta es la fecha Guardada convertida", FechaGuardada);
               Log.e("Valor de la fecha CUESTIONARIO : ", String.valueOf(localDateTime)); //imprime en consola el valor de la fecha
               Log.e("ESTA ES LA FECHA ACTUAL CONVERTIDA", FechaActualConvertida);

               if (EdtMatricula.getText().toString().equals("")) {
                   Toast.makeText(this, "Tienes que ingresar una matricula válida",
                           Toast.LENGTH_LONG).show();

               } else if (EdtMatricula.getText().toString().length() < 10) {
                   Toast.makeText(this, "Tamaño de matricula inválido",
                           Toast.LENGTH_LONG).show();

               } else if (FechaActualConvertida.equals(FechaGuardada)) {
                   Intent i = new Intent(this, AccesoQREstudianteLogin.class);
                   startActivity(i);
                   //comparamos para validar las sesiones

               } else {
                   Intent i = new Intent(this, EstudianteIngresado.class);
                   i.putExtra("MatriculaIngresada", EdtMatricula.getText().toString());
                   startActivity(i);
               }

           }
       }

}