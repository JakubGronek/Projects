import java.util.*;

public class Game {
    enum Player {
        X,
        O,
        none
    }
    private Player[][] board;
    private Player winner;
    private Player current;
    public Game(){
        current = Player.O;
        board = new Player[][]{
                {Player.X, Player.O, Player.O},
                {Player.X, Player.O, Player.X},
                {Player.O, Player.O, Player.O}};
    }

    public Player[][] getBoard() {
        return board;
    }

    public Player getWinner() {
        return winner;
    }

    public void insert(Player player, int row, int col){
        if (player == current){
            board[row][col] = player;
        }
    }

    public Player checkInputWin(int row, int col){
        if (checkRow(row) != Player.none || checkCol(col) != Player.none || checkDiagonal(row, col) != Player.none){
            return current;
        }
        return Player.none;
    }

    public Player checkRow(int row){
        Set<Player> freq = new HashSet<>(Arrays.asList(board[row]));
        if(freq.size() == 1){
            return board[row][0];
        }
        return Player.none;
    }

    public Player checkCol(int col){
        Player[] tmp = new Player[3];
        for(int row=0;row<3;row++){
            tmp[row] = board[row][col];
        }
        if(tmp[0] == tmp[1] && tmp[1] == tmp[2]){
            return board[0][col];
        }
        return Player.none;
    }


    public Player checkDiagonal(int row, int col){
        if(row+col == 1 || row+col == 3){
            return Player.none;
        }else{
//            To DO
        }
        return Player.none;
    }
}
