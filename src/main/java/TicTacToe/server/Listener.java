package TicTacToe.server;

import java.io.*;
import java.net.Socket;
import TicTacToe.gameScreen;
import TicTacToe.Game.Game;

public class Listener implements Runnable
{
    Socket socket;
    gameScreen UI;
//    Notifier notify;
    boolean shutdown = false;

    public Listener(Socket socket, gameScreen UI) // gameOver
    {
        this.socket = socket;
        this.UI = UI;
//        this.notify = notify;
    }
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream input = socket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            System.out.println("initialize inputs");
            String message;

            while (!shutdown)
            {
                System.out.println("start while loop");
                message = reader.readLine();
                if (message == null)
                {
                    continue;
                }
                else if (message.contains("/quit"))
                {
//                    reader.close();
//                    notify.shutdown();
//                    shutdown();
                }
                else if (message.contains("/serverShutdown"))
                {
//                    UI.quitCommand(true);
//                    notify.message("/quit");
                }
                else if (message.contains("yourTurn"))
                {
                    System.out.println("Your turn");
                    UI.UIToggleOn();
                }
                else if (message.contains("notYourTurn"))
                {
                    System.out.println("Not your turn");
                    UI.UIToggleOff();
                }
                else if (message.contains("invalid"))
                {
                    System.out.println("Invalid input");
                    UI.setErrorLabel("A issue has occurred, your board has been updated. Please make a new move.");
                    try {
                        UI.update((Game)objectInput.readObject());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (message.contains("valid"))
                {
                    System.out.println("Valid input");
                    UI.setErrorLabel("");
                    try {
                        UI.update((Game)objectInput.readObject());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (message.equalsIgnoreCase("1") || message.equalsIgnoreCase("2"))
                {
                    System.out.println("new game message");
                    UI.setPlayerLabel(message);
                    System.out.println("player label set " + message);


                    try {
                        UI.update((Game)(objectInput.readObject()));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("new game message end " + message);

                }
                else if (message.contains("gameOver"))
                {
                    UI.gameOver();
                }
                else
                {
//                    UI.newMessage(message);
                }
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown()
    {
        shutdown = true;
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
