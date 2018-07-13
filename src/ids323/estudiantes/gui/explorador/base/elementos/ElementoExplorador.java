package ids323.estudiantes.gui.explorador.base.elementos;

import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.MasterExplorador;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by User on 4/8/2017.
 */
public abstract class ElementoExplorador implements MouseListener, MouseMotionListener {
    protected final MasterExplorador master;
    protected boolean selected;
    protected boolean rollover;
    protected boolean expanded;

    public ElementoExplorador(MasterExplorador master) {
        this.master = master;
    }

    protected ArrayList<ElementoExplorador> children = new ArrayList<>();

    public abstract void render(Graphics g);

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isRollover() {
        return rollover;
    }

    public void setRollover(boolean rollover) {
        this.rollover = rollover;
    }

    public abstract TokenModulo getIdentifier();

    public abstract int getHeight();

    public MasterExplorador getMaster() {
        return master;
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
