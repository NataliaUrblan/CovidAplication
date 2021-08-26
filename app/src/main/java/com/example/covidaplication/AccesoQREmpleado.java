package com.example.covidaplication;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.appcompat.app.AppCompatActivity;


public class AccesoQREmpleado extends AppCompatActivity {
        String mensajep;
        int statusp;
        ImageView QrImagen;
        TextView tvm, tvDictamenn;
        QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_q_r_empleado);

        statusp=getIntent().getIntExtra("Status", 0);
        mensajep=getIntent().getStringExtra("Message");
        tvm= findViewById(R.id.mensaje);
        QrImagen=findViewById(R.id.QrImage);
        tvDictamenn=findViewById(R.id.TvDictamen);
        metodop(mensajep);
        GenerarQR(mensajep, statusp);

    }
    public void metodop(String msj){
        tvm.setText("Este es el mensaje"+msj);
    }

    public void GenerarQR(String message, int status){
        //tvMensaje.append("este es el msj"+message);
        Log.i("este es el mensaje que si aparece", message);
        if (status==200) {
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
                QrImagen.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        } else {
            tvDictamenn.setText("De acuerdo a su solicitud procesada no es permitido el acceso a las instalaciones, comuniquese con su director de carrera.");
        }



    }
}