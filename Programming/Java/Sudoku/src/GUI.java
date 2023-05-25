import javax.naming.Context;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    GridInput[][] board;
    JFrame frame;
    JPanel grid;
    JPanel pane;

    public static void main(String[] args) {
        Sudoku s = new Sudoku(); //create new sudoku
        GUI g = new GUI(s); //create new gui window
        s.generate(Sudoku.Level.EASY); //
        g.setBoard(s.getGrid());
    }

    public GUI(Sudoku s) {

        grid = new JPanel();
        grid.setLayout(new GridLayout(9, 9));

        board = new GridInput[9][9];

        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(600,600));

        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        pane.setMinimumSize(new Dimension(500, 600));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = new GridInput(row, col);
//                board[row][col].addActionListener(e -> {
//                    for (int x = 0; x < 9; x++) {
//                        for (int y = 0; y < 9; y++) {
//                            String value = board[x][y].getText().strip();
//                            if(value != "" && !board[x][y].isSet()){
//                                if(s.isValid(Integer.parseInt(value), x, y)){
//                                    board[x][y].setForeground(Color.black);
//                                }else{
//                                    board[x][y].setForeground(Color.red);
//                                }
//                            }
//                        }
//                    }
//                });
                grid.add(board[row][col]);
            }
        }

        JButton solve = new JButton("Solve");
        solve.addActionListener(e -> {
            s.solve();
            setBoard(s.getGrid());
        });

        JButton check = new JButton("Check");
        check.addActionListener(e -> {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++){
                    String value = board[row][col].getText().strip();
                    if(value != "" && !board[row][col].isSet()){
                        if(s.isValid(Integer.parseInt(value), row, col)){
                            board[row][col].setForeground(Color.black);
                        }else{
                            board[row][col].setForeground(Color.red);
                        }
                    }
                }
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = 2;
        pane.add(grid, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        pane.add(solve, c);
        c.gridx = 1;
        pane.add(check, c);
        frame.add(pane);
        frame.pack();
        frame.setVisible(true);
    }

    public void setBoard(int[][] data) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = String.valueOf(data[row][col]);
                if (value.equals("0")) {
                    board[row][col].setText("");
                } else {
                    board[row][col].setText(value);
                    board[row][col].isGiven(true);
                    board[row][col].setEditable(false);
                    board[row][col].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
}
