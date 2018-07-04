package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Asignatura;

import javax.swing.*;

public class ModuloEdicionAsignatura extends JPanel implements DisplayModule {

    private Asignatura asignatura;

    public ModuloEdicionAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
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
