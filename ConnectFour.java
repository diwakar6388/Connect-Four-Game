import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFour extends JFrame {

    // Constants for grid size
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final Color EMPTY_COLOR = Color.WHITE;
    private static final Color PLAYER1_COLOR = Color.RED;
    private static final Color PLAYER2_COLOR = Color.YELLOW;

    private JPanel[][] grid;
    private int currentPlayer = 1;

    public ConnectFour() {
        setTitle("Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the grid
        grid = new JPanel[ROWS][COLS];
        JPanel board = new JPanel(new GridLayout(ROWS, COLS));

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = new JPanel();
                grid[row][col].setBackground(EMPTY_COLOR);
                grid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                board.add(grid[row][col]);
            }
        }

        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / (getWidth() / COLS);
                dropDisc(col);
            }
        });

        add(board);
        setVisible(true);
    }

    private void dropDisc(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col].getBackground().equals(EMPTY_COLOR)) {
                grid[row][col].setBackground(currentPlayer == 1 ? PLAYER1_COLOR : PLAYER2_COLOR);
                if (checkWin(row, col)) {
                    JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resetGame();
                } else {
                    currentPlayer = 3 - currentPlayer; // Switch player
                }
                break;
            }
        }
    }

    private boolean checkWin(int row, int col) {
        return (checkDirection(row, col, 1, 0) >= 4 ||  // Horizontal
                checkDirection(row, col, 0, 1) >= 4 ||  // Vertical
                checkDirection(row, col, 1, 1) >= 4 ||  // Diagonal down-right
                checkDirection(row, col, 1, -1) >= 4);  // Diagonal down-left
    }

    private int checkDirection(int row, int col, int dRow, int dCol) {
        int count = 1;
        Color color = grid[row][col].getBackground();

        // Check in the positive direction
        for (int i = 1; i < 4; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS)
                break;
            if (!grid[newRow][newCol].getBackground().equals(color))
                break;
            count++;
        }

        // Check in the negative direction
        for (int i = 1; i < 4; i++) {
            int newRow = row - i * dRow;
            int newCol = col - i * dCol;
            if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS)
                break;
            if (!grid[newRow][newCol].getBackground().equals(color))
                break;
            count++;
        }

        return count;
    }

    private boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (grid[0][col].getBackground().equals(EMPTY_COLOR))
                return false;
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col].setBackground(EMPTY_COLOR);
            }
        }
        currentPlayer = 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnectFour());
    }
}