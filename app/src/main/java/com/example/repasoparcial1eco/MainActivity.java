package com.example.repasoparcial1eco;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener  {

    private Button bIzquierda, bDerecha, bArriba, bAbajo;
    BufferedWriter writer;
    BufferedReader reader;
    int x,y;
    int posX,posY;
    boolean buttonPressed;
    TCPSingleton tcp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bDerecha= findViewById(R.id.bDerecha);
        bIzquierda= findViewById(R.id.bIzquierda);
        bArriba= findViewById(R.id.bArriba);
        bAbajo= findViewById(R.id.bAbajo);
        x=250;
        y=350;

        tcp= TCPSingleton.getInstance();
        //initUser();


        bIzquierda.setOnTouchListener(this);
        bDerecha.setOnTouchListener(this);
        bArriba.setOnTouchListener(this);
        bAbajo.setOnTouchListener(this);

    }


    /*public void initUser(){

        new Thread(
                ()->{

                    try {
                        Log.e(">>","Esperando conexion");
                        Socket socket= new Socket("192.168.0.5",7000);
                        Log.e(">>","Conectado");


                        //Input Output
                        OutputStream os= socket.getOutputStream();
                        InputStream is=socket.getInputStream();


                        //Writer reader
                        writer=new BufferedWriter(new OutputStreamWriter(os));
                        reader= new BufferedReader(new InputStreamReader(is));


                        //Recepción mensaje
                        while(true) {
                            String line = reader.readLine();
                            System.out.println(line);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        ).start();
    }*/



    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                buttonPressed=true;
                break;

            case MotionEvent.ACTION_UP:
                buttonPressed=false;
                break;
        }

        if( buttonPressed==true){

            new Thread(
                    ()->{
                        while (buttonPressed){

                            switch (view.getId()){
                                case R.id.bDerecha:
                                    x= x+5;
                                    break;

                                case R.id.bIzquierda:

                                    x= x-5;
                                    break;

                                case R.id.bArriba:
                                    y=y-5;
                                    break;
                                case R.id.bAbajo:
                                    y=y+5;
                                    break;
                            }

                            Gson gson= new Gson();
                            Bola bola= new Bola(x,y);
                            String json=gson.toJson(bola);

                            //Envio el json
                            //enviar(json);
                            tcp.enviar(json);

                            Log.e(">>>", json);

                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).start();

        }
        return false;

    }



   /* public void enviar(String mensaje){

        new Thread(
                ()->{
                    try {
                        writer.write(mensaje + "\n");
                        writer.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

        ).start();

    }*/
}