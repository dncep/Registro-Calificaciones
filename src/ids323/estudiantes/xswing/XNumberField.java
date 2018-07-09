package ids323.estudiantes.xswing;

import java.util.regex.Pattern;

public class XNumberField extends XTextField {

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public XNumberField() {
        this(1);
    }

    public XNumberField(int text) {
        super("" + text, Pattern.compile("\\d+"));
    }

    public int getValue() {
        try {
            return Integer.parseInt(getText());
        } catch(NumberFormatException x) {
            return 0;
        }
    }
}
