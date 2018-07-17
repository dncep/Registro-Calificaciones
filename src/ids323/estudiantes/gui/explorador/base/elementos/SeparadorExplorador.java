package ids323.estudiantes.gui.explorador.base.elementos;

import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.MasterExplorador;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Representa un separador dentro de un explorador.
 */
public class SeparadorExplorador extends ElementoExplorador {

    public SeparadorExplorador(MasterExplorador master) {
        super(master);
    }

    @Override
    public void render(Graphics g) {
        master.getFlatList().add(this);

        g.setColor((this.rollover || this.selected) ? master.getColorMap().get("item.rollover.fondo") : master.getColorMap().get("item.fondo"));
        g.fillRect(0, master.getOffsetY(), master.getWidth(), this.getHeight());

        g.setColor(master.getColorMap().get("item.texto"));
        g.fillRect(master.getWidth() / 10, master.getOffsetY() + ((this.getHeight() / 2) - 1), 8 * (master.getWidth() / 10), 2);

        master.setOffsetY(master.getOffsetY() + getHeight());
    }

    @Override
    public int getHeight() {
        return master.getRowHeight();
    }

    @Override
    public TokenModulo getIdentifier() {
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
