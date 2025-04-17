public class PointPieces {

    public static boolean definedPointPiece(String[][] board, int fromRow, int fromCol, int toRow) {
        if (isPathClear(board, fromRow, fromCol, toRow) == true) {

            if (toRow == 0 && board[fromRow][fromCol].charAt(1) != 'R') {// heat upper boarder & Change the piece state
                                                                         // to
                                                                         // xRx
                board[fromRow][fromCol] = board[fromRow][fromCol].charAt(0) + "R" + board[fromRow][fromCol].charAt(1);

                return ForwardPointPiece(board, toRow);

            } else if (board[fromRow][fromCol].charAt(1) != 'R') { // is not reverse piece
                return ForwardPointPiece(board, toRow);

            } else if (toRow == 5 && board[fromRow][fromCol].charAt(1) == 'R') { // heat below boarder & Change the
                                                                                 // piece
                                                                                 // state to xx
                board[fromRow][fromCol] = board[fromRow][fromCol].charAt(0) + "" + board[fromRow][fromCol].charAt(2);

                return BackwardPointPiece(board, toRow);

            } else if (board[fromRow][fromCol].charAt(1) == 'R') { // is reverse piece
                return BackwardPointPiece(board, toRow);
            } else {
                System.out.println("error move here: definedPointPiece");
                return false;
            }
        } else {
            System.out.println("error move here: isPathClear in definedPointPiece");
            return false;
        }

    }

    // if is xx, normal Point
    public static boolean ForwardPointPiece(String[][] board, int toRow) {
        if ((toRow == validMove.fromRow - 1 && validMove.fromCol == validMove.toCol)
                || (toRow == validMove.fromRow - 2 && validMove.fromCol == validMove.toCol)) {
            return true;
        } else {
            System.out.println("error move here: ForwardPointPiece");
            return false;
        }
    }

    // if is xRx, reverse Point
    public static boolean BackwardPointPiece(String[][] board, int toRow) {
        if ((toRow == validMove.fromRow + 1 && validMove.fromCol == validMove.toCol)
                || (toRow == validMove.fromRow + 2 && validMove.fromCol == validMove.toCol)) {
            return true;
        } else {
            System.out.println("error move here: BackwardPointPiece");
            return false;
        }
    }

    private static boolean isPathClear(String[][] board, int fromRow, int fromCol, int toRow) {
        int minStep = Math.min(fromRow, toRow);
        int maxStep = Math.max(fromRow, toRow);

        // Check if there are no pieces in the path
        for (int i = minStep + 1; i < maxStep; i++) {
            if (!board[i][fromCol].equals(" ")) {
                System.out.println("error move here: isPathClear");
                return false;
            }
        }
        return true;
    }
}
