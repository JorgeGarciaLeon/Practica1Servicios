import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        boolean continueSending = true;
        boolean sendName = false;
        final String DESPEDIDA = "Good Bye";
        String messageRecived = "";

        Socket socket = new Socket("127.0.0.1", 8080);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        String responseServer = "";

        while(continueSending) {
            if(!sendName){
                System.out.println("Escribe el nombre de usuario con: usuario ");
                oos.writeObject(writeMessage());
                sendName = true;
            }else {
                messageRecived = (String) ois.readObject();
                if (messageRecived.equals(DESPEDIDA)) {
                    System.out.println("Entra en despedida");
                    if (oos != null) oos.close();
                    if (ois != null) ois.close();
                    if (socket != null) socket.close();
                    continueSending = false;
                    System.out.println("Conexión cerrada");
                }else {
                    System.out.println(messageRecived);
                    System.out.println("Escribe un mensaje con message:");
                    oos.writeObject(writeMessage());
                }


            }
        }
    }

    private static String writeMessage() {
        Scanner intro = new Scanner(System.in);
        String message = intro.nextLine();
        return message;
    }

}