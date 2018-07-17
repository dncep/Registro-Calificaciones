package ids323.estudiantes.data;

import ids323.estudiantes.Main;
import ids323.estudiantes.Recursos;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.ModuloEdicionAsignatura;
import ids323.estudiantes.gui.modulos.vista.ModuloVistaAsignatura;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Representación de una asignatura como objeto.
 * */
public class Asignatura implements TokenModulo {

    /**
     * El ícono que representa la entidad Asignatura en el tamaño original.
     * */
    public static final Image ICON = Recursos.getIcono("asignatura");
    /**
     * El ícono que representa la entidad Asignatura, más un símbolo de agregar, en el tamaño original.
     * */
    public static final Image ICON_NUEVO = Recursos.getIcono("asignatura_nueva");

    /**
     * El identificador interno de la asignatura.
     * */
    private int id;
    /**
     * El área académica a la cual pertenece la asignaturra.
     * */
    private AreaAcademica area;
    /**
     * El código o la clave que representa la asignatura.
     * */
    private String codigo;
    /**
     * El nombre completo de la asignatura.
     * */
    private String nombre;
    /**
     * El profesor de esta asignatura.
     * */
    private Profesor profesor;
    /**
     * La cantidad de créditos de la asignatura.
     * */
    private int creditos;

    /**
     * Especifica si al abrir esta asignatura en una pestaña nueva, debe mostrar el módulo de edición o de vista.
     * */
    private boolean editando = false;

    /**
     * Crea un objeto estudiante con el ID especificado. Las demás propiedades deberán ser agregadas mediante los métodos set.<br>Será usado solo para cargar asignaturas de un archivo.
     *
     * @param id El identificador único de la asignatura.
     * */
    public Asignatura(int id) {
        this.id = id;
    }

    /**
     * Crea un objeto asignatura con las propiedades especificadas.<br>
     *     Llamar este constructor cambiará el campo {@link Registro#ID_ASIGNATURA} del {@link Main#registro}.
     *
     * @param area El área académica de la asignatura.
     * @param codigo El código o clave de la asignatura.
     * @param nombre El nombre de la asignatura.
     * @param profesor El profesor encargado de la asignatura.
     * @param creditos La cantidad de créditos que vale la asignatura.
     * */
    public Asignatura(AreaAcademica area, String codigo, String nombre, Profesor profesor, int creditos) {
        this.id = Main.registro.ID_ASIGNATURA++;
        this.area = area;
        this.codigo = codigo;
        this.nombre = nombre;
        this.profesor = profesor;
        this.creditos = creditos;
    }

    /**
     * Obtiene el identificador interno de la asignatura.
     *
     * @return El ID de la asignatura.
     * */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el área académica de la asignatura.
     *
     * @return El área académica de la asignatura.
     * */
    public AreaAcademica getArea() {
        return area;
    }

    /**
     * Cambia el área académica de la asignatura.
     *
     * @param area La nueva área académica de la asignatura.
     * */
    public void setArea(AreaAcademica area) {
        this.area = area;
    }

    /**
     * Obtiene el código o clave de la asignatura.
     *
     * @return El código de la asignatura.
     * */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Cambia el código o clave de la asignatura.
     *
     * @param codigo El nuevo código de la asignatura.
     * */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre de la asignatura.
     *
     * @return El nombre de la asignatura.
     * */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre de la asignatura.
     *
     * @param nombre El nuevo nombre de la asignatura.
     * */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el profesor encargado de la asignatura.
     *
     * @return El profesor de la asignatura.
     * */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Cambia el profesor de la asignatura.
     *
     * @param profesor El nuevo profesor de la asignatura.
     * */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * Obtiene el número de créditos de la asignatura.
     *
     * @return El número de créditos de la asignatura.
     * */
    public int getCreditos() {
        return creditos;
    }

    /**
     * Cambia el número de créditos de la asignatura.
     *
     * @param creditos El nuevo número de créditos de la asignatura.
     * */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    /**
     * Obtiene si esta asignatura está en condición de editar.
     *
     * @return <code>true</code> si está siendo editada, <code>false</code> si está siendo vista o no tiene una pestaña asociada.
     * */
    public boolean isEditando() {
        return editando;
    }

    /**
     * Cambia si esta asignatura debe estar siendo editada.
     *
     * @param editando <code>true</code> si debe ser editada, <code>false</code> si debe ser vista o no tiene una pestaña asociada.
     * */
    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    /**
     * Borra esta materia en {@link Main#registro} tras solicitar una confirmación al usuario. No se borran asignaturas presentes en calificaciones.
     * */
    public void borrar() {
        int result = JOptionPane.showOptionDialog(Main.ventana.jframe, "¿Está seguro de que quiere borrar " + this + "?", "Confirmación de acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(ICON), new String[] {"Si", "No"}, "Si");
        if(result != JOptionPane.YES_OPTION) return;

        for(Calificaciones calif : Main.registro.calificaciones) {
            if(calif.getCalificaciones().containsKey(this)) {
                JOptionPane.showMessageDialog(Main.ventana.jframe, "<html>No se puede borrar " + this + ":<br>El reporte de calificaciones de " + calif.getEstudiante() + " para trimestre " + calif.getTrimestre() + " contiene esta asignatura</html>", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(Calificaciones.ICON));
                return;
            }
        }

        TabManager.closeTab(TabManager.getTabForToken(this));
        Main.registro.asignaturas.remove(this);
        Main.ventana.exploradorRegistro.refresh();
    }

    /**
     * Crea una nueva asignatura con propiedades predefinidas, y abre una pestaña para editarla.
     * */
    public static Asignatura crearNueva() {
        if(Main.registro.profesores.isEmpty()) {
            JOptionPane.showMessageDialog(Main.ventana.jframe, "No existen profesores registrados para la nueva asignatura", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(Asignatura.ICON));
            return null;
        }
        Asignatura asig = new Asignatura(AreaAcademica.BASICAS, "Codigo", "Nombre", Main.registro.profesores.get(0), 1);
        Main.registro.asignaturas.add(asig);
        Main.ventana.exploradorRegistro.refresh();
        asig.setEditando(true);
        TabManager.openTab(asig);
        return asig;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }

    @Override
    public String getTitulo() {
        return codigo + " - " + nombre;
    }

    @Override
    public Image getIcono() {
        return ICON;
    }

    @Override
    public String getHint() {
        return creditos + " crédito" + ((creditos == 1) ? "" : "s") + " - " + area.getNombre();
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
        return (editando) ? new ModuloEdicionAsignatura(tab, this) : new ModuloVistaAsignatura(tab, this);
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
        return nombre + "\n" +
                codigo + "\n" +
                area.getNombre() + "\n" +
                creditos + " créditos" + "\n" +
                profesor.getInformacionBusqueda();
    }
}
