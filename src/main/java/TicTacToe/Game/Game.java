package TicTacToe.Game;
import java.io.Serializable;


public class Game implements Serializable {
    long gameID;
    int[] buttons = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    int player1WinCounter = 0;
    int player2WinCounter = 0;
    int draws = 0;
    int whoGetsFirstMove = 1;
    int[] winner = {0, 0, 0};
    int currentPlayer = 1;
    int xGoesTo = 1;
    boolean winTurnErrorHandled = false;


    public Game(long ID) {
        gameID = ID;
        player1WinCounter = 0;
        player2WinCounter = 0;
        draws = 0;
        whoGetsFirstMove = 1;
        winner[0] = 0;
        winner[1] = 0;
        winner[2] = 0;
        currentPlayer = 1;
        xGoesTo = 1;

    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getDraws() {
        return draws;
    }

    public int getPlayer1WinCounter() {
        return player1WinCounter;
    }

    public int getPlayer2WinCounter() {
        return player2WinCounter;
    }

    public int getxGoesTo() {
        return xGoesTo;
    }

    public int getButton(int temp) {
        return buttons[temp];
    }

    public long getGameID() {
        return gameID;
    }

    public int[] getWinner() {
        return winner;
    }

    public void setWhoGetsFirstMove(int whoGetsFirstMove) {
        this.whoGetsFirstMove = whoGetsFirstMove;
    }

    public int whoGetsNextMove()
    {
        return currentPlayer;
    }

    public void resetboard()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = 0;
        }
        for (int i = 0; i < winner.length; i++)
        {
            winner[i] = 0;
        }
        swapFirstMoveMaker();
        if (xGoesTo == 1)
        {
            xGoesTo = 2;
        }
        else
        {
            xGoesTo = 1;
        }
    }

    boolean buttonPressed (int button, int player)
    {
        if (buttons[button] == 0 && player == whoGetsNextMove())
        {
            buttons[button] = player; // buttons -- 0 == empty, 1 == player1, 2 == player2
            if (currentPlayer == 1)
            {
                currentPlayer = 2;
            }
            else
            {
                currentPlayer = 1;
            }
            return true;
        }
        else
        {
            return false; // not valid move, need to refresh board on client side
        }
    }
    void makeEqual(Game game)
    {
        this.buttons = game.buttons;
        this.player1WinCounter = game.player1WinCounter;
        this.player2WinCounter = game.player2WinCounter;
    }
    void updateWinCounter(int player)
    {

        if(player == 3)
        {
            return;
        }
        else if (player == 1)
        {
            player1WinCounter++;
        }
        else if (player == 2)
        {
            player2WinCounter++;
        }
        else
        {
            draws++;
        }

    }
    public int numberOfGames()
    {
        return draws + player1WinCounter + player2WinCounter;
    }

    public int getWhoGetsFirstMove() {
        return whoGetsFirstMove;
    }

    public boolean isGameOver()
    {
        for (int i = 1; i < 3; i++) {
            // check rows
            for (int j = 0; j < 3; j++) {
                if (buttons[j * 3] == i && buttons[j * 3 + 1] == i && buttons[j * 3 + 2] == i) {
                    updateWinCounter(i);
                    winner[0] = i;
                    winner[1] = 0;
                    winner[2] = j;
                    return true;
                }
            }
            for (int j = 0; j < 3; j++)
            {
                if (buttons[j] == i && buttons[j + 3] == i && buttons[j + 6] == i)
                {
                    updateWinCounter(i);
                    winner[0] = i;
                    winner[1] = 1;
                    winner[2] = j;
                    return true;
                }
            }
            if (buttons[0] == i && buttons[4] == i && buttons[8] == i)
            {
                updateWinCounter(i);
                winner[0] = i;
                winner[1] = 2;
                winner[2] = 0;
                return true;
            }
            if (buttons[2] == i && buttons[4] == i && buttons[6] == i)
            {
                updateWinCounter(i);
                winner[0] = i;
                winner[1] = 2;
                winner[2] = 1;
                return true;
            }
        }
        for (int i = 0; i < 9; i++)
        {
            if (buttons[i] == 0)
            {
                updateWinCounter(3);
                winner[0] = 0;
                winner[1] = 0;
                winner[2] = 0;
                return false;
            }
        }
        updateWinCounter(4);
        winner[0] = 4;
        winner[1] = 0;
        winner[2] = 0;
        return true;
    }
    private void swapFirstMoveMaker()
    {
        if (whoGetsFirstMove == 1)
        {
            whoGetsFirstMove = 2;
        }
        else
        {
            whoGetsFirstMove = 1;
        }
    }
    @Override
    public String toString()
    {
        return (  gameID + "\n" +
                player1WinCounter  + "\n" +
                player2WinCounter + "\n" +
                draws  + "\n" +
                whoGetsFirstMove  + "\n" +
                winner[0] + "\n" +
                winner[1]  + "\n" +
                winner[2]  + "\n" +
                currentPlayer + "\n" +
                xGoesTo  + "\n");
    }


}
