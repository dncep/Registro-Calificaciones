package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Asignatura;

public class ModuloEdicionAsignatura extends ModuloEdicion {

    private Asignatura asignatura;

    public ModuloEdicionAsignatura(Asignatura asignatura) {
        super("Editando asignatura " + asignatura.getCodigo());
        this.asignatura = asignatura;

        valores.add(new ValorEdicion<>("Nombre", asignatura.getNombre(), (v) -> v.length() > 0 ? null : "Nombre no debe estar vacío", (v) -> asignatura.setNombre(v)));
        valores.add(new ValorEdicion<>("Código", asignatura.getCodigo(), (v) -> v.length() > 0 ? null : "Código no debe estar vacío", (v) -> asignatura.setCodigo(v)));
        valores.add(new ValorEdicion<>("Área Académica", asignatura.getArea(), (v) -> null, asignatura::setArea));
        valores.add(new ValorEdicion<>("Créditos", asignatura.getCreditos(), (v) -> null, asignatura::setCreditos));
        valores.add(new ValorEdicion<>("Profesor", asignatura.getProfesor(), (v) -> null, asignatura::setProfesor));

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
