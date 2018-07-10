package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Asignatura;

public class ModuloVistaAsignatura extends ModuloVista {

    private Asignatura asignatura;

    public ModuloVistaAsignatura(Asignatura asignatura) {
        super(asignatura.getNombre(), asignatura.getCodigo());
        this.asignatura = asignatura;

        infoPanel.put("PROFESOR", asignatura.getProfesor());
        infoPanel.put("CRÉDITOS", asignatura.getCreditos() + " crédito" + ((asignatura.getCreditos() == 1) ? "" : "s"));
        infoPanel.put("AREA ACADÉMICA", asignatura.getArea().getNombre());

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
