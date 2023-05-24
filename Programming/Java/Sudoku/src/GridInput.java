import javax.swing.*;
import java.awt.*;

public class GridInput extends JTextField {
    private Font font;
    private boolean set = false;
    private int row;
    private int col;

    GridInput(int row, int col){
        this.row = row;
        this.col = col;
        font  = new Font("SansSherif",Font.BOLD,60);
        this.setSize(10,10);
        this.setFont(font);
        this.setDocument(new LengthRestrictedDocument(1));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void isGiven(boolean set){
        this.set = set;
    }

    public boolean isSet() {
        return set;
    }
}
