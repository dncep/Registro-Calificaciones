package ids323.estudiantes.gui.modulos.vista;

import ids323.estudiantes.data.Profesor;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;

public class ModuloVistaProfesor extends ModuloVista {

    private Profesor profesor;

    public ModuloVistaProfesor(Tab tab, Profesor profesor) {
        super(tab, profesor.getNombre() + " " + profesor.getApellido(), "P" + profesor.getId());
        this.profesor = profesor;

        construir();
    }

    @Override
    protected void empezarEditar() {
        profesor.setEditando(true);
        TabManager.closeTab(TabManager.getTabForToken(profesor));
        TabManager.openTab(profesor);
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
