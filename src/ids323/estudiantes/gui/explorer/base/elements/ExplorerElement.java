package ids323.estudiantes.gui.explorer.base.elements;

import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.explorer.base.ExplorerMaster;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by User on 4/8/2017.
 */
public abstract class ExplorerElement implements MouseListener {
    protected final ExplorerMaster master;
    protected boolean selected;
    protected boolean rollover;
    protected boolean expanded;

    public ExplorerElement(ExplorerMaster master) {
        this.master = master;
    }

    protected ArrayList<ExplorerElement> children = new ArrayList<>();

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

    public abstract ModuleToken getIdentifier();

    public abstract int getHeight();

    public ExplorerMaster getMaster() {
        return master;
    }
}
