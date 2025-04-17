public class HourglassPiece {
    public static boolean HourglassMovementPiece(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);
        
        boolean isValidMove = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        
        if(isValidMove) {
            return true;
        }
        
        else {
            System.out.println("Error move in HourglassPiece: Invalid move");
            return false;
        }
        
    }
}
