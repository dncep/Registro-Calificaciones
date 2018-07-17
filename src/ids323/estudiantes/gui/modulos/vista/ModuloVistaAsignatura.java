package ids323.estudiantes.gui.modulos.vista;

import ids323.estudiantes.data.Asignatura;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;

public class ModuloVistaAsignatura extends ModuloVista {

    private Asignatura asignatura;

    public ModuloVistaAsignatura(Tab tab, Asignatura asignatura) {
        super(tab, asignatura.getNombre(), asignatura.getCodigo());
        this.asignatura = asignatura;

        panelInfo.put("PROFESOR", "" + asignatura.getProfesor());
        panelInfo.put("CRÉDITOS", asignatura.getCreditos() + " crédito" + ((asignatura.getCreditos() == 1) ? "" : "s"));
        panelInfo.put("AREA ACADÉMICA", asignatura.getArea().getNombre());

        construir();
    }

    @Override
    protected void empezarEditar() {
        asignatura.setEditando(true);
        TabManager.closeTab(TabManager.getTabForToken(asignatura));
        TabManager.openTab(asignatura);
    }

    @Override
    public Object getValor() {
        return null;
    }

    @Override
    public boolean puedeGuardar() {
        return false;
    }

    @Override
    public Object guardar() {
        return null;
    }

    @Override
    public void enfocar() {

    }
}
