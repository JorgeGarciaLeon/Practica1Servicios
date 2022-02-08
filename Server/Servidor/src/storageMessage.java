import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class storageMessage {

    public storageMessage() {
    }

    private static List<Object> MessagesClients = Collections.synchronizedList(new ArrayList<>());
    private static String messageToSend;

    public static synchronized String getAllMensajesClientes() throws InterruptedException {
        messageToSend = "";
        for (int i = 0; i < MessagesClients.size(); i++) {
            messageToSend += MessagesClients.get(i) + "\n";
        }
        return messageToSend;
    }
    public static synchronized void setMensajesClientes(String messageToWrite) throws InterruptedException {
        MessagesClients.add(messageToWrite);
    }

}
