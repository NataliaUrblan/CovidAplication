package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.IdentityHashMap;

import javax.net.ssl.SSLEngineResult;

public class LoginEmpleado extends AppCompatActivity {
    int statusApi=0;
    String usuario;
    TextView tv1;
    EditText EdtIdEmpleado;
    Button BtnIngresarEmpleado;
    String idEmpleado="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);
        EdtIdEmpleado=findViewById(R.id.etCorreo);
        usuario=EdtIdEmpleado.getText().toString();
        BtnIngresarEmpleado=findViewById(R.id.BtnLoginRegistro);
        AndroidThreeTen.init(this); //se utiliza para la fecha
       // ValidarUsuario(usuario);
    }
    public void BtnIngresarEmp(View view) {
        idEmpleado = EdtIdEmpleado.getText().toString();
        if (EdtIdEmpleado.getText().toString().equals("")) {
            Toast.makeText(this, "Tienes que ingresar una matricula válida",
                    Toast.LENGTH_LONG).show();

        } else if (EdtIdEmpleado.getText().toString().length() < 6) {
            Toast.makeText(this, "Tamaño de matricula inválido",
                    Toast.LENGTH_LONG).show();

        }else {
        // Crear nueva cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //int id = 125353;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://services.uteq.edu.mx/api/covid19/personas/Empleados/" + idEmpleado, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("XD", "ResponseXD: " + response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            int Status = obj.getInt("status");
                            String MessageE = obj.getString("message");

                            if (Status == 200) {
                                statusApi = 1;
                                verificasesion(idEmpleado, statusApi);
                                Log.e("ESTE ES EL STATUS RESPUESTA", String.valueOf(statusApi));
                            } else if (Status != 200) {
                                statusApi = 0;
                                verificasesion(idEmpleado, statusApi);
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

    public void verificasesion(String idEmpleado, int StatusApi) {
        if(StatusApi==1) {
            SharedPreferences preferences = getSharedPreferences("DatosGuardadosEmpleado", Context.MODE_PRIVATE);
            int solicitud = preferences.getInt("solicitud", 0);
            int ValorStatus = preferences.getInt("StatusValue", 0);
            if (ValorStatus > 200) {
                Log.e("ESTE ES EL VALOR BUSCADO", String.valueOf(ValorStatus));
                Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show();
            } else {
                if (solicitud <= 0) {
                    Intent i = new Intent(this, EmpleadoIngresado.class);
                    i.putExtra("IdIngresado", idEmpleado);
                    Log.e("esta es el id", idEmpleado);
                    startActivity(i);

                } else {
                    acciones();
                }
            }
        }else {
            Toast.makeText(this, "Empleado no encontrado, registrese como invitado", Toast.LENGTH_LONG).show();
        }

    }

    private void acciones() {
        LocalDateTime HoraFecha = LocalDateTime.now();
        String FechaActualConvertida = HoraFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //conmienza codigo para consultar los valores guardados en el sharedpreferences
        SharedPreferences preferences=getSharedPreferences("DatosGuardadosEmpleado", Context.MODE_PRIVATE);
        String NombreEmpleadoLogin=preferences.getString("NombreEmpleadoGuardado", "no hay informacion");
        int StatusEmpleado=preferences.getInt("statusGuardadoEmpleado", 0);
        String MessageEmpleado=preferences.getString("messageGuardadoEmpleado", "no hay informacion");

        String fecha = preferences.getString("fecha", "no hay informacion"); //BUSCA SOLO LA FECHA

        Log.e("RFECHAAA: ", fecha);
        Log.e("MENSAJE GUARDADO: ", MessageEmpleado);
        Log.e("STATUS GUARDADO : ", String.valueOf(StatusEmpleado));

        if (fecha == "no hay informacion" && MessageEmpleado == "no hay informacion" &&  StatusEmpleado == 0) {
            Intent i = new Intent(this, EmpleadoIngresado.class);
            i.putExtra("IdIngresado", EdtIdEmpleado.getText().toString());
            startActivity(i);

        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime localDateTime = LocalDateTime.parse(fecha, dateTimeFormatter); //cambia el valor de la variable fecha string a date.
            //le da el formato solo fechha al localdatetime
            String FechaGuardada = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            Log.e("Esta es la fecha Guardada convertida", FechaGuardada);
            Log.e("Valor de la fecha CUESTIONARIO : ", String.valueOf(localDateTime)); //imprime en consola el valor de la fecha
            Log.e("ESTA ES LA FECHA ACTUAL CONVERTIDA", FechaActualConvertida);

            if (EdtIdEmpleado.getText().toString().equals("")) {
                Toast.makeText(this, "Tienes que ingresar una matricula válida",
                        Toast.LENGTH_LONG).show();

            } else if (EdtIdEmpleado.getText().toString().length() < 6) {
                Toast.makeText(this, "Tamaño de matricula inválido",
                        Toast.LENGTH_LONG).show();

            } else if (FechaActualConvertida.equals(FechaGuardada)) {
                Intent i = new Intent(this, AccesoQREmpleadoLogin.class);
                startActivity(i);
                //comparamos para validar las sesiones

            } else {
                Intent i = new Intent(this,EmpleadoIngresado.class);
                i.putExtra("IdIngresado", EdtIdEmpleado.getText().toString());
                startActivity(i);
            }

        }

    }




    private void ValidarUsuario(String Usuario){

    }


}

