import sockets.Client;
import sockets.OpenServer;

import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class Ventana extends JFrame implements ActionListener, Runnable {

    private Client acceso = new Client();
    private OpenServer server_access = new OpenServer();
    private JLabel texto;
    private JLabel port_num;
    private JTextField entryBox;
    private JButton boton;
    private JTextArea tbox;
    private JTextField port_box;
    private JScrollPane scroll;
    private String mensaje_entrante;

    public Ventana() {
        super();
        server_access.FindPort();
        configurarVentana();
        inicializarComponentes();
        this.mensaje_entrante = "";
        Thread begin_server = new Thread(this);
        begin_server.start();
    }

    public void setMensaje_entrante(String mensaje_entrante) {
        this.mensaje_entrante = mensaje_entrante;
        tbox.append(this.mensaje_entrante);
    }

    private void configurarVentana() {
        this.setTitle("Chat");
        this.setSize(410, 410);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {

        texto = new JLabel();
        port_num = new JLabel();
        entryBox = new JTextField();
        boton = new JButton();
        tbox = new JTextArea();
        scroll = new JScrollPane(tbox);
        port_box = new JTextField();

        texto.setText("Chat");
        texto.setBounds(20, 0, 100, 25);

        port_num.setText(String.valueOf(server_access.getPuerto()));
        port_num.setBounds(130,340,100,25);

        entryBox.setBounds(20, 300, 250, 30);
        port_box.setBounds(20,340,100,25);

        //tbox.setBounds(20, 20, 350, 250);
        scroll.setBounds(20, 20, 350, 250);
        tbox.setEditable(false);
        tbox.setLineWrap(true);

        boton.setText("Enviar");
        boton.setBounds(280, 300, 100, 30);
        boton.addActionListener(this);

        this.add(texto);
        this.add(port_num);
        this.add(entryBox);
        this.add(port_box);
        this.add(boton);
        this.add(scroll);
    }
    public String getText(){
        return entryBox.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        acceso.setPuerto(Integer.parseInt(this.port_box.getText()));
        String message = entryBox.getText();
        tbox.append("De ti: "+message + "\n");
        acceso.setMensaje(message + "\n");
        acceso.start();
    }

    @Override
    public void run() {
        try{
            ServerSocket receptor = new ServerSocket(server_access.getPuerto());
            while(true) {
                Socket entrada = receptor.accept();
                DataInputStream data_entrance = new DataInputStream(entrada.getInputStream());
                String message = data_entrance.readUTF();
                tbox.append("\nRecibe: " + message);
                entrada.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
