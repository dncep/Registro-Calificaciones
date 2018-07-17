package ids323.estudiantes.gui.modulos.edicion.logica;

import javax.swing.*;

/**
 * Representa el componente de entrada para un valor a editar.
 * */
public interface EntradaValor {
    /**
     * Solicita el componente que representa la entrada de este valor.
     * */
    JComponent getComponente();
    /**
     * Comprueba que el valor dado por el usuario es válido.
     *
     * @return <code>null</code> si no hay errores en la entrada, un <code>String</code> si hay un error, detallando el
     * problema.
     * */
    String validarEntrada();
    /**
     * Hace que el valor en la entrada se pase al objeto que se está editando.
     * */
    void guardarEntrada();
    /**
     * Obtiene el objeto {@link ValorEdicion} que describe cómo esta entrada debe manejar un dato.
     * */
    ValorEdicion<?> getValorEdicion();

    /**
     * Obtiene un <code>hashCode</code> del valor actualmente presente en el campo de edición. Se utiliza para comparar
     * datos actuales con los datos guardados.
     * */
    int getCodigoValor();
}
