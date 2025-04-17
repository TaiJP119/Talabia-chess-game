import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class buttonListener implements ActionListener {
    private int secondRow;
    private int secondCol;
    private String[][] board;
    public static String[] firstClickValues = new String[3];
    public static String[] secondClickValues = new String[3];
    private static int clickCount;
    public String buttonText;
    private static boolean isWhiteButton; // New field to track the color of the button
    public static boolean isWhiteTurn; // Black = T, white = F
    public JButton clickedButton;
    public static int turnCount;

    // Add this method to toggle the player turn after each move
    public static void togglePlayerTurn() {
        isWhiteTurn = !isWhiteTurn;

    }

    // Add this method to check if it's currently the black player's turn
    public static boolean isWhitePlayer() {
        if (isWhiteTurn) {
            System.out.println("White team");
        } else {
            System.out.println("Black team");
        }

        return isWhiteTurn;
    }

    public buttonListener(int row, int col, String[][] board) {
        this.secondRow = row;
        this.secondCol = col;
        this.board = board;
        isWhiteButton = true; // Set the starting color (white team)
    }

    public boolean Turn() {
        if ((isWhiteTurn == true && firstClickValues[2].endsWith("w"))
                || (isWhiteTurn == false && firstClickValues[2].endsWith("b"))) {
            clickCount++;
            return true;
        } else {
            // Display a message or handle an invalid move for the wrong player's turn
            JOptionPane.showMessageDialog(clickedButton,
                    "It's not your turn.",
                    "Invalid Move",
                    JOptionPane.ERROR_MESSAGE);
            clickCount = 0;
            System.out.println("error move here: Turn");
            return false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        clickedButton = (JButton) e.getSource();
        // Check if the button's color matches the current player's turn

        buttonText = clickedButton.getText();
        System.out.println("Clicked Button Value: " + buttonText);

        // Get the values from the clicked button
        String[] values = { String.valueOf(secondRow), String.valueOf(secondCol), buttonText };

        // First click

        if (clickCount == 0) {
            System.arraycopy(values, 0, firstClickValues, 0, 3);
            System.out.println(Arrays.toString(firstClickValues));
            System.out.println("clickCount: " + clickCount);
            Turn();

        }

        else if (clickCount == 1) {
            // Second click
            System.arraycopy(values, 0, secondClickValues, 0, 3);
            System.out.println(Arrays.toString(secondClickValues));
            System.out.println("clickCount: " + clickCount);
            if (validMove.isValid(firstClickValues, secondClickValues, board)) {

                turnCount++;
                System.out.println("Turn count: " + buttonListener.turnCount);
                Board.updateBoard(
                        Integer.parseInt(firstClickValues[0]),
                        Integer.parseInt(firstClickValues[1]),
                        Integer.parseInt(secondClickValues[0]),
                        Integer.parseInt(secondClickValues[1]));

                JOptionPane.showMessageDialog(clickedButton,
                        "First Clicked Button (From): " + Arrays.toString(firstClickValues) + "\n" +
                                "Second Clicked Button (To): " + Arrays.toString(secondClickValues) + "\n" + "\n" +
                                "Turn: " + isWhitePlayer(),
                        "Valid Move",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Display an error message or handle an invalid move

                JOptionPane.showMessageDialog(clickedButton,
                        "Invalid Move! Please make a valid move.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                clickCount = 0;
                return;
            }

            togglePlayerTurn();
            System.out.println("Old game!!");
            isWhitePlayer();
            // Reset click count after processing the second button click
            clickCount = 0;
            GameWin.winning();
            return;
        }

    }

    // TESTING
    public void printBoardB() {
        // Print the current state of the board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------");
        // Print the current state of the board in reverse order
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------");
    }
}
