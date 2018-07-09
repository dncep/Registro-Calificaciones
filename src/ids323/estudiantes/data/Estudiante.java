package ids323.estudiantes.data;

import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.gui.modulos.DisplayModule;
import ids323.estudiantes.gui.modulos.ModuloEdicionEstudiante;
import ids323.estudiantes.gui.modulos.ModuloVistaEstudiante;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

/**
 * Representación de un estudiante como objeto.
 * */
public class Estudiante implements ModuleToken {

    private static final Image ICON = Commons.getIcon("estudiante");

    private String nombre;
    private String apellido;
    private Calendar fechaNacimiento;
    private Estado estado;
    public int id;
    private Carrera carrera;
    private Cedula cedula;
    private boolean esExtranjero = false;

    private boolean editando = false;

    public Estudiante(int id) {
        this.id = id;
    }

    public Estudiante(Registro registro, String nombre, String apellido, Calendar fechaNacimiento, Estado estado, Carrera carrera, Cedula cedula, boolean esExtranjero) {
        id = registro.ID_ESTUDIANTE++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.carrera = carrera;
        this.cedula = cedula;
        this.esExtranjero = esExtranjero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Cedula getCedula() {
        return cedula;
    }

    public void setCedula(Cedula cedula) {
        this.cedula = cedula;
    }

    public boolean isEsExtranjero() {
        return esExtranjero;
    }

    public void setExtranjero(boolean esExtranjero) {
        this.esExtranjero = esExtranjero;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    @Override
    public String getLabel() {
        return id + " - " + apellido.toUpperCase() + ", " + nombre.toUpperCase();
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    @Override
    public String getHint() {
        return carrera.getCodigo();
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
        return (editando) ? new ModuloEdicionEstudiante(this) : new ModuloVistaEstudiante(this);
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
