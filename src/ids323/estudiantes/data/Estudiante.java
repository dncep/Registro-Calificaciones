package ids323.estudiantes.data;

import ids323.estudiantes.Main;
import ids323.estudiantes.Recursos;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.ModuloEdicionEstudiante;
import ids323.estudiantes.gui.modulos.vista.ModuloVistaEstudiante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

/**
 * Representación de un estudiante como objeto.
 * */
public class Estudiante implements TokenModulo {

    /**
     * El ícono que representa la entidad Estudiante en el tamaño original.
     * */
    public static final Image ICON = Recursos.getIcono("estudiante");
    /**
     * El ícono que representa la entidad Estudiante, más un símbolo de agregar, en el tamaño original.
     * */
    public static final Image ICON_NUEVO = Recursos.getIcono("estudiante_nuevo");

    /**
     * El nombre del estudiante.
     * */
    private String nombre;
    /**
     * El apellido del estudiante.
     * */
    private String apellido;
    /**
     * La fecha de nacimiento del estudiante.
     * */
    private Calendar fechaNacimiento;
    /**
     * El estado o condición académica del estudiante.
     * */
    private Estado estado;
    /**
     * El ID o matrícula del estudiante.
     * */
    private int id;
    /**
     * La carrera que cursa el estudiante.
     * */
    private Carrera carrera;
    /**
     * Si el estudiante es extranjero o no.
     * */
    private boolean esExtranjero = false;

    /**
     * Especifica si al abrir este estudiante en una pestaña nueva, debe mostrar el módulo de edición o de vista.
     * */
    private boolean editando = false;

    /**
     * Crea un objeto estudiante con el ID especificado. Las demás propiedades deberán ser agregadas mediante los métodos set.<br>Será usado solo para cargar estudiantes de un archivo.
     *
     * @param id El ID o matrícula del estudiante.
     * */
    public Estudiante(int id) {
        this.id = id;
    }

    /**
     * Crea un objeto estudiante con las propiedades especificadas.<br>
     *     Llamar este constructor cambiará el campo {@link Registro#ID_ESTUDIANTE} del {@link Main#registro}.
     *
     *
     * @param nombre El nombre del estudiante.
     * @param apellido El apellido del estudiante.
     * @param fechaNacimiento La fecha de nacimiento del estudiante.
     * @param estado El estado o condición académica del estudiante.
     * @param carrera La carrera que cursa el estudiante.
     * @param esExtranjero Si el estudiante es extranjero o no.
     * */
    public Estudiante(String nombre, String apellido, Calendar fechaNacimiento, Estado estado, Carrera carrera, boolean esExtranjero) {
        id = Main.registro.ID_ESTUDIANTE++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.carrera = carrera;
        this.esExtranjero = esExtranjero;
    }

    /**
     * Devuelve una colección de las calificaciones que pertenecen a este estudiante dentro de {@link Main#registro}.
     *
     * @return Las calificaciones pertenecientes a este estudiante.
     * */
    public Collection<Calificaciones> getAllCalificaciones() {
        ArrayList<Calificaciones> lista = new ArrayList<>();
        for(Calificaciones calif : Main.registro.calificaciones) {
            if(calif.getEstudiante() == this) lista.add(calif);
        }
        return lista;
    }

    /**
     * Calcula el índice general del estudiante basado en todas sus calificaciones en {@link Main#registro}.
     *
     * @return El índice general del estudiante. Si no tiene calificaciones, devuelve -1.
     * */
    public double getIndiceGeneral() {
        Collection<Calificaciones> califs = getAllCalificaciones();
        if(califs.isEmpty()) return -1;
        double puntos = 0;
        double creditos = 0;
        for(Calificaciones calif : califs) {
            puntos += calif.getPuntosDeHonor();
            creditos += calif.getCreditos();
        }
        return creditos != 0 ? puntos / creditos : -1;
    }

    /**
     * Calcula los honores que recibiría el estudiante basado en su índice general actual (según las calificaciones en {@link Main#registro}).
     *
     * @return <ul><li>"Summa Cum Laude" para índice >= 3.8</li>
     *         <li>"Magna Cum Laude" para índice >= 3.5 y menor que 3.8</li>
     *         <li>"Cum Laude" para índice >= 3.2 y menor que 3.5</li>
     *         <li>"-" si no tiene calificaciones registradas</li></ul>
     * */
    public String getHonores() {
        double indice = getIndiceGeneral();
        if(indice == -1) return "-";
        if(indice >= 3.8) return "Summa Cum Laude";
        if(indice >= 3.5) return "Magna Cum Laude";
        return indice >= 3.2 ? "Cum Laude" : "Sin honor";
    }

