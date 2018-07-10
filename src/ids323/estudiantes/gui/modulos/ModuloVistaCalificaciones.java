package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Calificaciones;

import javax.swing.*;

public class ModuloVistaCalificaciones extends JPanel implements DisplayModule {

    private Calificaciones calificaciones;

    public ModuloVistaCalificaciones(Calificaciones calificaciones) {
        this.calificaciones = calificaciones;
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
