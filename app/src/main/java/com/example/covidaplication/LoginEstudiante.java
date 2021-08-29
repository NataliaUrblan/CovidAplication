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

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


public class LoginEstudiante extends AppCompatActivity {
    TextView tv1;
    EditText EdtMatricula ;
    Button BtnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estudiante);
        EdtMatricula=findViewById(R.id.etCorreo);
        AndroidThreeTen.init(this); //se utiliza para la fecha
    }

    public void BtnIngresar(View view) {
        SharedPreferences preferences=getSharedPreferences("DatosGuardadosEstudiante", Context.MODE_PRIVATE);
        String matriculaGuardada=preferences.getString("matricula","No se encuentra");
        String matriculaIngresada= EdtMatricula.getText().toString();
        int solicitud = getIntent().getIntExtra("solicitud", 0);
        if(solicitud<0  ){
            Intent i = new Intent(this, EstudianteIngresado.class);
            i.putExtra("MatriculaIngresada", matriculaIngresada);
            Log.e("esta es la matricula", matriculaIngresada);
            startActivity(i);
        }else {
        acciones();
        }

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