package com.example.repasoparcial1eco;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPSingleton extends Thread {

    private static TCPSingleton unicaInstancia;


    private BufferedWriter writer;
    private BufferedReader reader;

    private TCPSingleton(){

    }


    public static TCPSingleton getInstance(){

    if(unicaInstancia==null){
    unicaInstancia= new TCPSingleton();
    unicaInstancia.start();
    }
        return unicaInstancia;
    }



    public void run(){

        try {
            Log.e(">>","Esperando conexion");
            Socket socket= new Socket("192.168.0.5",8000);
            Log.e(">>","Conectado J2");


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


    //enviar
    public void enviar(String mensaje){

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

    }



}
