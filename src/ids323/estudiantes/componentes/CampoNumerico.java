package ids323.estudiantes.componentes;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class CampoNumerico extends CampoTexto implements KeyListener {

    private static final String allowedCharacters = "-+0123456789";

    public CampoNumerico() {
        this(1);
    }

    public CampoNumerico(int text) {
        super("" + text, Pattern.compile("[-+]?\\d+"));

        this.addKeyListener(this);
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
}
