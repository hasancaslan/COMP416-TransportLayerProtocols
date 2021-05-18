import java.io.*;
import java.net.Socket;

public class CertificateServerThread extends Thread {
    private static final int BUFFER_SIZE = 4096;
    private final Socket socket;

    public CertificateServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        sendCertificateFile();
    }

    public void sendCertificateFile() {
        try {
            System.out.println("Sending certificate file...");
            InputStream caInput = new BufferedInputStream(new FileInputStream(new File("server_crt.crt")));

            byte[] bytes = new byte[BUFFER_SIZE];

            int counter;
            while ((counter = caInput.read(bytes)) > 0) {
                socket.getOutputStream().write(bytes, 0, counter);
            }

            socket.getOutputStream().write(26);
            System.out.println("Certificate file is sent.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
