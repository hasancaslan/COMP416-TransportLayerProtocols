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
public class Main {
    public final static String TLS_SERVER_ADDRESS = "localhost";
    public final static int TLS_SERVER_PORT = 60453;
    public final static int TLS_CERTIFICATE_PORT = 4444;
    public final static int ID_NUMBER_LENGTH = 5;

    public static void main(String[] args) throws Exception {
        CertificateConnectToServer certificateConnectToServer = new CertificateConnectToServer(TLS_SERVER_ADDRESS, TLS_CERTIFICATE_PORT);

        certificateConnectToServer.connect();
        certificateConnectToServer.receiveCertificateFile();
        certificateConnectToServer.disconnect();

        SSLConnectToServer sslConnectToServer = new SSLConnectToServer(TLS_SERVER_ADDRESS, TLS_SERVER_PORT);

        /*
        Sends a message over SSL socket to the server and prints out the received message from the server
         */

        StringBuilder sb = new StringBuilder();
        
        String chars;

        for (int i = 0; i < ID_NUMBER_LENGTH; i++) {
            sslConnectToServer.Connect();
            chars = sslConnectToServer.SendForAnswer(i);
            sslConnectToServer.Disconnect();

            if (chars == null || chars.equals("")) {
                break;
            }

            System.out.println(chars);
            sb.append(chars);
        }

        System.out.println("Final merged number : " + sb.toString());
    }
}
