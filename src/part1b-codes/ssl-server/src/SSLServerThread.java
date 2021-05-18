import javax.net.ssl.SSLSocket;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private static int COUNT = 0;
    private final String KUSIS_ID = "60453";
    private final String KUSIS_USERNAME = "haslan16";
    private final String RESPONSE = KUSIS_ID + KUSIS_USERNAME + new SimpleDateFormat("ddMMyyyy").format(new Date());
    private SSLSocket sslSocket;
    private String line = new String();
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
            line = is.readLine();
            System.out.println(line);
            System.out.println(line);
            if (line.equals("1")) {
                os.write(RESPONSE);

                System.out.println("Client " + sslSocket.getRemoteSocketAddress() + " sent : " + line);
                System.out.println("Response is : " + RESPONSE + " to client : " + sslSocket.getRemoteSocketAddress());

            } else if (line.equals("2")) {
                char[] response = RESPONSE.toCharArray();
                System.out.println(response.length);
                if (SSLServerThread.COUNT < response.length) {
                    os.write(response[SSLServerThread.COUNT]);
                    SSLServerThread.COUNT += 1;
                } else {
                    SSLServerThread.COUNT = 0;
                }

                System.out.println("Client " + sslSocket.getRemoteSocketAddress() + " sent : " + line);
                System.out.println("Response is : " + RESPONSE + " to client : " + sslSocket.getRemoteSocketAddress());
            }


            os.flush();
        } catch (IOException e) {
            line = this.getClass().toString(); //reused String line for getting thread name
            System.out.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getClass().toString(); //reused String line for getting thread name
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
