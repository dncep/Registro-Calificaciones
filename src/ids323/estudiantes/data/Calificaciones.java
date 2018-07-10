package ids323.estudiantes.data;

import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.gui.modulos.*;
import ids323.estudiantes.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Calificaciones implements ModuleToken {

    private static final Image ICON = Commons.getIcon("calificaciones");

    private Trimestre trimestre;
    private Estudiante estudiante;
    private HashMap<Asignatura, Integer> calificaciones = new HashMap<>();
    private boolean editando = false;

    @Override
    public String getLabel() {
        return estudiante.id + " | " + trimestre;
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    @Override
    public String getHint() {
        return "Calificaciones de estudiante " + estudiante.getNombre() + " " + estudiante.getApellido() + " para trimestre " + trimestre;
    }

    @Override
    public Collection<ModuleToken> getSubTokens() {
        return Collections.emptyList();
    }

    @Override
    public boolean isExpandable() {
        return false;
    }

    @Override
    public DisplayModule createModule() {
        return (editando) ? new ModuloEdicionCalificaciones(this) : new ModuloVistaCalificaciones(this);
    }

    @Override
    public void onInteract() {
        editando = editando && TabManager.getTabForToken(this) != null;
        TabManager.openTab(this);
    }

    @Override
    public JPopupMenu generatePopup(ProjectExplorerItem explorerItem) {
        return null;
    }
}
