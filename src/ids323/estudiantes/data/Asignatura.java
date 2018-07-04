package ids323.estudiantes.data;

import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.gui.modulos.*;
import ids323.estudiantes.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Representación de una asignatura como objeto.
 * */
public class Asignatura implements ModuleToken {

    private static final Image ICON = Commons.getIcon("asignatura");

    public int id;
    public AreaAcademica area;
    public String codigo;
    public String nombre;
    public String profesor;
    public int creditos;

    private boolean editando = false;

    public Asignatura() {
    }

    @Override
    public String getLabel() {
        return codigo + " - " + nombre;
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    @Override
    public String getHint() {
        return creditos + " crédito" + ((creditos == 1) ? "" : "s") + " - " + area.getNombre();
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
    public String toString() {
        return codigo + " - " + nombre + " (ID: " + id + ")";
    }

    @Override
    public DisplayModule createModule() {
        return (editando) ? new ModuloEdicionAsignatura(this) : new ModuloVistaAsignatura(this);
    }

    @Override
    public void onInteract() {
        editando = editando && TabManager.getTabForToken(this) != null;
        TabManager.openTab(this);
    }

    @Override
    public JPopupMenu generatePopup(ProjectExplorerItem explorerItem) {
        JPopupMenu menu = new JPopupMenu();

        {
            JMenuItem item = new JMenuItem("Ver");

            item.addActionListener(e -> onInteract());

            menu.add(item);
        }
        {
            JMenuItem item = new JMenuItem("Editar");

            item.addActionListener(e -> {
                editando = true;
                TabManager.closeTab(TabManager.getTabForToken(this));
                TabManager.openTab(this);
            });

            menu.add(item);
        }
        {
            JMenuItem item = new JMenuItem("Borrar");

            item.addActionListener(e -> {
                System.out.println("Action not supported yet");
            });

            menu.add(item);
        }
        return menu;
    }
}
