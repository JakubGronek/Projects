import java.util.Arrays;
import java.util.Random;

public class Sudoku {
    public static final int SIZE = 9;
    private int[][] grid;

//  Enum containing possible difficulty levels
    enum Level {
        EASY,
        MEDIUM,
        HARD
    }

//    Constructor creating new empty 9X9 array called grid
    public Sudoku() {
        grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
    }

//    Getter functions
    public int[][] getGrid(){
        return grid;
    }
    public int getValue(int row, int col) {
        return grid[row][col];
    }

//    Function to insert value into grid
    public void put(int number, int row, int col) {
        grid[row][col] = number;
    }

//    Function to print current grid to console
    public void print() {
        System.out.println();
        for (int row = 0; row < SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < SIZE; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|");
                }
                System.out.print(grid[row][column]);
            }
            System.out.println();
        }
        System.out.println();
    }

//    Functions for validating if number is placed correctly
    private boolean inRow(int number, int row) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == number) {
                return false;
            }
        }
        return true;
    }
    private boolean inCol(int number, int col) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col] == number) {
                return false;
            }
        }
        return true;
    }
    private boolean inBox(int number, int row, int col) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = col - col % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (grid[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

//Function that checks if input is correct
    public boolean isValid(int number, int row, int col) {
        return inRow(number, row) &&
                inCol(number, col) &&
                inBox(number, row, col);
    }

//    Backtracking function for solving sudoku
    public boolean solve() {
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                if (getValue(row, col) == 0) { //if element is empty
                    for (int test = 1; test <= Sudoku.SIZE; test++) { //cycle throught numbers 1-9
                        if (isValid(test, row, col)) { //if number can be put insert it
                            put(test, row, col);
                            if (solve()) { // try solving with new number inserted
                                return true; // if at the end of grid without errors
                            } else {
                                put(0, row, col); //if error ocured remove current number and try next
                            }
                        }
                    }
                    return false; //if no numbers are correct
                }
            }
        }
        return true; // if grid cant be solved
    }

//    Function to generate random grid
    public void generate(Level difficulty) {
        Random generator = new Random();
        int row;
        int col;
        int number;
        for (int i = 0; i < 9; i++) { //Put 9 random numbers in 9 random places
            boolean set = false;
            while (!set) {
                row = generator.nextInt(0, 9);
                col = generator.nextInt(0, 9);
                number = generator.nextInt(1, 10);
                if (isValid(number, row, col)) {
                    set = true;
                    put(number, row, col);
                }
            }
        }
        solve(); // solve grid
        switch (difficulty) { // remove numbers from grid based on difficulty
            case EASY -> filter(0.6);
            case MEDIUM -> filter(0.7);
            case HARD -> filter(0.8);
        }
    }

//    Function to remove random numbers from grid based on weight
    public void filter(double weight) {
        Random random = new Random();
        for (int row = 0; row<SIZE;row++){
            for (int col = 0; col < SIZE; col++){
                if(random.nextDouble() < weight){
                    grid[row][col] = 0;
                }
            }
        }
    }

//    Functions to export and import grid from file
    public void toFile(){
        FileOperations f = new FileOperations();
        String data = "";
        for (int[] row : grid){
            data += Arrays.toString(row).replace("[","{").replace("]","}")+"|";
        }
        data = data.substring(0,data.length()-1)+"";
        f.writeData(data);
    }
    public void gridFromFile(){
        FileOperations f = new FileOperations();
        String[] data = f.readData().split("\\|");
        String[] items;
        for(int i =0;i<9;i++){
            items = data[i].replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\s", "").split(",");
            for(int x=0;x<9;x++){
                grid[i][x] = Integer.parseInt(items[x]);
            }
        }
    }

}
