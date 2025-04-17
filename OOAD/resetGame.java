public class resetGame {
    public static void resetGame() {
        Board.initializePieceImages();
        Board.initializeBoard();
        buttonListener.isWhiteTurn = true; // white team go first.
        buttonListener.turnCount = 0;
        Board.updateButtons();
        Board.printBoard(); // Optional: Print the board after reset for debugging
        System.out.println("New game:::::::");
        buttonListener.isWhitePlayer();
    }
}
