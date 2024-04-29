package TicTacToe.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Notifier implements Runnable {
    Socket socket;
    String message;
    public Notifier(Socket socket, String message)
    {
        this.socket = socket;
        this.message = message;
    }
    @Override
    public void run() {
        if (message == null)
        {
            return;
        }
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            if (message.contains("move"))
            {
                System.out.println(message.charAt(message.length()-1) + " -- move");
                out.println(message.charAt(message.length()-1));
            }
            else if (message.contains("mode"))
            {
                System.out.println(message.charAt(message.length()-1));

                out.println(message.charAt(message.length()-1) + " -- mode");
            }
                else
            {}



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
