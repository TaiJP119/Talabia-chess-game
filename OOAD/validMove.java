public class validMove {
    public static int fromRow;
    public static int fromCol;
    public static String fromValue;

    public static int toRow;
    public static int toCol;
    public static String toValue;

    public static boolean isValid(String[] firstClickValues, String[] secondClickValues, String[][] board) {
        fromRow = Integer.parseInt(firstClickValues[0]);
        fromCol = Integer.parseInt(firstClickValues[1]);
        fromValue = firstClickValues[2];

        toRow = Integer.parseInt(secondClickValues[0]);
        toCol = Integer.parseInt(secondClickValues[1]);
        toValue = secondClickValues[2];

        // System.out.println("fromRow: " + fromRow);
        // System.out.println("fromCol: " + fromCol);
        System.out.println("fromValue: " + fromValue);
        // System.out.println("toRow: " + toRow);
        // System.out.println("toCol: " + toCol);
        System.out.println("toValue: " + toValue);

        // Check if the destination is within the board boundaries
        if (toRow < 0 || toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
            System.out.println("error move here: isValid");
            return false;
        }
        return validCapture() && piecesMovement();
    }

    private static boolean validCapture() {
        // Check the movement based on the color of the piece
        if (fromValue.endsWith("w") && (toValue.endsWith("b") || toValue.equals(" "))) {
            return true;
        } else if (fromValue.endsWith("b") && (toValue.endsWith("w") || toValue.equals(" "))) {
            // Add more conditions if needed
            return true;
        } else {
            System.out.println("error move here: validCapture");
            return false;
        }
    }

    public static boolean piecesMovement() {
        // Use a switch statement to handle different piece types
        switch (fromValue.charAt(0)) {
            case 'A':
                return PointPieces.definedPointPiece(Board.board, fromRow, fromCol, toRow);
            case 'H':
               return HourglassPiece.HourglassMovementPiece(fromRow, fromCol, toRow, toCol);
            case 'T':
                return TimePiece.TimeMovementPiece(Board.board, fromRow, fromCol, toRow, toCol);
            case 'P':
                return PlusPiece.PlusMovementPiece(Board.board, fromRow, fromCol, toRow, toCol);
            case 'S':
                return SunPiece.SunMovementPiece(Board.board, fromRow, fromCol, toRow , toCol);

            default:
                System.out.println("No such pieces");
                return false;
        }
    }

    private static boolean TimePiece() {
        System.out.println("Time piece logic");
        return true;
    }

    private static boolean PlusPiece() {
        System.out.println("Plus piece logic");
        return true;
    }

    private static boolean SunPiece() {
        System.out.println("Sun piece logic");
        return true;
    }

}
