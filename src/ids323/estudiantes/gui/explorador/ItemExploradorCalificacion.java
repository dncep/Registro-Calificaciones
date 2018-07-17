package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Calificaciones;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.MarcaExplorador;
import ids323.estudiantes.gui.explorador.base.elementos.ElementoExplorador;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Representa un ítem dentro de un explorador de calificaciones que pertenece a un objeto {@link Calificaciones}.
 */
public class ItemExploradorCalificacion extends ElementoExplorador {
    private MasterExploradorCalificacion parent = null;

    private Calificaciones calif;

    private int x = 0;
    private int y = 0;
    private boolean closeRollover = false;

    public ItemExploradorCalificacion(MasterExploradorCalificacion parent, Calificaciones calif) {
        super(parent);
        this.parent = parent;
        this.calif = calif;
    }

    public void render(Graphics g) {
        g.setFont(master.getFont());
        int y = master.getOffsetY();
        this.y = y;
        master.getFlatList().add(this);

        int x = master.getInitialIndent();

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

        x += 23;

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

        String label = calif.getTrimestre().toString();

        g.drawString(label, x, master.getOffsetY() + metrics.getAscent() + ((master.getRowHeight() - metrics.getHeight())/2));

        x = master.getWidth();

        Image closeIcon = master.getAssetMap().get("close");

        int margin = ((master.getRowHeight() - closeIcon.getHeight(null)) / 2);
        x -= margin;
        x -= closeIcon.getWidth(null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, closeRollover ? 1f : 0.5f));
        g.drawImage(closeIcon, x, y + margin, null);
        x -= margin;

        g2d.setComposite(oldComposite);

        double indice = calif.getIndiceTrimestral();
        String labelIndice = indice >= 0 ? StringUtil.omitirDecimales(indice,2) : "-";
        x -= metrics.stringWidth(labelIndice);
        g.drawString(labelIndice, x, master.getOffsetY() + metrics.getAscent() + ((master.getRowHeight() - metrics.getHeight())/2));

        if(master.getFlag(MarcaExplorador.DEBUG_WIDTH)) {
            g.setColor(Color.YELLOW);
            g.fillRect(master.getContentWidth()-2, master.getOffsetY(), 2, master.getRowHeight());
            g.setColor(Color.GREEN);
            g.fillRect(x-2, master.getOffsetY(), 2, master.getRowHeight());
        }

        master.setOffsetY(master.getOffsetY() + master.getRowHeight());
        master.setContentWidth(Math.max(master.getContentWidth(), master.getWidth()));
        for(ElementoExplorador i : children) {
            i.render(g);
        }
    }

    public void open() {
        TabManager.openTab(calif);
    }

    @Override
    public TokenModulo getIdentifier() {
        return calif;
    }

    @Override
    public int getHeight() {
        return master.getRowHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(closeRollover) {
            int result = JOptionPane.showOptionDialog(Main.ventana.jframe, "¿Está seguro de que quiere borrar registro de calificaciones para el trimestre " + calif.getTrimestre() + "?", "Confirmación de acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(Calificaciones.ICON), new String[] {"Si", "No"}, "Si");
            if(result != JOptionPane.YES_OPTION) return;

            Main.registro.calificaciones.remove(calif);
            TabManager.closeTab(TabManager.getTabForToken(calif));
            master.refresh();
            master.repaint();
        } else if(e.getButton() == MouseEvent.BUTTON1 && !e.isControlDown() && e.getClickCount() % 2 == 0) {
            this.open();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            //x = indentation * master.getIndentPerLevel() + master.getInitialIndent();
            master.setSelected(this, e);
        } else if(e.isPopupTrigger()) {
            if(!this.selected) master.setSelected(this, new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), 0, e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger(), MouseEvent.BUTTON1));
            //JPopupMenu menu = calif.generarMenu();
            //if(menu != null) menu.show(e.getComponente(), e.getX(), e.getY());
        }
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
    public void mouseMoved(MouseEvent e) {
        int margin = ((master.getRowHeight() - 32) / 2);
        closeRollover = e.getX() >= master.getWidth()-margin-32 &&
                e.getX() < master.getWidth()-margin &&
                e.getY() >= y + margin &&
                e.getY() < y + margin + 32;
    }
}
