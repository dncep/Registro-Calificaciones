package ids323.estudiantes.xswing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class XNumberField extends XTextField implements KeyListener, DocumentListener {

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    private static final String allowedCharacters = "-+0123456789";
    private int value;

    public XNumberField() {
        this(1);
    }

    public XNumberField(int text) {
        super("" + text, Pattern.compile("[-+]?\\d+"));

        this.addKeyListener(this);
        this.getDocument().addDocumentListener(this);
    }

    public int getValue() {
        try {
            return Integer.parseInt(getText());
        } catch(NumberFormatException x) {
            return 0;
        }
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!allowedCharacters.contains("" + e.getKeyChar())) e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        System.out.println(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println(e);
    }
}
