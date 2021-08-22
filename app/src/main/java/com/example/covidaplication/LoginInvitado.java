package com.example.covidaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginInvitado extends AppCompatActivity {
    private static final String TAG = "InvitadoIngresado";

        EditText et1;
        EditText et2;
        EditText et3;
        EditText et4;
        EditText et5;
        Button btnInsertar;
    String et1_Ingresado="";
    String et2_Ingresado="";
    String et3_Ingresado="";
    String et4_Ingresado="";
    String et5_Ingresado="";
    String Nombre;
    String ApellidoP;
    String ApellidoM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_invitado);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);

    }


    public void RegistroInvitado(View view) {

        et1_Ingresado=et1.getText().toString();
        et2_Ingresado=et2.getText().toString();
        et3_Ingresado=et3.getText().toString();
        et4_Ingresado=et4.getText().toString();
        et5_Ingresado=et5.getText().toString();

            String url ="http://services.uteq.edu.mx/api/Invitados";

            // Optional Parameters to pass as POST request
            JSONObject js = new JSONObject();
            try {
                js.put("nombre" ,et1_Ingresado);
                js.put("appaterno",et2_Ingresado);
                js.put("apmaterno",et3_Ingresado);
                js.put("correo",et4_Ingresado);
                js.put("fechareg",et5_Ingresado);

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

                                 DatosInvitado(Nombre, ApellidoM, ApellidoP);
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



        }
        public void DatosInvitado (String Nom, String Ap1, String Ap2){

            Intent i = new Intent(this, InvitadoIngresado.class);
            i.putExtra("NombreInvitado", Nom);
            i.putExtra("ApellidoPInvitado", Ap1 );
            i.putExtra("ApellidoMInvitado", Ap2);
            startActivity(i);


        }
}
