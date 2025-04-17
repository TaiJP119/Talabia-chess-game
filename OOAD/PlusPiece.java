public class PlusPiece {
    
    public static boolean PlusMovementPiece(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        // only valid when vertical or horizontal move
        boolean isVerticalMove = fromRow != toRow && fromCol == toCol;
        boolean isHorizontalMove = fromRow == toRow && fromCol != toCol;

        if (isVerticalMove || isHorizontalMove) {
            return isPathClear(board, fromRow, fromCol, toRow, toCol);
        } else {
            System.out.println("Error move in PlusPiece: Invalid move");
            return false;
        }
    }

    private static boolean isPathClear(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        
        int minStepRow = Math.min(fromRow, toRow);
        int maxStepRow = Math.max(fromRow, toRow);
        int minStepCol = Math.min(fromCol, toCol);
        int maxStepCol = Math.max(fromCol, toCol);

        if (fromRow != toRow) {
            for (int i = minStepRow + 1; i < maxStepRow; i++) {
                if (!board[i][fromCol].equals(" ")) {
                    System.out.println("Error move in PlusPiece: Path not clear");
                    return false;
                }
            }
        } else if (fromCol != toCol) {
            for (int j = minStepCol + 1; j < maxStepCol; j++) {
                if (!board[fromRow][j].equals(" ")) {
                    System.out.println("Error move in PlusPiece: Path not clear");
                    return false;
                }
            }
        }

        return true;
    }
}
