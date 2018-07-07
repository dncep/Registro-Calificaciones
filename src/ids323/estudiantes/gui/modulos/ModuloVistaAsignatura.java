package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Asignatura;

import javax.swing.*;
import java.util.Calendar;

public class ModuloVistaAsignatura extends ModuloVista {

    private Asignatura asignatura;

    public ModuloVistaAsignatura(Asignatura asignatura) {
        super(asignatura.nombre, asignatura.codigo);
        this.asignatura = asignatura;

        infoPanel.put("PROFESOR", asignatura.profesor);
        infoPanel.put("CRÉDITOS", asignatura.creditos + " crédito" + ((asignatura.creditos == 1) ? "" : "s"));
        infoPanel.put("AREA ACADÉMICA", asignatura.area.getNombre());

        construir();
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
