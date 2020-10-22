package com.example.musicaeimagenes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static ImageButton miimagen;
    private Timer tiempo;
    private TimerTask miTareaTiempo;
    private int contador = 0;
    private String url;
    private String urlvideo;
    private int REQUEST_WRITE_STORAGE =12;
    private Button permisos;
    private Button mostrar;
    private Button descargar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miimagen = (ImageButton) findViewById(R.id.id_imagen);
        permisos = (Button) findViewById(R.id.id_permisos);
        mostrar = (Button) findViewById(R.id.id_mostrar);
        descargar = (Button) findViewById(R.id.id_descargar);




        miimagen.setOnClickListener(this);
        permisos.setOnClickListener(this);
        mostrar.setOnClickListener(this);
        descargar.setOnClickListener(this);





    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_imagen:
                //Preguntar por qué no se pone this en Intent.ACTION_VIEW
                Intent mienlace = new Intent(Intent.ACTION_VIEW, Uri.parse(urlvideo));
                startActivity(mienlace);
                break;
            case R.id.id_mostrar:
                cronometro();
                break;
            case R.id.id_permisos:
                comprobarpermisos(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                comprobarpermisos(Manifest.permission.READ_EXTERNAL_STORAGE);
                comprobarpermisos(Manifest.permission.INTERNET);


                break;
        }
    }


    public void imageLoad(String url, ImageButton botonimagen ){
        Picasso.get().load(url).into(botonimagen);
    }


    public void saveImage(String image){
        Picasso.get().load(image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
                    if(!directory.exists()){
                        directory.mkdir();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream("pruebas.jpg");
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_LONG);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }


    public void cronometro(){
        Resources res = getResources();
        final String[] imagenes = res.getStringArray(R.array.url_imagenes);
        final String[] videos = res.getStringArray(R.array.url_videos);
        final int longitud = imagenes.length;
        tiempo=new Timer();
        miTareaTiempo = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        url = imagenes[contador];
                        imageLoad(url,miimagen);
                        urlvideo=videos[contador];

                        contador++;
                        if(contador>(longitud-1)){
                            contador=0;
                        }
                    }
                });
            }
        };
        tiempo.schedule(miTareaTiempo,1,5000);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void comprobarpermisos (String  permiso){
        if (ContextCompat.checkSelfPermission (this,
                permiso)== PackageManager.PERMISSION_GRANTED)
        {

          Toast.makeText(this,"El permiso de "+permiso+"ya ha sido concedido",Toast.LENGTH_LONG).show(); }

        else{
            if(shouldShowRequestPermissionRationale(permiso)){
                Toast.makeText(this,"Se necesita permisos",Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{permiso}, REQUEST_WRITE_STORAGE);
        }
    }
}