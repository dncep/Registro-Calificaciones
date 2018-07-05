package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Estudiante;

import javax.swing.*;

public class ModuloEdicionEstudiante extends JPanel implements DisplayModule {

    private Estudiante estudiante;

    public ModuloEdicionEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.add(new JLabel(this.getClass().getSimpleName()));
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
