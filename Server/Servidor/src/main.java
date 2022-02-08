import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



public class main {

    static storageMessage message = new storageMessage();
    private static String messageToWrite = "";

    public static void main(String[] args) throws IOException {
        Socket socket;
        ServerSocket server = new ServerSocket(8080);

        System.out.println("Server Arrancado y escuchando");
        while(true){
            socket = server.accept();
            Hilo worker = new Hilo(socket);
            worker.start();
        }
    }

    static class Hilo extends Thread {
        private Socket socket = null;
        private Date time;
        private ObjectInputStream ois = null;
        private ObjectOutputStream oos = null;
        private String name;
        private String sayGoodBye = "Good Bye";
        private String errorMessage = "Error debes escribir message:";
        private String messageRecieved = "";
        private int doubleDot;
        private String value;

        public Hilo(Socket socketEntrada){
            this.socket = socketEntrada;
        }

        public void run(){
            System.out.println("Conexion recibida desde " + socket.getInetAddress());

            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                while(true) {
                    time = new Date();
                    messageRecieved = (String) ois.readObject();

                    if(messageRecieved.equals("bye")){
                        value = "bye";
                    }else{
                        doubleDot = messageRecieved.indexOf(':');
                        value = messageRecieved.substring(0,doubleDot+1);
                    }

                    switch (value){
                        case "bye":
                            oos.writeObject(sayGoodBye);
                            System.out.println("Conexion close");
                            break;
                        case "message:":
                            messageToWrite = name + " " + (time.toString()).substring(11,19) + " " + messageRecieved.substring(8);
                            message.setMensajesClientes(messageToWrite);
                            oos.writeObject(message.getAllMensajesClientes());
                            messageToWrite = "";
                            break;
                        case "name:":
                            name = messageRecieved.substring(5);
                            oos.writeObject(message.getAllMensajesClientes());
                            break;
                        default:
                            oos.writeObject(errorMessage);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
