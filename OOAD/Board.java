import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Board extends JFrame {
    private static final int rows = 6;
    private static final int cols = 7;
    public static JButton[][] buttons;
    public static JLabel[][] labels;
    public int row;
    public int col;
    public static String[][] board; // Change type to String
    private static Map<String, BufferedImage> pieceImages;
    private static Map<String, BufferedImage> pieceImagesR;
    public int row1;
    public int col1;
    public int row2;
    public int col2;

    private static final Color lightTileColor = Color.decode("#FFF5EE");
    private static final Color darkTileColor = Color.decode("#778899");

    public static void initializeBoard() {
        // Initialize the board with pieces
        board = new String[rows][cols]; // Change type to String
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = " "; // Use String value
            }
        }
        // BLACK
        board[0][0] = "Pb";
        board[0][1] = "Hb";
        board[0][2] = "Tb";
        board[0][3] = "Sb";
        board[0][4] = "Tb";
        board[0][5] = "Hb";
        board[0][6] = "Pb";

        board[1][0] = "Ab";
        board[1][1] = "Ab";
        board[1][2] = "Ab";
        board[1][3] = "Ab";
        board[1][4] = "Ab";
        board[1][5] = "Ab";
        board[1][6] = "Ab";
        // WHITE
        board[4][0] = "Aw";
        board[4][1] = "Aw";
        board[4][2] = "Aw";
        board[4][3] = "Aw";
        board[4][4] = "Aw";
        board[4][5] = "Aw";
        board[4][6] = "Aw";

        board[5][0] = "Pw";
        board[5][1] = "Hw";
        board[5][2] = "Tw";
        board[5][3] = "Sw";
        board[5][4] = "Tw";
        board[5][5] = "Hw";
        board[5][6] = "Pw";

    }

    public static void updateButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setIcon(new ImageIcon(getPieceImage(board[i][j])));
                buttons[i][j].setText(board[i][j]);
                buttons[i][j].revalidate(); // To ensure proper update
                buttons[i][j].repaint(); // To ensure proper update

                buttons[i][j].setForeground(buttons[i][j].getBackground());
            }
        }

    }

    private static void flipBoard() {
        // Create a temporary array to store the flipped board
        String[][] flippedBoard = new String[rows][cols];

        // Populate the flipped board with the contents of the original board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flippedBoard[i][j] = board[rows - 1 - i][cols - 1 - j];
            }
        }

        // Update the original board with the flipped board
        board = flippedBoard;
    }

    public static void updateBoard(int row1, int col1, int row2, int col2) {
        // Update the board array
        String pieceToMove = board[row1][col1];
        board[row2][col2] = pieceToMove;
        board[row1][col1] = " ";

        // Update the button at the source position
        buttons[row1][col1].setIcon(new ImageIcon(getPieceImage(board[row1][col1])));
        buttons[row1][col1].setText(board[row1][col1]);

        // Update the button at the destination position
        buttons[row2][col2].setIcon(new ImageIcon(getPieceImage(pieceToMove)));
        buttons[row2][col2].setText(pieceToMove);
        printBoard();
        updateButtons();
        transformTimePlus(board);
        flipBoard();

        updateButtons();

    }

    private static void transformTimePlus(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String piece = board[i][j];
                // y move. b move. y move. b move. now only transform
                // Additional conditions for the specific transformation rules you mentioned
                if (piece.endsWith("Tw") && buttonListener.turnCount % 4 == 0) {
                    // Transform white Time piece into white Plus piece after every 2 turns
                    board[i][j] = piece.replace("Tw", "Pw");
                } else if (piece.endsWith("Tb") && buttonListener.turnCount % 4 == 0) {
                    // Transform black Time piece into black Plus piece after every 2 turns
                    board[i][j] = piece.replace("Tb", "Pb");
                } else if (piece.endsWith("Pw") && buttonListener.turnCount % 4 == 0) {
                    // Transform white Plus piece into white Time piece after every 2 turns
                    board[i][j] = piece.replace("Pw", "Tw");
                } else if (piece.endsWith("Pb") && buttonListener.turnCount % 4 == 0) {
                    // Transform black Plus piece into black Time piece after every 2 turns
                    board[i][j] = piece.replace("Pb", "Tb");
                }
            }
        }
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createGameMenu());
        tableMenuBar.add(createViewMenu());

        return tableMenuBar;
    }

    private JMenu createGameMenu() {
        GameMenuListener menuListener = new GameMenuListener();
        final JMenu gameMenu = new JMenu("Options");

        // Create menu items
        JMenuItem startMenu = new JMenuItem("Start Game");
        JMenuItem saveMenu = new JMenuItem("Save Game");
        JMenuItem loadMenu = new JMenuItem("Load Game");

        // Add GameMenuListener to the menu
        startMenu.addActionListener(menuListener);
        saveMenu.addActionListener(menuListener);
        loadMenu.addActionListener(menuListener);

        // Add items to menu
        gameMenu.add(startMenu);
        gameMenu.add(saveMenu);
        gameMenu.add(loadMenu);

        return gameMenu;
    }

    private JMenu createViewMenu() {
        GameMenuListener menuListener = new GameMenuListener();
        final JMenu viewMenu = new JMenu("View");

        JMenuItem audienceView = new JMenuItem("Audience View");
        audienceView.addActionListener(menuListener);
        viewMenu.add(audienceView);

        return viewMenu;

    }

    public Board() {
        initializePieceImagesR();
        initializePieceImages();
        initializeBoard();
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // here
        buttonListener.isWhiteTurn = true; // white team go first.
        System.out.println("On Board: ");
        buttonListener.isWhitePlayer();

        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        add(boardPanel);
        buttons = new JButton[rows][cols];
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); // Add a larger border
        // Create and add buttons for each cell on the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton(board[i][j]);
                buttons[i][j].setIcon(new ImageIcon(getPieceImage(board[i][j])));
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBackground(((i + j) % 2 == 0) ? lightTileColor : darkTileColor);

                // Add ActionListener to each button
                buttons[i][j].addActionListener(new buttonListener(i, j, board));
                boardPanel.add(buttons[i][j]);
            }
        }

        // Update buttons based on the initial state of the board
        updateButtons();
    
        // Add the menu bar to the frame
        setJMenuBar(createTableMenuBar());
        
        createGuiPanel();
        setVisible(true);
    }

    
    public static JPanel createGuiPanel() {
        JPanel guiPanel = new JPanel(new GridLayout(rows, cols));
        labels = new JLabel[rows][cols];
        guiPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        System.out.println("createCount::> " + buttonListener.turnCount);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                labels[i][j] = new JLabel();
                labels[i][j].setIcon(new ImageIcon(getPieceImage(board[i][j])));
                labels[i][j].setBorder(BorderFactory.createEmptyBorder());// to prevent border line
                labels[i][j].revalidate(); // To ensure proper update
                labels[i][j].repaint(); // To ensure proper update
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground(((i + j) % 2 == 0) ? Color.WHITE : Color.GRAY);

                labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i][j].setVerticalAlignment(SwingConstants.CENTER);
                
                guiPanel.add(labels[i][j]);
            }
        }

        return guiPanel;
    }

    public static void updateLabels0() {
        System.out.println("count...update...>" + buttonListener.turnCount);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labels[i][j].setIcon(new ImageIcon(getPieceImage(board[i][j])));
                labels[i][j].revalidate(); // To ensure proper update
                labels[i][j].repaint(); // To ensure proper update
                labels[i][j].setForeground(buttons[i][j].getBackground());

            }
        }
    }

    public static void updateLabels() {
        System.out.println("count...update...>" + buttonListener.turnCount);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labels[i][j].setIcon(new ImageIcon(getPieceImageR(board[i][j])));
                labels[i][j].revalidate(); // To ensure proper update
                labels[i][j].repaint(); // To ensure proper update
                labels[i][j].setForeground(buttons[i][j].getBackground());

            }
        }
    }

    public static void updateRLabels() {
        System.out.println("count...rrr...>" + buttonListener.turnCount);
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                labels[rows - 1 - i][cols - 1 - j].setIcon(new ImageIcon(getPieceImage(board[i][j])));
                labels[rows - 1 - i][cols - 1 - j].revalidate(); // To ensure proper update
                labels[rows - 1 - i][cols - 1 - j].repaint(); // To ensure proper update
                labels[rows - 1 - i][cols - 1 - j].setForeground(buttons[i][j].getBackground());

            }
        }
    }

    public static void initializePieceImagesR() {
        pieceImagesR = new HashMap<>();
        try {
            // white
            pieceImagesR.put("Pw", ImageIO.read(new File("images/whitePlus.png")));
            pieceImagesR.put("Hw", ImageIO.read(new File("images/whiteHourglass.png")));
            pieceImagesR.put("Tw", ImageIO.read(new File("images/whiteTime.png")));
            pieceImagesR.put("Sw", ImageIO.read(new File("images/whiteSun.png")));
            pieceImagesR.put("Aw", ImageIO.read(new File("images/whitePointR.png")));
            // Check if the values are available before adding to the map
            if (new File("images/whitePointR.png").exists()) {
                pieceImagesR.put("ARw", ImageIO.read(new File("images/whitePoint.png")));
            }
            // black
            pieceImagesR.put("Pb", ImageIO.read(new File("images/blackPlus.png")));
            pieceImagesR.put("Hb", ImageIO.read(new File("images/blackHourglass.png")));
            pieceImagesR.put("Tb", ImageIO.read(new File("images/blackTime.png")));
            pieceImagesR.put("Sb", ImageIO.read(new File("images/blackSun.png")));
            pieceImagesR.put("Ab", ImageIO.read(new File("images/blackPointR.png")));
            // Check if the values are available before adding to the map
            if (new File("images/blackPointR.png").exists()) {
                pieceImagesR.put("ARb", ImageIO.read(new File("images/blackPoint.png")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initializePieceImages() {
        pieceImages = new HashMap<>();
        try {
            // white
            pieceImages.put("Pw", ImageIO.read(new File("images/whitePlus.png")));
            pieceImages.put("Hw", ImageIO.read(new File("images/whiteHourglass.png")));
            pieceImages.put("Tw", ImageIO.read(new File("images/whiteTime.png")));
            pieceImages.put("Sw", ImageIO.read(new File("images/whiteSun.png")));
            pieceImages.put("Aw", ImageIO.read(new File("images/whitePoint.png")));
            // pieceImages.put("ARw", ImageIO.read(new File("images/whitePointR.png")));
            // Check if the values are available before adding to the map
            if (new File("images/whitePointR.png").exists()) {
                pieceImages.put("ARw", ImageIO.read(new File("images/whitePointR.png")));
            }
            // black
            pieceImages.put("Pb", ImageIO.read(new File("images/blackPlus.png")));
            pieceImages.put("Hb", ImageIO.read(new File("images/blackHourglass.png")));
            pieceImages.put("Tb", ImageIO.read(new File("images/blackTime.png")));
            pieceImages.put("Sb", ImageIO.read(new File("images/blackSun.png")));
            pieceImages.put("Ab", ImageIO.read(new File("images/blackPoint.png")));
            // pieceImages.put("ARb", ImageIO.read(new File("images/blackPointR.png")));
            // Check if the values are available before adding to the map
            if (new File("images/blackPointR.png").exists()) {
                pieceImages.put("ARb", ImageIO.read(new File("images/blackPointR.png")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedImage getPieceImageR(String piece) {

        if (pieceImagesR.containsKey(piece)) {
            BufferedImage originalImage = pieceImagesR.get(piece);

            if (buttonListener.turnCount != 0 && buttonListener.turnCount % 2 == 1) {
                // Flip the image 180 degrees
                AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
                tx.translate(-originalImage.getWidth(null), -originalImage.getHeight(null));

                // Create a new image with the flipped transformation
                AffineTransformOp op = new AffineTransformOp(tx,
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                return op.filter(originalImage, null);
            } else {
                // No flip, return the original image
                return originalImage;
            }
        } else {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }

    }

    public static BufferedImage getPieceImage(String piece) {

        if (pieceImages.containsKey(piece)) {
            BufferedImage originalImage = pieceImages.get(piece);

            if (buttonListener.turnCount != 0 && buttonListener.turnCount % 2 == 1) {
                // Flip the image 180 degrees
                AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
                tx.translate(-originalImage.getWidth(null), -originalImage.getHeight(null));

                // Create a new image with the flipped transformation
                AffineTransformOp op = new AffineTransformOp(tx,
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                return op.filter(originalImage, null);
            } else {
                // No flip, return the original image
                return originalImage;
            }
        } else {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }

    }

    // TESTING
    public static void printBoard() {
        if (buttonListener.turnCount == 0) {
            System.out.println("---------------------ori--------------------------------");
            // Print the current state of the board
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("------------------------ori-----------------------------------");
            updateLabels0();
        } else if (buttonListener.turnCount != 0 && buttonListener.turnCount % 2 == 1) {
            System.out.println("---------------------ori--------------------------------");
            // Print the current state of the board
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("------------------------ori-----------------------------------");
            updateLabels();
        } else {
            System.out.println("------------------------reverse-----------------------------------");
            // Print the current state of the board in reverse order
            for (int i = rows - 1; i >= 0; i--) {
                for (int j = cols - 1; j >= 0; j--) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("-------------------------reverse----------------------------------");
            updateRLabels();
        }
    }

}
