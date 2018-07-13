package ids323.estudiantes.componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 2/11/2017.
 */
public class Relleno extends JPanel {

    public Relleno(boolean opaco) {
        this.setOpaque(opaco);
    }

    public Relleno(int tamano) {
        this(tamano, tamano);
    }

    public Relleno(int ancho, int alto) {
        this(false);
        Dimension dim = new Dimension(ancho, alto);
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);
    }

    public Relleno(int tamano, boolean opaco) {
        this(opaco);
        Dimension dim = new Dimension(tamano, tamano);
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);
    }

    public Relleno(int tamano, Color color) {
        this(tamano, true);
        this.setBackground(color);
    }
}
