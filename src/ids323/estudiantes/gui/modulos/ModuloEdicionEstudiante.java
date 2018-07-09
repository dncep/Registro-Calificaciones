package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Estudiante;

import java.util.Calendar;

public class ModuloEdicionEstudiante extends ModuloEdicion {

    private Estudiante estudiante;

    private enum Nacionalidad {
        DOMINICANO,
        EXTRANJERO;

        @Override
        public String toString() {
            return name().charAt(0) + name().toLowerCase().substring(1);
        }
    }

    public ModuloEdicionEstudiante(Estudiante estudiante) {
        super("Editando estudiante " + estudiante.id);
        this.estudiante = estudiante;

        valores.add(new ValorEdicion<>("Nombre", estudiante.getNombre(), (v) -> v.length() > 0 ? null : "Nombre no debe estar vacío", (v) -> estudiante.setNombre(v)));
        valores.add(new ValorEdicion<>("Apellido", estudiante.getApellido(), (v) -> v.length() > 0 ? null : "Apellido no debe estar vacío", (v) -> estudiante.setApellido(v)));
        valores.add(new ValorEdicion<>("Condición Académica", estudiante.getEstado(), (v) -> null, (v) -> estudiante.setEstado(v)));
        valores.add(new ValorEdicion<>("Carrera", estudiante.getCarrera(), (v) -> null, (v) -> estudiante.setCarrera(v)));
        valores.add(new ValorEdicion<>("Nacionalidad", estudiante.isEsExtranjero() ? Nacionalidad.EXTRANJERO : Nacionalidad.DOMINICANO, (v) -> null, (v) -> estudiante.setExtranjero(v == Nacionalidad.EXTRANJERO)));
        valores.add(new ValorEdicion<>("Fecha de Nacimiento", estudiante.getFechaNacimiento(), (v) -> v.getTimeInMillis() < Calendar.getInstance().getTimeInMillis() ? null : "Fecha debe estar en el pasado", (v) -> estudiante.setFechaNacimiento(v)));

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
