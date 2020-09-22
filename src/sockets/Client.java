package sockets;
import java.net.*;
import java.io.*;

public class Client {
    private ServerSocket server;
    private Socket socket;
    private int puerto= 5000;
    private String ip = "127.0.0.1";
    private BufferedReader entrada;
    private String mensaje = "";

    public Client(){
        this.ip = ip;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setPuerto(int Puerto){
        this.puerto = Puerto;
    }

    public void start(){
        try{
            Socket clients = new Socket(this.ip,this.puerto);
            DataOutputStream salida = new DataOutputStream(clients.getOutputStream());
            salida.writeUTF(this.mensaje);
            salida.close();

        } catch(UnknownHostException e1) {
            e1.printStackTrace();

        } catch (IOException e1){
            System.out.println(e1.getMessage());
        }
    }
}