    /**
     * Obtiene el nombre del estudiante.
     *
     * @return El nombre del estudiante.
     * */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del estudiante.
     *
     * @param nombre El nombre nuevo del estudiante.
     * */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del estudiante.
     *
     * @return El apellido del estudiante.
     * */
    public String getApellido() {
        return apellido;
    }

    /**
     * Cambia el apellido del estudiante.
     *
     * @param apellido El apellido nuevo del estudiante.
     * */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene la fecha de nacimiento del estudiante.
     *
     * @return La fecha de nacimiento del estudiante.
     * */
    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Cambia la fecha de nacimiento del estudiante.
     *
     * @param fechaNacimiento La fecha de nacimiento neuva del estudiante.
     * */
    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el estado o condición académica del estudiante.
     *
     * @return El estado del estudiante.
     * */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Cambia el estado o condición académica del estudiante.
     *
     * @param estado El estado nuevo del estudiante.
     * */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la matrícula del estudiante.
     *
     * @return La matrícula del estudiante.
     * */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la carrera del estudiante.
     *
     * @return La carrera del estudiante.
     * */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * Cambia la carrera del estudiante.
     *
     * @param carrera La carrera nueva del estudiante.
     * */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    /**
     * Obtiene si el estudiante es extranjero.
     *
     * @return <code>false</code> si el estudiante es dominicano, <code>true</code> si no lo es.
     * */
    public boolean isExtranjero() {
        return esExtranjero;
    }

    /**
     * Cambia si el estudiante es extranjero.
     *
     * @param esExtranjero <code>false</code> si el estudiante es dominicano, <code>true</code> si no lo es.
     * */
    public void setExtranjero(boolean esExtranjero) {
        this.esExtranjero = esExtranjero;
    }

    /**
     * Obtiene si este estudiante está en condición de editar.
     *
     * @return <code>true</code> si está siendo editado, <code>false</code> si está siendo visto o no tiene una pestaña asociada.
     * */
    public boolean isEditando() {
        return editando;
    }

    /**
     * Cambia si este estudiante debe estar siendo editado.
     *
     * @param editando <code>true</code> si debe ser editado, <code>false</code> si debe ser visto o no tiene una pestaña asociada.
     * */
    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    /**
     * Borra este estudiante y sus calificaciones en {@link Main#registro} tras solicitar una confirmación al usuario.
     * */
    public void borrar() {
        int result = JOptionPane.showOptionDialog(Main.ventana.jframe, "¿Está seguro de que quiere borrar " + this + " y sus calificaciones?", "Confirmación de acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(ICON), new String[] {"Si", "No"}, "Si");
        if(result != JOptionPane.YES_OPTION) return;

        TabManager.closeTab(TabManager.getTabForToken(this));
        Main.registro.estudiantes.remove(this);
        Main.registro.calificaciones.removeAll(getAllCalificaciones());
        Main.ventana.exploradorRegistro.refresh();
    }

    /**
     * Crea un nuevo estudiante con propiedades predefinidas, y abre una pestaña para editarlo.
     * */
    public static Estudiante crearNuevo() {
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.set(Calendar.YEAR, fechaNacimiento.get(Calendar.YEAR)-18);

        Estudiante est = new Estudiante("Nombre", "Apellido", fechaNacimiento, Estado.EN_PROCESO, Carrera.AGN, false);
        Main.registro.estudiantes.add(est);
        Main.ventana.exploradorRegistro.refresh();
        est.setEditando(true);
        TabManager.openTab(est);

        return est;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

    @Override
    public String getTitulo() {
        return id + " - " + apellido + ", " + nombre;
    }

    @Override
    public Image getIcono() {
        return ICON;
    }

    @Override
    public String getHint() {
        return carrera.getCodigo();
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
        return (editando) ? new ModuloEdicionEstudiante(tab, this) : new ModuloVistaEstudiante(tab, this);
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
        return nombre + " " + apellido + "\n" +
                id + "\n" +
                estado + "\n" +
                carrera.getCodigo() + "\n" +
                carrera.getNombre() + "\n" +
                ((esExtranjero) ? "Extranjero\nExtranjera" : "Dominicano\nDominicana") +
                (getHonores().length()>1 ? "\n" + getHonores() : "");
    }
}
