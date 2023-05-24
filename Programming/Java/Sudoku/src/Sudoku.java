import java.util.Arrays;
import java.util.Random;

public class Sudoku {
    public static final int SIZE = 9;
    int[][] grid;

    enum Level {
        EASY,
        MEDIUM,
        HARD
    }

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

    public void generate(Level difficulty) {
        Random generator = new Random();
        int row;
        int col;
        int number;
        for (int i = 0; i < 9; i++) {
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
        solve();
        switch (difficulty) {
            case EASY:
                remove(0.6);break;
            case MEDIUM:
                remove(0.7);break;
            case HARD:
                remove(0.8);break;
        }
    }

    public boolean solve() {
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                if (getValue(row, col) == 0) {
                    for (int test = 1; test <= Sudoku.SIZE; test++) {
                        if (isValid(test, row, col)) {
                            put(test, row, col);

                            if (solve()) {
                                return true;
                            } else {
                                put(0, row, col);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

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

    public int getValue(int row, int col) {
        return grid[row][col];
    }

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

    public boolean isValid(int number, int row, int col) {
        return inRow(number, row) &&
                inCol(number, col) &&
                inBox(number, row, col);
    }

    public void put(int number, int row, int col) {
        grid[row][col] = number;
    }

    public void remove(double weight) {
        Random random = new Random();
        for (int row = 0; row<SIZE;row++){
            for (int col = 0; col < SIZE; col++){
                if(random.nextDouble() < weight){
                    grid[row][col] = 0;
                }
            }
        }
    }

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

    public int[][] getGrid(){
        return grid;
    }
}
