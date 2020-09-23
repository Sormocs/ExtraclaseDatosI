import sockets.Client;
import sockets.OpenServer;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

/**
 * Clase Ventana que se encarga de utilizar los métodos de la clase JFrame de Java, para
 * así crear la ventana donde se encuentra el chat, los botones y todo lo que conlleva el
 * chat. Además en sus atributos crea un objeto "Cliente" y un "OpenServer", que son los
 * encargados de realizar la conexión entre los puertos a través de los sockets para enviar
 * y recibir información, ya que de esta manera se logra accesar a los métodos que contiene
 * cada una y logran realizar el proceso satisfactoriamente
 */

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

    /**
     * Método ventana, el cual se encarga de llamar al constructor y métodos de la clase padre (JFrame) y a los objetos
     * de "Client" y "OpenServer" creados en esta clase para iniciar la conexión con los sockets. Comienza con
     * el método FindPort de OpenServer para conectarse a el primer puerto que encuentre disponible, además de
     * que llama a los métodos que inician los componentes y configuran la ventana del chat. También inicia un
     * "Thread" para poder correr el programa varias veces.
     */
    public Ventana() {
        super();
        server_access.FindPort();
        configurarVentana();
        inicializarComponentes();
        Thread begin_server = new Thread(this);
        begin_server.start();
    }

    /**
     * Método "configurarVentana", se encarga de llamar a los métodos de la clase padre que se encargan
     * de asiganar los valores a los componentes de la ventana, para crear la ventana con las condiciones
     * que se le indican.
     */
    private void configurarVentana() {
        this.setTitle("Chat");
        this.setSize(410, 410);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Inicia todos los componenetes que se necesitan para la ventana, textos, cuadros de texto, el botón para
     * enviar el mensaje y el área donde se muestran los mensajes enviados y recibidos.
     */
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

    /**
     * Se encarga de detectar el evento que sucede cuando se la hce click al botón de enviar el mensaje y
     * revisar si se puede enviar el mensaje a algún puerto, si tira un error que no lo permite, sale un
     * messagebox diciéndole al usuario que introduzca todos los datos antes de intentar enviar un mensaje.
     * @param e Es el evento que recibe cuando se presiona el botón enviar en la ventana.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            acceso.setPuerto(Integer.parseInt(this.port_box.getText()));
            String message = entryBox.getText();
            tbox.append("Envía: " + message + "\n");
            acceso.setMensaje(message + "\n");
            acceso.start();
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(this, "Por favor Introduzca los datos requeridos");
        }
    }

    /**
     * Método Run que se debe implementar junto con la implementación de Runnable. Se encarga de recibir los
     * datos enviados al puerto a través del socket y de mostrar el mensaje en el "área de texto" que funciona como
     * el historial del chat.
     */
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
