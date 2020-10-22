package com.example.musicaeimagenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {



    ImageView miimagen1 ;
    ImageView miimagen2;
    ImageView miimagen3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button miboton= findViewById(R.id.id_botonmain2);
         miimagen1 = findViewById(R.id.id_imagen1);
         miimagen2 = findViewById(R.id.id_imagen2);
        miimagen3 = findViewById(R.id.id_imagen3);
        miboton.setOnClickListener(this);



    }
    public void cargarImagenes(){
        Resources res = getResources();
        String[] imagenes = res.getStringArray(R.array.url_imagenes);
        String[] videos = res.getStringArray(R.array.url_videos);

        imageLoad(imagenes[0],miimagen1);
        imageLoad(imagenes[1],miimagen2);
        imageLoad(imagenes[2],miimagen3);

    }
    public void imageLoad(String url, ImageView miimagen ){
        Picasso.get().load(url).into(miimagen);
    }

    @Override
    public void onClick(View v) {
        cargarImagenes();
    }
}