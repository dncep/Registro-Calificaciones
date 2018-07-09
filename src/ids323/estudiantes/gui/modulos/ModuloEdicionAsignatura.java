package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Asignatura;

import javax.swing.*;
import java.util.Calendar;

public class ModuloEdicionAsignatura extends ModuloEdicion {

    private Asignatura asignatura;

    public ModuloEdicionAsignatura(Asignatura asignatura) {
        super("Editando asignatura " + asignatura.codigo);
        this.asignatura = asignatura;

        valores.add(new ValorEdicion<>("Nombre", asignatura.nombre, (v) -> v.length() > 0 ? null : "Nombre no debe estar vacío", (v) -> asignatura.nombre = v));
        valores.add(new ValorEdicion<>("Código", asignatura.codigo, (v) -> v.length() > 0 ? null : "Código no debe estar vacío", (v) -> asignatura.codigo = v));
        valores.add(new ValorEdicion<>("Área Académica", asignatura.area, (v) -> null, (v) -> asignatura.area = v));
        valores.add(new ValorEdicion<>("Créditos", asignatura.creditos, (v) -> null, (v) -> asignatura.creditos = v));
        valores.add(new ValorEdicion<>("Profesor", asignatura.profesor, (v) -> null, (v) -> asignatura.profesor = v));

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
