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

    private int id;
    private AreaAcademica area;
    private String codigo;
    private String nombre;
    private String profesor;
    private int creditos;

    private boolean editando = false;

    public Asignatura(int id) {
        this.id = id;
    }

    public Asignatura(Registro registro, AreaAcademica area, String codigo, String nombre, String profesor, int creditos) {
        this.id = registro.ID_ASIGNATURA++;
        this.area = area;
        this.codigo = codigo;
        this.nombre = nombre;
        this.profesor = profesor;
        this.creditos = creditos;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AreaAcademica getArea() {
        return area;
    }

    public void setArea(AreaAcademica area) {
        this.area = area;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
}
