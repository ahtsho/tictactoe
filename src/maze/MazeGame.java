import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeGame extends JFrame implements KeyListener {
    private int currentLevel;
    private int mazeSize;
    private int robotRow;
    private int robotCol;
    private JLabel[][] mazeCells;
    private JLabel infoLabel;

    public MazeGame() {
        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        mazeSize = 50;
        currentLevel = 1;
        robotRow = 0;
        robotCol = 0;

        mazeCells = new JLabel[mazeSize][mazeSize];
        JPanel mazePanel = new JPanel(new GridLayout(mazeSize, mazeSize));

        for (int row = 0; row < mazeSize; row++) {
            for (int col = 0; col < mazeSize; col++) {
                mazeCells[row][col] = new JLabel();
                mazeCells[row][col].setOpaque(true);
                mazeCells[row][col].setBackground(Color.WHITE);
                mazePanel.add(mazeCells[row][col]);
            }
        }

        infoLabel = new JLabel("Level 1");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        add(mazePanel, BorderLayout.CENTER);
        add(infoLabel, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);

        updateMaze();
        startGame();
    }

    private void startGame() {
        EventQueue.invokeLater(() -> {
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void updateMaze() {
        for (int row = 0; row < mazeSize; row++) {
            for (int col = 0; col < mazeSize; col++) {
                if (row == robotRow && col == robotCol) {
                    mazeCells[row][col].setBackground(Color.GREEN);
                } else if (row == mazeSize - 1 && col == mazeSize - 1) {
                    mazeCells[row][col].setBackground(Color.YELLOW);
                } else {
                    mazeCells[row][col].setBackground(Color.WHITE);
                }
            }
        }

        infoLabel.setText("Level " + currentLevel);
    }

    private void nextLevel() {
        currentLevel++;
        mazeSize++;
        robotRow = 0;
        robotCol = 0;

        if (currentLevel > 1) {
            JOptionPane.showMessageDialog(this, "Congratulations! You completed Level " + (currentLevel - 1));
        }

        updateMaze();
    }

    private void moveRobot(int newRow, int newCol) {
        if (newRow >= 0 && newRow < mazeSize && newCol >= 0 && newCol < mazeSize) {
            robotRow = newRow;
            robotCol = newCol;
            updateMaze();

            if (robotRow == mazeSize - 1 && robotCol == mazeSize - 1) {
                if (currentLevel >= 3) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You completed Level " + currentLevel);
                    JOptionPane.showMessageDialog(this, "You won the game!");
                    System.exit(0);
                } else {
                    nextLevel();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                moveRobot(robotRow - 1, robotCol);
                break;
            case KeyEvent.VK_DOWN:
                moveRobot(robotRow + 1, robotCol);
                break;
            case KeyEvent.VK_LEFT:
                moveRobot(robotRow, robotCol - 1);
                break;
            case KeyEvent.VK_RIGHT:
                moveRobot(robotRow, robotCol + 1);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MazeGame::new);
    }
}
