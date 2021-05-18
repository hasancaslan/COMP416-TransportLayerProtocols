import java.io.*;
import java.net.Socket;

public class CertificateConnectToServer {
    private static final int BUFFER_SIZE = 4096;
    protected String serverAddress;
    protected int serverPort;
    private Socket socket;
    private DataInputStream is;
    private PrintWriter os;

    public CertificateConnectToServer(String address, int port) {
        serverAddress = address;
        serverPort = port;
    }

    public void connect() {
        System.out.println("connect");
        try {
            socket = new Socket(serverAddress, serverPort);
            is = new DataInputStream(socket.getInputStream());
            os = new PrintWriter(socket.getOutputStream());
        } catch (EOFException e) {
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveCertificateFile() {
        int count;
        byte[] buffer = new byte[BUFFER_SIZE];

        try (FileOutputStream fos = new FileOutputStream("server_crt.crt")) {
            while ((count = is.read(buffer)) > 0) {
                System.out.println(count);
                if (buffer[count - 1] == 26) {
                    fos.write(buffer, 0, count - 1);
                    break;
                } else {
                    fos.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the socket and closes the buffers
     */
    public void disconnect() {
        try {
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
