package com.cotton;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class CameraActivity extends Activity {
    //Boton en encender/apagar
    private ImageView boton;
    //Layout donde se verá la imagen de la camara
    private SurfaceView surfaceView;
    //Es el modulo que comunica la camara con el layout
    private SurfaceHolder surfaceHolder;
    //Camara librería de Hardware
    private Camera camara;
    //Simplemente para controlar si esta on/off
    private boolean encendida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        //relacionamos con el xml
        boton = (ImageView)findViewById(R.id.photo_button);
        surfaceView = (SurfaceView) findViewById(R.id.view_camera);
        //Le indicamos cual será el destino (surfaceview)
        surfaceHolder = surfaceView.getHolder();
        //la pp se iniciara en off
        encendida = false;
        try{
            startCamara();
        }catch(Exception e){

        }

        //al presionar el boton
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Lamamos a la funcion de la camara que hemos creado
                    startCamara();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    private void startCamara() throws IOException {
        //Si la camara esta apagada
        if(!encendida){
            //pillamos el control del hardware de la camara
            camara = Camera.open();
            //Enlazamos el hardware con el surfaceHolder
            camara.setPreviewDisplay(surfaceHolder);
            //Encendemos todo
            camara.startPreview();
            encendida = true;
        }else{
            //paramos la camara
            camara.stopPreview();
            //liberamos el hardware
            camara.release();
            //Sabemos que ya esta en off
            encendida = false;
        }
    }

}