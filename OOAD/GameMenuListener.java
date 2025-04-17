import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameMenuListener implements ActionListener {
    private JFrame viewFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem sourceMenu = (JMenuItem) e.getSource();
        String menuItemText = sourceMenu.getText();

        if ("Start Game".equals(menuItemText)) {
            // Create an instance of the ResetGame class
            resetGame resetGame = new resetGame();
            
            // Call the reset method to reset the game
            resetGame.resetGame();

        } else if ("Save Game".equals(menuItemText)) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                        oos.writeObject(Board.board);  // Assuming Board.board is Serializable
                        oos.writeObject(buttonListener.isWhiteTurn);
                        oos.writeObject(buttonListener.turnCount);
                        // Add more objects to save as needed
                        System.out.println("Game saved successfully!");
                    }
                } 
                   
            } catch(IOException ex) {
                ex.printStackTrace();
            }
    
            
        } else if ("Load Game".equals(menuItemText)) {
            try {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                    Board.board = (String[][]) ois.readObject();  // Assuming Board.board is Serializable
                    buttonListener.isWhiteTurn = (boolean) ois.readObject();
                    buttonListener.turnCount = (int) ois.readObject();
                    // Read more objects as needed
                    Board.updateButtons();  // Update GUI based on the loaded state
                    System.out.println("Game loaded successfully!");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        } else if("Audience View".equals(menuItemText)) {
            // Open audience view window
            if(viewFrame == null) {
                viewFrame =  new JFrame("Audience View");
                viewFrame.setSize(500, 500);
                viewFrame.setLocationRelativeTo(null);
                viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel viewPanel = Board.createGuiPanel();
                viewFrame.add(viewPanel);
                viewFrame.setVisible(true);
            }
            else {
                viewFrame.setVisible(true);
            }
        }
    }
}

