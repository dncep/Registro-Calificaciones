package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.MarcaExplorador;
import ids323.estudiantes.gui.explorador.base.MasterExplorador;
import ids323.estudiantes.gui.explorador.base.elementos.ElementoExplorador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Representa un ítem dentro de un explorador que pertenece a un {@link TokenModulo}.
 */
public class ItemExploradorRegistro extends ElementoExplorador {
    private ItemExploradorRegistro parent = null;

    private TokenModulo token = null;
    private int indentation = 0;

    private boolean expanded = false;

    private Image icon = null;

    private int x = 0;

    ItemExploradorRegistro(MasterExplorador master, TokenModulo token, ArrayList<TokenModulo> toOpen) {
        super(master);
        this.token = token;

        if(toOpen.contains(this.token)) {
            expand(toOpen);
        }

        this.icon = token.getIcono();
        if(this.icon != null) this.icon = this.icon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    }

    private ItemExploradorRegistro(ItemExploradorRegistro parent, TokenModulo token, ArrayList<TokenModulo> toOpen) {
        super(parent.getMaster());
        this.parent = parent;

        this.token = token;
        this.indentation = parent.indentation + 1;
        this.x = indentation * master.getIndentPerLevel() + master.getInitialIndent();

        if(toOpen.contains(this.token)) {
            expand(toOpen);
        }

        this.icon = token.getIcono();
        if(this.icon != null) this.icon = this.icon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    }

    private void expand(ArrayList<TokenModulo> toOpen) {
        for(TokenModulo subToken : token.getSubTokens()) {
            this.children.add(new ItemExploradorRegistro(this, subToken, toOpen));
        }
        expanded = true;
        master.getExpandedElements().add(this.token);
        master.repaint();
    }

    private void collapse() {
        this.propagateCollapse();
        this.children.clear();
        expanded = false;
        master.repaint();
    }

    private void propagateCollapse() {
        master.getExpandedElements().remove(this.token);
        for(ElementoExplorador element : children) {
            if(element instanceof ItemExploradorRegistro) ((ItemExploradorRegistro) element).propagateCollapse();
        }
    }

    public void render(Graphics g) {
        g.setFont(master.getFont());
        int y = master.getOffsetY();
        master.getFlatList().add(this);

        int x = (indentation * master.getIndentPerLevel()) + master.getInitialIndent();

        g.setColor((this.rollover || this.selected) ? master.getColorMap().get("item.rollover.fondo") : master.getColorMap().get("item.fondo"));
        g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getRowHeight());
        if(this.selected) {
            g.setColor(master.getColorMap().get("item.seleccionado.fondo"));

            switch(master.getSelectionStyle()) {
                case "FULL": {
                    g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getRowHeight());
                    break;
                }
                case "LINE_LEFT": {
                    g.fillRect(0, master.getOffsetY(), master.getSelectionLineThickness(), master.getRowHeight());
                    break;
                }
                case "LINE_RIGHT": {
                    g.fillRect(master.getWidth() - master.getSelectionLineThickness(), master.getOffsetY(), master.getSelectionLineThickness(), master.getRowHeight());
                    break;
                }
                case "LINE_TOP": {
                    g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getSelectionLineThickness());
                    break;
                }
                case "LINE_BOTTOM": {
                    g.fillRect(0, master.getOffsetY() + master.getRowHeight() - master.getSelectionLineThickness(), master.getWidth(), master.getSelectionLineThickness());
                    break;
                }
            }
        }

        //Expand/Collapse button
        if(token.isExpandible()){
            int margin = ((master.getRowHeight() - 16) / 2);
            if(expanded) {
                g.drawImage(master.getAssetMap().get("collapse"),x,y + margin,16, 16,new Color(0,0,0,0),null);
            } else {
                g.drawImage(master.getAssetMap().get("expand"),x,y + margin,16, 16,new Color(0,0,0,0),null);
            }
        }
        x += 23;

        //File Icon
        if(this.icon != null) {
            int margin = ((master.getRowHeight() - 16) / 2);
            g.drawImage(this.icon,x + 8 - icon.getWidth(null)/2,y + margin + 8 - icon.getHeight(null)/2, null);
            x += 25;
        }

        //File Name

        if(this.selected) {
            g.setColor(master.getColorMap().get("item.seleccionado.texto"));
        } else if(this.rollover) {
            g.setColor(master.getColorMap().get("item.rollover.texto"));
        } else {
            g.setColor(master.getColorMap().get("item.texto"));
        }
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        Graphics2D g2d = (Graphics2D) g;
        Composite oldComposite = g2d.getComposite();

        g.drawString(token.getTitulo(), x, master.getOffsetY() + metrics.getAscent() + ((master.getRowHeight() - metrics.getHeight())/2));
        x += metrics.stringWidth(token.getTitulo());

        g2d.setComposite(oldComposite);

        if(master.getFlag(MarcaExplorador.DEBUG_WIDTH)) {
            g.setColor(Color.YELLOW);
            g.fillRect(master.getContentWidth()-2, master.getOffsetY(), 2, master.getRowHeight());
            g.setColor(Color.GREEN);
            g.fillRect(x-2, master.getOffsetY(), 2, master.getRowHeight());
        }

        master.setOffsetY(master.getOffsetY() + master.getRowHeight());
        master.setContentWidth(Math.max(master.getContentWidth(), x));
        for(ElementoExplorador i : children) {
            i.render(g);
        }
    }

    private void open() {
        this.token.enInteraccion();
        if(token.isExpandible()) {
            if(expanded) collapse();
            else expand(new ArrayList<>());
        }
    }

    @Override
    public int getHeight() {
        return master.getRowHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && !e.isControlDown() && e.getClickCount() % 2 == 0 && (e.getX() < x || e.getX() > x + master.getRowHeight())) {
            this.open();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            //x = indentation * master.getIndentPerLevel() + master.getInitialIndent();
            if(token.isExpandible() && e.getX() >= x && e.getX() <= x + 20) {
                if(expanded) collapse();
                else expand(new ArrayList<>());
            } else {
                master.setSelected(this, e);
            }
        }
        confirmarActivacionMenu(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        confirmarActivacionMenu(e);
    }

    private void confirmarActivacionMenu(MouseEvent e) {
        if(e.isPopupTrigger()) {
            if(!this.selected) master.setSelected(this, new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), 0, e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger(), MouseEvent.BUTTON1));
            JPopupMenu menu = token.generarMenu();
            if(menu != null) menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public TokenModulo getIdentifier() {
        return token;
    }
}
