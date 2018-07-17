package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Recursos;
import ids323.estudiantes.gui.TokenModulo;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class TokenRanking implements TokenModulo {

    public static final Image ICON = Recursos.getIcono("calificaciones");

    @Override
    public String getTitulo() {
        return "Ranking";
    }

    @Override
    public Image getIcono() {
        return ICON;
    }

    @Override
    public String getHint() {
        return "Ranking de índice académico";
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return null;
    }

    @Override
    public boolean isExpandible() {
        return false;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return new ModuloRanking();
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu() {
        return null;
    }
}
