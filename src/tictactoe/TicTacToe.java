package tictactoe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private boolean isPlayerX;
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        isPlayerX = true;

        // Create buttons and add action listeners
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        statusLabel = new JLabel("Player X's turn");
        add(statusLabel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (isPlayerX) {
                button.setText("X");
                statusLabel.setText("Player O's turn");
            } else {
                button.setText("O");
                statusLabel.setText("Player X's turn");
            }
            isPlayerX = !isPlayerX;

            if (checkWin()) {
                String winner = isPlayerX ? "O" : "X";
                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                resetGame();
            } else if (checkDraw()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
            }
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = buttons[row][col].getText();
            }
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][0].equals(board[row][2]) && !board[row][0].isEmpty()) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(board[1][col]) && board[0][col].equals(board[2][col]) && !board[0][col].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].isEmpty()) ||
                (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].isEmpty())) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        isPlayerX = true;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
