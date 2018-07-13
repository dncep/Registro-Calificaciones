package ids323.estudiantes.gui;

import ids323.estudiantes.gui.explorador.ItemExploradorRegistro;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public interface TokenModulo {
    String getTitulo();
    Image getIcono();
    String getHint();
    Collection<TokenModulo> getSubTokens();
    boolean isExpandible();
    ModuloPantalla crearModulo(Tab tab);

    void enInteraccion();

    JPopupMenu generarMenu(ItemExploradorRegistro explorerItem);

    default String getInformacionBusqueda() {
        return null;
    }
}
