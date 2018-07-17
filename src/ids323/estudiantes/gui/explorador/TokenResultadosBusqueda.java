package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * La entidad que representa los resultados de búsqueda.
 * */
public class TokenResultadosBusqueda implements TokenModulo {

    @Override
    public String getTitulo() {
        return "RESULTADOS DE LA BÚSQUEDA";
    }

    @Override
    public Image getIcono() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return Main.ventana.exploradorRegistro.getSearchResults();
    }

    @Override
    public boolean isExpandible() {
        return true;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return null;
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu() {
        return null;
    }
}
