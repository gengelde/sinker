/**
 * Final Project Sinker
 *
 * @author Garrett Engelder
 * @version 04/28/23
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GridGUI extends JFrame 
{
    private JButton[][] gridButtons;
    private JLabel[] rowLabels;
    private JLabel[] colLabels;
    private JLabel triesLeftLabel;
    private boolean[][] pieceGrid;
    private int numPieces;
    private int triesLeft;
    private int piecesLeft;
    
    public GridGUI() 
    {
        super("Sinker");
        
        pieceGrid = new boolean[10][10];
        numPieces = 0;
        triesLeft = 80;
        piecesLeft = 31;
        
        placePieces(4, 1, 2);
        placePieces(3, 1, 3);
        placePieces(2, 1, 4);
        placePieces(1, 1, 6);

        gridButtons = new JButton[10][10];
        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) 
        {
            for (int j = 0; j < 10; j++) 
            {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].addActionListener(e -> 
                {
                    JButton btn = (JButton) e.getSource();
                    int row = -1, col = -1;
                    for (int i1 = 0; i1 < 10; i1++) 
                    {
                        for (int j1 = 0; j1 < 10; j1++) 
                        {
                            if (gridButtons[i1][j1] == btn) 
                            {
                                row = i1;
                                col = j1;
                                break;
                            }
                        }
                        if (row != -1) 
                        {
                            break;
                        }
                    }
                    if (!hasWaterOrExplosionIcon(btn)) {
                        if (pieceGrid[row][col]) 
                        {
                            ImageIcon explosion = new ImageIcon("explosion.jpg");
                            btn.setIcon(explosion);
                            piecesLeft--;
                        } 
                        else 
                        {
                            ImageIcon water = new ImageIcon("water.jpg");
                            btn.setIcon(water);
                        }
                        triesLeft--;
                        triesLeftLabel.setText("Tries left: " + triesLeft + "     Pieces left:" + piecesLeft);
                        if (piecesLeft == 0) 
                        {
                            int option = JOptionPane.showOptionDialog(null, "You sunk all of the ships, you win! Play again?", "Congratulations!",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                            
                            if (option == JOptionPane.YES_OPTION) 
                            {
                                dispose();
                                new GridGUI();
                            } 
                            else 
                            {
                                System.exit(0);
                            }
                        } 
                        else if (triesLeft == 0) 
                        {
                            int option = JOptionPane.showOptionDialog(null, "Too many tries, you lose! Try again?", "Game Over",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                    
                            if (option == JOptionPane.YES_OPTION) 
                            {
                                dispose();
                                new GridGUI();
                            } 
                            else 
                            {
                                System.exit(0);
                            }
                        }
                    }
                });
            gridPanel.add(gridButtons[i][j]);
            }
        }

        rowLabels = new JLabel[10];
        colLabels = new JLabel[10];
        JPanel labelPanel = new JPanel(new GridLayout(11, 11));
        labelPanel.add(new JLabel(" "));
        
        for (int i = 0; i < 10; i++) 
        {
            rowLabels[i] = new JLabel(Character.toString((char) ('A' + i)), SwingConstants.CENTER);
            labelPanel.add(rowLabels[i]);
        }
        for (int i = 0; i < 10; i++) 
        {
            colLabels[i] = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            labelPanel.add(colLabels[i]);
            for (int j = 0; j < 10; j++) 
            {
                labelPanel.add(gridButtons[i][j]);
            }
        }

        triesLeftLabel = new JLabel("Tries left: " + triesLeft + "     Pieces left:" + piecesLeft);
        triesLeftLabel.setHorizontalAlignment(SwingConstants.LEFT);
        triesLeftLabel.setVerticalAlignment(SwingConstants.TOP);
        add(triesLeftLabel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
        add(gridPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }
    private void placePieces(int num, int length, int width) 
    {
        Random rand = new Random();
        for (int i = 0; i < num; i++) 
        {
            int row = -1, col = -1;
            boolean vertical = rand.nextBoolean();
            while (row == -1 || col == -1 || overlapsPiece(row, col, length, width, vertical)) 
            {
                row = rand.nextInt(10 - (vertical ? length : width));
                col = rand.nextInt(10 - (vertical ? width : length));
            }
            for (int j = 0; j < (vertical ? length : width); j++) 
            {
                for (int k = 0; k < (vertical ? width : length); k++) 
                {
                    pieceGrid[row + j][col + k] = true;
                }
            }
            numPieces++;
        }
    }
    private boolean overlapsPiece(int row, int col, int length, int width, boolean vertical) 
    {
        for (int i = 0; i < (vertical ? length : width); i++) 
        {
            for (int j = 0; j < (vertical ? width : length); j++) 
            {
                if (pieceGrid[row + i][col + j]) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean hasWaterOrExplosionIcon(JButton button) 
    {
        Icon currentIcon = button.getIcon();
        return currentIcon != null &&
        (currentIcon.toString().contains("water.jpg") || currentIcon.toString().contains("explosion.jpg"));
    }
    public static void main(String[] args) 
    {
        new GridGUI();
    }
}
