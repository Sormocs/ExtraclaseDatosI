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
    private JLabel send;
    private JTextField entryBox;
    private JButton boton;
    private JTextArea tbox;
    private JTextField port_box;
    private JScrollPane scroll;

    public Ventana() {
        super();
        server_access.FindPort();
        configurarVentana();
        inicializarComponentes();
        Thread begin_server = new Thread(this);
        begin_server.start();
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
        send = new JLabel();
        entryBox = new JTextField();
        boton = new JButton();
        tbox = new JTextArea();
        scroll = new JScrollPane(tbox);
        port_box = new JTextField();

        texto.setText("Chat");
        texto.setBounds(20, 0, 100, 25);

        port_num.setText("Este Puerto: "+ String.valueOf(server_access.getPuerto()));
        port_num.setBounds(280,340,100,25);

        send.setText("Enviar al puerto:");
        send.setBounds(20,335,100,30);

        entryBox.setBounds(20, 300, 250, 30);
        port_box.setBounds(120,340,100,25);

        scroll.setBounds(20, 20, 350, 250);
        tbox.setEditable(false);
        tbox.setLineWrap(true);

        boton.setText("Enviar");
        boton.setBounds(280, 300, 100, 30);
        boton.addActionListener(this);

        this.add(texto);
        this.add(port_num);
        this.add(send);
        this.add(entryBox);
        this.add(port_box);
        this.add(boton);
        this.add(scroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{//if (this.entryBox.getText() != null & this.port_box.getText() != null){
            acceso.setPuerto(Integer.parseInt(this.port_box.getText()));
            String message = entryBox.getText();
            tbox.append("De ti: " + message + "\n");
            acceso.setMensaje(message + "\n");
            acceso.start();
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(this, "Por favor Introduzca los datos requeridos");
        }
    }

    @Override
    public void run() {
        try{
            ServerSocket receptor = new ServerSocket(server_access.getPuerto());
            while(true) {
                Socket entrada = receptor.accept();
                DataInputStream data_entrance = new DataInputStream(entrada.getInputStream());
                String message = data_entrance.readUTF();
                tbox.append("Recibe: " + message);
                entrada.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
