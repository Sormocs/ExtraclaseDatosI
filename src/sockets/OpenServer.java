package sockets;
import java.net.*;
import java.io.*;

/**
 *Clase que permite al programa mantenerse "a la escucha", conecta mediante un ServerSocket a un puerto y
 * se mantiene a la espera qde que le entre la información. En sus métodos puede asiganar el puerto,
 * obtenerlo y lo más importante, encontrar el primer puerto libre que pueda.
 */
public class OpenServer{
    private String ip;
    private int puerto;
    private boolean open;


    /**
     * Método que define la IP, el primer puerto al que se debe intentar crear el ServerSocket y un booleano para
     * cambiar su valor una vez el programa ya pueda recibir información.
     */
    public OpenServer(){
        this.ip = "127.0.0.1";
        this.puerto = 5000;
        this.open = false;

    }

    /**
     * @return Retorna el puerto al cuál logró establecer una conexión.
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * @param puerto Definne el puerto al cuál se conecta el ServerSocket.
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Ciclo que se encarga de intentar conectar con cada puerto empezando por el 5000, que es el que viene por
     * defecto en la clase propiamente. Este método es accedido desde el constructor de la clase Ventana, ya que
     * se necesita conocer el puerto al cual se conecta cada una antes de iniciar los demás componentes.
     */
    public void FindPort(){
        while(this.open == false){
            if (this.getPuerto() < 65000) {
                try {

                    new ServerSocket(this.getPuerto()).close();
                    this.setPuerto(this.getPuerto());
                    this.open = true;

                } catch (IOException e) {

                    this.setPuerto(this.getPuerto() + 1);
                }
            } else{
                break;
            }
        }
    }


}
