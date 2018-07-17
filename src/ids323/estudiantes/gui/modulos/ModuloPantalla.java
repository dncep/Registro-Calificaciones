package ids323.estudiantes.gui.modulos;

/**
 * Representa un componente que puede ser mostrado al abrir una pestaña.
 * */
public interface ModuloPantalla {
    /**
     * Retorna el valor que contiene el módulo; se utiliza para identificar cambios en su contenido.
     * */
    Object getValor();
    /**
     * Retorna <code>true</code> si el módulo es editable y puede guardar su contenido.
     * */
    boolean puedeGuardar();
    /**
     * Guarda el contenido del módulo
     * @return El valor que contiene el módulo guardado.
     * */
    Object guardar();
    /**
     * Se llama cada vez que la pestaña relacionada a este módulo se selecciona.
     * */
    void enfocar();
}
