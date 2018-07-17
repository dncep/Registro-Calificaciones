package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Calificaciones;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.data.Trimestre;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.elementos.ElementoExplorador;
import ids323.estudiantes.gui.modulos.TabManager;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Representa un ítem dentro de un explorador de calificaciones que pertenece a un {@link Estudiante} y crea un nuevo reporte de calificaciones.
 */
public class ItemNuevoExploradorCalificacion extends ElementoExplorador {

    private MasterExploradorCalificacion parent;
    private Estudiante estudiante;

    private boolean pressed = false;

    public ItemNuevoExploradorCalificacion(MasterExploradorCalificacion parent, Estudiante estudiante) {
        super(parent);
        this.parent = parent;
        this.estudiante = estudiante;
    }

    @Override
    public void render(Graphics g) {
        master.getFlatList().add(this);

        g.setColor(pressed ? master.getColorMap().get("item.seleccionado.fondo") : (this.rollover || this.selected) ? master.getColorMap().get("item.rollover.fondo") : master.getColorMap().get("item.fondo"));
        g.fillRect(0, master.getOffsetY(), master.getWidth(), this.getHeight());

        g.setColor(master.getColorMap().get("item.texto"));
        g.setFont(master.getFont().deriveFont(Font.BOLD));

        FontMetrics fm = g.getFontMetrics();
        String label = "+ Nuevo reporte de calificaciones";

        g.drawString(label, master.getWidth()/2 - fm.stringWidth(label)/2, master.getOffsetY() + ((this.getHeight()/2) + (fm.getAscent()/2)) - 2);

        master.setOffsetY(master.getOffsetY() + getHeight());
    }

    @Override
    public TokenModulo getIdentifier() {
        return null;
    }

    @Override
    public int getHeight() {
        return master.getRowHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Trimestre trimestre = Trimestre.getTrimestreCercano();

        while(true) {
            boolean valido = true;
            for(Calificaciones calif : estudiante.getAllCalificaciones()) {
                if(calif.getTrimestre().equals(trimestre)) {
                    trimestre = trimestre.getProximo();
                    valido = false;
                    break;
                }
            }
            if(valido) break;
        }

        Calificaciones calif = new Calificaciones(trimestre, estudiante);
        Main.registro.calificaciones.add(calif);
        TabManager.openTab(calif);

        master.refresh();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        master.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        master.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        master.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        master.setCursor(Cursor.getDefaultCursor());
        pressed = false;
    }
}
