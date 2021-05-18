import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CertificateServer extends Thread {
    private ServerSocket serverSocket;

    public CertificateServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Certificate server is up and running on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            listenAndAccept();
        }
    }

    private void listenAndAccept() {
        Socket s;
        try {
            s = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            CertificateServerThread ct = new CertificateServerThread(s);
            ct.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Certificate Server Class.Connection establishment error inside listen and accept function");
        }
    }
}
