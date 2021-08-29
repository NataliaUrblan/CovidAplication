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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.jakewharton.threetenabp.AndroidThreeTen;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrarInvitado extends AppCompatActivity {
    private static final String TAG = "InvitadoIngresado";

        EditText et1;
        EditText et2;
        EditText et3;
        EditText et4;

        TextView hfecha;
        Button btnInsertar;
    String et1_Ingresado="";
    String et2_Ingresado="";
    String et3_Ingresado="";
    String et4_Ingresado="";

    String Nombre;
    String ApellidoP;
    String ApellidoM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_invitado);
        AndroidThreeTen.init(this);
        et1=(EditText) findViewById(R.id.etCorreo);
        et2=(EditText) findViewById(R.id.et2);
        et3=(EditText) findViewById(R.id.et3);
        et4=(EditText) findViewById(R.id.et4);
        CargarPreferencias();

       // SharedPreferences preferences = getSharedPreferences("SaveCorreo",Context.MODE_PRIVATE );
        // et1.setText(preferences.getString("mail", ""));

    }



    public void RegistroInvitado(View view) {
        LocalDateTime HoraAccesoInvitado= LocalDateTime.now();
        et1_Ingresado=et1.getText().toString();
        et2_Ingresado=et2.getText().toString();
        et3_Ingresado=et3.getText().toString();
        et4_Ingresado=et4.getText().toString();


            String url ="http://services.uteq.edu.mx/api/Invitados";

            // Optional Parameters to pass as POST request
            JSONObject js = new JSONObject();
            try {
                js.put("nombre" ,et1_Ingresado);
                js.put("appaterno",et2_Ingresado);
                js.put("apmaterno",et3_Ingresado);
                js.put("correo",et4_Ingresado);
                js.put("fechareg",HoraAccesoInvitado);

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

                                 Nombre= obj.getString("nombre");
                                 ApellidoP = obj.getString("appaterno");
                                 ApellidoM = obj.getString("apmaterno");
                                 int idSolicitudInvitado = obj.getInt("id_solicitud");
                                 DatosInvitado(idSolicitudInvitado, HoraAccesoInvitado);
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

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

            };
        Volley.newRequestQueue(this).add(jsonObjReq);

            // Adding request to request queue

        GuardarPreferencesCorreo(et1_Ingresado, et2_Ingresado, et4_Ingresado, HoraAccesoInvitado);

        }
        public void CargarPreferencias(){ //metodo que busca la informacion
        SharedPreferences preferences=getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        String Correo=preferences.getString("Correo", "no hay informacion"); //busca el correo si no hay nada muestra el msj de no hay informacion


        }


    public void GuardarPreferencesCorreo(String nombre, String apellido, String Correo, LocalDateTime  hora){
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //credenciales es nombre de las preferencias donde se almacenara todos los datos.
        String usuario=Correo;
        String Nombre=nombre;
        String Apellido=apellido;
        int solicitudderegistro=0;
        String fechaString=hora.toString(); //convierte hora y fecha en string para poder almacenarla en shared preferences
        SharedPreferences.Editor Objeditor = preferencias.edit();
        Objeditor.putString("Correo", usuario); // guarda correo
        Objeditor.putString("Nombre", Nombre); // guarda nombre
        Objeditor.putString("Apellido", Apellido); // guarda apellido
        Objeditor.putString("fecha",fechaString); // Guarda fecha
        Objeditor.putInt("soliRegistro",solicitudderegistro);
        Objeditor.commit(); //hace commit para guardar los datos

    }

        public void DatosInvitado( int solicitudInvitado, LocalDateTime FechaHora){
            Intent i = new Intent(this, InvitadoIngresado.class);
            i.putExtra("idSolicitudInvitado",  solicitudInvitado);
            startActivity(i);
        }

}
