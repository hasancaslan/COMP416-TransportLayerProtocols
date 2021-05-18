import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * Copyright [2017] [Yahya Hassanzadeh-Nazarabadi]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class SSLServerThread extends Thread {
    private final String ID_NUMBER = "60453";
    private SSLSocket sslSocket;
    private BufferedReader is;
    private BufferedWriter os;

    public SSLServerThread(SSLSocket s) {
        sslSocket = s;
    }

    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

        } catch (IOException e) {
            System.out.println("Server Thread. Run. IO error in server thread");
        }
        try {
            int index = is.read();
            char reply = ID_NUMBER.charAt(index);
            os.write(reply);
            os.flush();
            System.out.println("Client " + sslSocket.getRemoteSocketAddress() + " sent : " + reply);
        } catch (IOException e) {
            String line = this.getClass().toString();
            System.out.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            String line = this.getClass().toString();
            System.out.println("Server Thread. Run.Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Closing the connection");
                if (is != null) {
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if (os != null) {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (sslSocket != null) {
                    sslSocket.close();
                    System.out.println("Socket Closed");
                }

            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}
