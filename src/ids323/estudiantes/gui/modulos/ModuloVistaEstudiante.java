package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Estudiante;

import javax.swing.*;
import java.awt.*;

public class ModuloVistaEstudiante extends JPanel implements DisplayModule {

    private Estudiante estudiante;

    public ModuloVistaEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;

        this.setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel(estudiante.nombre + " " + estudiante.apellido);
        title.setFont(title.getFont().deriveFont(24f).deriveFont(Font.BOLD));
        titlePanel.add(title);
        header.add(titlePanel, BorderLayout.NORTH);

        JPanel subtitlePanel = new JPanel();
        JLabel subtitle = new JLabel(estudiante.id + "");
        subtitle.setFont(subtitle.getFont().deriveFont(18f).deriveFont(Font.PLAIN));
        subtitlePanel.add(subtitle);
        header.add(subtitlePanel);

        this.add(header, BorderLayout.NORTH);
        this.add(new JPanel());
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean canSave() {
        return false;
    }

    @Override
    public Object save() {
        return null;
    }

    @Override
    public void focus() {

    }
}
