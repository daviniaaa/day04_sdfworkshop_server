package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        // 2 arguments - 1 for file, 1 for the port the server will start on
        // e.g. java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
        String fileName = args[0];
        String port = args[1];

        // check whether file exists before server begins processing
        File cookieFile = new File(fileName);
        if (!cookieFile.exists()) {
            System.out.println("The file '" + fileName + "' could not be found.");
            System.exit(0);
        }

        // slide 8 - establish server connection
        Integer portNum = Integer.parseInt(port);
        ServerSocket server = new ServerSocket(portNum);
        Socket socket = server.accept();

        // store data sent over from client, e.g. get-cookie
        String msgReceived = "";

        // // to check
        // Cookie cookie = new Cookie();
        // cookie.readCookieFile(fileName);
        // String test1 = cookie.getRandomCookie();
        // System.out.println(test1);
        // String test2 = cookie.getRandomCookie();
        // System.out.println(test2);


        // slide 9 - allo server to read and write over the communication channel
        try(InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            
            try(OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos); 

                // write logic to receive and send
                while (!msgReceived.equals("quit")) {
                    // slide 9 - receive msg
                    msgReceived = dis.readUTF();

                    if (msgReceived.equals("get-cookie")) {
                        // instantiate cookie.java to get a random cookie
                        Cookie cookie = new Cookie();
                        cookie.readCookieFile(fileName);
                        
                        // send the random cookie out using DataOutputStream (dos.writeUTF(...), dos.flush())
                        dos.writeUTF(cookie.getRandomCookie());
                        dos.flush();
                    } else { // returns empty line in case other inputs are received, otherwise the program will crash
                        dos.writeUTF("");
                        dos.flush();
                    }
                    
                }

                // closes all output streams in reverse order
                dos.close();
                bos.close();
                os.close();
            }

            // closes all input streams in reverse order
            dis.close();
            bis.close();
            is.close();
            
        } catch (EOFException e) {
            e.printStackTrace();
            socket.close(); // slide 10 - close the connection
            
        }

        server.close();
        
    }
}
