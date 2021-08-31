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

public class AccesoQRInvitado extends AppCompatActivity {
        TextView Nombre, Apellido;
        String mensajeInvitado;
        int statusInvitado;
        ImageView QrImagenInvitado;
        TextView tvInvitado, tvDictamenInvitado;
        QRGEncoder qrgEncoder;
        String ApellidoInvitado, NombreInvitado;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_acceso_q_r_invitado);

            SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            String NombreI=preferences.getString("Nombre", "no hay informacion");
            String ApellidoI=preferences.getString("Apellido", "no hay informacion");

            mensajeInvitado=getIntent().getStringExtra("Message");
            statusInvitado=getIntent().getIntExtra( "Status",0);


            Nombre=findViewById(R.id.NombreUsuarioInvitado);
            Apellido=findViewById(R.id.ApellidoUsuarioInvitado);

            QrImagenInvitado=findViewById(R.id.QRInvitado);
            tvDictamenInvitado=findViewById(R.id.DictamenInvitado);



            GenerarQR(mensajeInvitado, statusInvitado);
            Nombre.setText(NombreI);
            Apellido.setText(ApellidoI);
        }




        public void GenerarQR(String message, int status){

            Log.i("este es el mensaje que si aparece", message);
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
                        QrImagenInvitado.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                } else {
                    tvDictamenInvitado.setText("De acuerdo a su solicitud procesada no es permitido el acceso a las instalaciones, comuniquese con su director de carrera.");
                }



        }
    public void CerrarSesionInvitado(View view) {
        Intent i = new Intent(this,Inicio.class);
        startActivity(i);
    }
}