package com.example.covidaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AccesoQREstudiante extends AppCompatActivity {

    String mensajeEstudiante;
    int statusEstudiante;
    String NombreE, ApellidoE;
    ImageView QrImagenEstudiante;
    TextView tvEstudianteNombre,tvEstudianteApellido, tvDictamenEstudiante;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_q_r_estudiante);
        NombreE=getIntent().getStringExtra("NomEstudiante");
        ApellidoE=getIntent().getStringExtra("ApeEstudiante");
        int statusEstudiante=getIntent().getIntExtra("Status", 0);
        String mensajeEstudiante=getIntent().getStringExtra("Message");
        tvEstudianteNombre = findViewById(R.id.NombreEstudiante);
        tvEstudianteApellido= findViewById(R.id.ApellidoEstudiante);
        tvEstudianteNombre.setText(NombreE);
        tvEstudianteApellido.setText(ApellidoE);
        QrImagenEstudiante = findViewById(R.id.ImageQREstudiante);
        tvDictamenEstudiante = findViewById(R.id.DictamenEstudiante);
        GenerarQR(mensajeEstudiante, statusEstudiante);
    }
    public void GenerarQR(String message, int status){
        //Log.i("este es el mensaje que si aparece", message);
        if (status == 200) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            // initializing a variable for default display.
            Display display = manager.getDefaultDisplay();
            // creating a variable for point which
            // is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);
            // getting width and
            // height of a point
            int width = point.x;
            int height = point.y;
            Bitmap bitmap;
            // generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;
            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(message, null, QRGContents.Type.TEXT, dimen);
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                QrImagenEstudiante.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        } else {
            tvDictamenEstudiante.setText("De acuerdo a su solicitud procesada no es permitido el acceso a las instalaciones, comuniquese con su director de carrera.");
        }
    }

    public void CerrarSesionEstudiante(View view) {
        Intent i = new Intent(this,Inicio.class);
        startActivity(i);
    }
}