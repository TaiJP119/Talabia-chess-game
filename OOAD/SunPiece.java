public class SunPiece {
    public static boolean SunMovementPiece(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        // Math.abs() to get the absolute difference, ∣a−b∣
        int stepRow = Math.abs(fromRow - toRow);
        int stepCol = Math.abs(fromCol - toCol);
        if (stepRow <= 1 && stepCol <= 1) {
            return true;
        }

        else {
            System.out.println("Error move in SunPiece: Invalid move");
            return false;
        }
    }

}
