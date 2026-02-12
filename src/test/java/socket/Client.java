package socket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;

public class Client extends WebSocketClient {
    private final SocketContext context;
    private LocalTime openedTime;

    public Client(SocketContext context) throws URISyntaxException {
        super(new URI(context.getURI()));
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        openedTime = LocalTime.now();
        System.out.println("Opened connection: " + context.getURI());
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received new message: " + message);
        context.getMessageList().add(message);
        if(message.equals(context.getExpectedMessage())) {
            closeConnection(1000, "Received expected message");
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Close socket with code: " + code + ", reason is: " + reason);
        context.setStatusCode(code);
    }

    @Override
    public void onError(Exception e) {

    }

    public int getAliveTime() {
        LocalTime closedTime = LocalTime.now();
        int diffSecond = openedTime.getSecond() - closedTime.getSecond();
        context.setTimeTaken(diffSecond);
        return diffSecond;
    }
}
