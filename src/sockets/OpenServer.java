package sockets;
import javax.print.attribute.standard.DateTimeAtCompleted;
import java.net.*;
import java.io.*;

public class OpenServer{
    private String ip;
    private int puerto;
    private boolean open;


    public OpenServer(){
        this.ip = "127.0.0.1";
        this.puerto = 45000;
        this.open = false;

    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void FindPort(){
        while(this.open == false){
            if (this.getPuerto() < 65000) {
                try {
                    // Successful connection means the port is taken
                    new ServerSocket(this.getPuerto()).close();
                    this.setPuerto(this.getPuerto());
                    this.open = true;
                } catch (IOException e) {
                    // Could not connect
                    this.setPuerto(this.getPuerto() + 1);
                }
            } else{
                break;
            }
        }
        System.out.println(this.getPuerto());
    }


}
