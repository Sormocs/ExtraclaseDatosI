package sockets;
import java.net.*;
import java.io.*;

/**
 *Clase "Client" que permite de hacer al programa funcionar como cliente, es decir, de permitir que pueda enviar
 * información al puerto que introduce el usuario en la ventana, por lo tanto esta clase se llama siempre que se
 * presiona el botón enviar.
 */
public class Client {
    private ServerSocket server;
    private Socket socket;
    private int puerto= 5000;
    private String ip = "127.0.0.1";
    private BufferedReader entrada;
    private String mensaje = "";

    /**
     * Constructor de cliente, aunque no recibe valores, estos van a cambiar ya que posee los iniciales que se
     * asignan directamente en la clase. La ip es el único que siempre tiene el mismo valor debido a que siempre
     * se envía la información de manera local.
     */
    public Client(){
        this.ip = ip;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    /**
     * Recibe un String que representa el mensaje que introdujo el usuario.
     * @param mensaje Mensaje que será enviado al puerto introducido por el usuario.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Recibe un int que representa el puerto.
     * @param Puerto El puerto al cuál se debe enviar el mensaje.
     */
    public void setPuerto(int Puerto){
        this.puerto = Puerto;
    }

    /**
     * Se encarga de iniciar el socket por el cual se envía el mensaje. Recoge la IP, el puerto y el mensaje
     * para enviarlos por el socket al puerto deseado. Este método es accedido por el botón "enviar" en la
     * clase Ventana.
     */
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


