import javax.swing.*;
import java.awt.*;


//Class for singular input in grid | extends default Swing text field
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
        this.setDocument(new LengthRestrictedDocument(1)); //limits text size to 1 number from 1-9
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void given(boolean set){
        this.set = set;
    } //to differentiate beetwen user input and program generated

    public boolean isSet() {
        return set;
    }
}
