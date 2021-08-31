


package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class LoginInvitado extends AppCompatActivity {
    EditText etCorreo;
    String valorCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_invitado);

        AndroidThreeTen.init(this); //se utiliza para la fecha
        etCorreo=findViewById(R.id.etCorreo);

    }

   public void BtnRegistrarInvitado(View view) {
        Intent i = new Intent(this,RegistrarInvitado.class);
        startActivity(i);
    }



    public void BtnIniciarSesionInvitado(View v) { //Valida que un usuario invitado este registrado con su correo
        valorCorreo = etCorreo.getText().toString();

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Correo = preferences.getString("Correo", "no hay informacion");
        if (etCorreo.getText().toString().equals("")) {
            Toast.makeText(this, "Tienes que ingresar un correo",
                    Toast.LENGTH_LONG).show();

        } else  if(Correo.equals("no hay informacion")) {
            Toast.makeText(this, "Correo no registrado",
                    Toast.LENGTH_LONG).show();

        }
      else {
            BuscaUsuario(valorCorreo);
    }
    }
    public void validarCorreoExistente(){

    }

    public void BuscaUsuario(String valorCorreo){
        LocalDateTime HoraFecha = LocalDateTime.now();
        String FechaActualConvertida = HoraFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        //conmienza codigo para consultar los valores guardados en el sharedpreferences
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Correo = preferences.getString("Correo", "no hay informacion");
        String Nombre = preferences.getString("Nombre", "no hay informacion");
        String Apellido = preferences.getString("Apellido", "no hay informacion");
        String fecha = preferences.getString("fecha", "no hay informacion"); //BUSCA SOLO LA FECHA
        String messageGuardado = preferences.getString("message", "no hay informacion");
        int statusGuardado = preferences.getInt("status", 0);
        Log.e("MENSAJE GUARDADO: ", messageGuardado);
        Log.e("STATUS GUARDADO : ", String.valueOf(statusGuardado));


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(fecha, dateTimeFormatter); //cambia el valor de la variable fecha string a date.

        //le da el formato solo fechha al localdatetime
        String FechaGuardada = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Log.e("Esta es la fecha Guardada convertida", FechaGuardada);
        Log.e("Valor de la fecha CUESTIONARIO : ", String.valueOf(localDateTime)); //imprime en consola el valor de la fecha
        Log.e("ESTA ES LA FECHA ACTUAL CONVERTIDA", String.valueOf(FechaActualConvertida));


        if (valorCorreo.equalsIgnoreCase(Correo)) {
            if (FechaActualConvertida.equals(FechaGuardada)) {
                Intent i = new Intent(this, AccesoQRInvitadoLogin.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, SesionInvitado.class);
                i.putExtra("RegistroNombreInvitado", Nombre);
                i.putExtra("RegistroApellidoInvitado", Apellido);
                i.putExtra("Regist roCorreo", Correo);
                startActivity(i);
            }
        } else {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show();
        }
    }
}
