package ids323.estudiantes.gui;

import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Representa una entidad que puede ser mostrada tanto en un explorador como en una pestaña.
 * */
public interface TokenModulo {
    /**
     * Obtiene el título que será mostrado en el explorador y en la lista de pestañas para esta entidad.
     *
     * @return El título de esta entidad.
     * */
    String getTitulo();
    /**
     * Obtiene el ícono que será mostrado junto al título en el explorador y en la lista de pestañas para esta entidad.
     *
     * @return El ícono para esta entidad. Puede ser <code>null</code>.
     * */
    Image getIcono();
    /**
     * Obtiene el "tooltip" que será mostrado al mover el cursor sobre la pestaña para esta entidad en la lista de pestañas.
     *
     * @return El "tooltip" de esta entidad.
     * */
    String getHint();
    /**
     * Obtiene la lista de entidades que serán mostradas al expandir esta entidad en un explorador.
     *
     * @return La lista de sub-entidades.
     * */
    Collection<TokenModulo> getSubTokens();
    /**
     * Obtiene si esta entidad puede expandirse para mostrar sub-entidades.
     *
     * @return <code>true</code> si se puede expandir, <code>false</code> si no se puede expandir.
     * */
    boolean isExpandible();
    /**
     * Crea una pantalla para esta entidad, dado un objeto pestaña.
     *
     * @param tab La pestaña que está solicitando esta ventana.
     *
     * @return El componente pantalla para la entidad.
     * */
    ModuloPantalla crearModulo(Tab tab);

    /**
     * Se ejecuta cuando se hace doble-click al elemento que representa esta entidad en el explorador.
     * */
    void enInteraccion();

    /**
     * Genera un menú de acciones que se muestra cuando se hace click-derecho a la entidad en el explorador.
     *
     * @return El menú a mostrar. Puede ser <code>null</code>.
     * */
    JPopupMenu generarMenu();

    /**
     * Devuelve toda la información que la entidad considere que pueda ser encontrada mediante una búsqueda.
     *
     * @return La información que la entidad considere apta para búsqueda, en líneas distintas. Puede ser <code>null</code>.
     * */
    default String getInformacionBusqueda() {
        return null;
    }
}
