import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.regex.Pattern;

public final class LengthRestrictedDocument extends PlainDocument {

    private final int limit;
    private final Pattern pattern = Pattern.compile("[1-9]");
    public LengthRestrictedDocument(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            if (pattern.matcher(str).matches()){
                super.insertString(offs, str, a);
            }
        }
    }
}