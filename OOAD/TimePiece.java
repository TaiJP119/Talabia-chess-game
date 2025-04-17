public class TimePiece {
    public static boolean TimeMovementPiece(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Check if the movement is diagonol
        if(rowDiff == colDiff) {
            // Check if the path is clear
            if(isDiagonalPathClear(board, fromRow, fromCol, toRow, toCol)) {
                return true;
            }
            else {
                System.out.println("Error move in TimePiece: Path not clear");
            }
        }
        else {
            System.out.println("Error move in TimePiece: Invalid move");
        }
        
        return false;
    }

    private static boolean isDiagonalPathClear(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = (toRow > fromRow) ? 1: -1;
        int colStep = (toCol > fromCol) ? 1 : -1;

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow && currentCol != toCol) {
            if (!board[currentRow][currentCol].equals(" ")) {
                return false; // Path is not clear
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }
}
