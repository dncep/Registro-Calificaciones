package ids323.estudiantes.data;

import ids323.estudiantes.Main;
import ids323.estudiantes.Recursos;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.ModuloEdicionProfesor;
import ids323.estudiantes.gui.modulos.vista.ModuloVistaProfesor;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Representación de un profesor como objeto.
 * */
public class Profesor implements TokenModulo {

    /**
     * El ícono que representa la entidad Profesor en el tamaño original.
     * */
    public static final Image ICON = Recursos.getIcono("profesor");
    /**
     * El ícono que representa la entidad Profesor, más un símbolo de agregar, en el tamaño original.
     * */
    public static final Image ICON_NUEVO = Recursos.getIcono("profesor_nuevo");

    /**
     * El identificador interno del profesor.
     * */
    private int id;
    /**
     * El nombre del profesor.
     * */
    private String nombre;
    /**
     * El apellido del profesor.
     * */
    private String apellido;

    /**
     * Especifica si al abrir este profesor en una pestaña nueva, debe mostrar el módulo de edición o de vista.
     * */
    private boolean editando = false;

    /**
     * Crea un objeto profesor con el ID especificado. Las demás propiedades deberán ser agregadas mediante los métodos set.<br>Será usado solo para cargar profesores de un archivo.
     *
     * @param id El identificador interno del profesor.
     * */
    public Profesor(int id) {
        this.id = id;
    }

    /**
     * Crea un objeto profesor con las propiedades especificadas.<br>
     *     Llamar este constructor cambiará el campo {@link Registro#ID_PROFESOR} del {@link Main#registro}.
     *
     *
     * @param nombre El nombre del profesor.
     * @param apellido El apellido del profesor.
     * */
    public Profesor(String nombre, String apellido) {
        id = Main.registro.ID_PROFESOR++;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Obtiene el nombre del profesor.
     *
     * @return El nombre del profesor.
     * */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del profesor.
     *
     * @param nombre El nombre nuevo del profesor.
     * */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del profesor.
     *
     * @return El apellido del profesor.
     * */
    public String getApellido() {
        return apellido;
    }

    /**
     * Cambia el apellido del profesor.
     *
     * @param apellido El apellido nuevo del profesor.
     * */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el identificador interno del profesor.
     *
     * @return El ID del profesor.
     * */
    public int getId() {
        return id;
    }

    /**
     * Obtiene si este profesor está en condición de editar.
     *
     * @return <code>true</code> si está siendo editado, <code>false</code> si está siendo visto o no tiene una pestaña asociada.
     * */
    public boolean isEditando() {
        return editando;
    }

    /**
     * Cambia si este profesor debe estar siendo editado.
     *
     * @param editando <code>true</code> si debe ser editado, <code>false</code> si debe ser visto o no tiene una pestaña asociada.
     * */
    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    /**
     * Borra este profesor en {@link Main#registro} tras solicitar una confirmación al usuario. No se borran profesores encargados de asignaturas presentes en el registro.
     * */
    public void borrar() {
        int result = JOptionPane.showOptionDialog(Main.ventana.jframe, "¿Está seguro de que quiere borrar " + this + "?", "Confirmación de acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(ICON), new String[] {"Si", "No"}, "Si");
        if(result != JOptionPane.YES_OPTION) return;

        for(Asignatura asig : Main.registro.asignaturas) {
            if(asig.getProfesor() == this) {
                JOptionPane.showMessageDialog(Main.ventana.jframe, "<html>No se puede borrar " + this + ":<br>La asignatura " + asig + " está registrada con su nombre</html>", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(Asignatura.ICON));
                return;
            }
        }

        TabManager.closeTab(TabManager.getTabForToken(this));
        Main.registro.profesores.remove(this);
        Main.ventana.exploradorRegistro.refresh();
    }

    /**
     * Crea un nuevo profesor con propiedades predefinidas, y abre una pestaña para editarlo.
     * */
    public static Profesor crearNuevo() {
        Profesor prof = new Profesor("Nombre", "Apellido");

        Main.registro.profesores.add(prof);
        Main.ventana.exploradorRegistro.refresh();
        prof.setEditando(true);
        TabManager.openTab(prof);

        return prof;
    }

    @Override
    public String toString() {
        return "Prof. " + nombre + " " + apellido;
    }

    @Override
    public String getTitulo() {
        return "P" + id + " - " + apellido + ", " + nombre;
    }

    @Override
    public Image getIcono() {
        return ICON;
    }

    @Override
    public String getHint() {
        return "Prof. " + apellido;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return Collections.emptyList();
    }

    @Override
    public boolean isExpandible() {
        return false;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return editando ? new ModuloEdicionProfesor(tab, this) : new ModuloVistaProfesor(tab, this);
    }

    @Override
    public void enInteraccion() {
        editando = editando && TabManager.getTabForToken(this) != null;
        TabManager.openTab(this);
    }

    @Override
    public JPopupMenu generarMenu() {
        JPopupMenu menu = new JPopupMenu();

        {
            JMenuItem item = new JMenuItem("Ver");

            item.addActionListener(e -> enInteraccion());

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
        menu.addSeparator();
        {
            JMenuItem item = new JMenuItem("Borrar");

            item.addActionListener(e -> borrar());

            menu.add(item);
        }
        return menu;
    }

    @Override
    public String getInformacionBusqueda() {
        return "Prof. " + nombre + " " + apellido + "\n" +
                "P" + id;
    }
}
