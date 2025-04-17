import javax.swing.JOptionPane;

public class GameWin {
    public static void winning() {
        boolean SwFound = false;
        boolean SbFound = false;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (Board.board[i][j].equals("Sw")) {
                    SwFound = true;
                } else if (Board.board[i][j].equals("Sb")) {
                    SbFound = true;
                }
            }
        }

        if (!SwFound) {
            JOptionPane.showMessageDialog(null, "Black team wins!");
            resetGame.resetGame();
        } else if (!SbFound) {
            JOptionPane.showMessageDialog(null, "White team wins!");
            resetGame.resetGame();
        } else {
            // Continue the game
        }
    }

}
