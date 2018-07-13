package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Calificaciones;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.MasterExplorador;
import ids323.estudiantes.util.Comunes;

import java.awt.*;
import java.util.ArrayList;

public class MasterExploradorCalificacion extends MasterExplorador {

    Estudiante estudiante;

    public MasterExploradorCalificacion(Estudiante estudiante) {
        this.estudiante = estudiante;

        colors.put("fondo", Colores.PRIMARIO_MAS_OSCURO);
        colors.put("item.fondo", Colores.PRIMARIO_MAS_OSCURO);
        colors.put("item.texto", Colores.TEXTO);
        colors.put("item.seleccionado.fondo", Colores.ENFASIS);
        colors.put("item.seleccionado.texto", Colores.TEXTO);
        colors.put("item.rollover.fondo", Colores.PRIMARIO_OSCURO);
        colors.put("item.rollover.texto", Colores.TEXTO);

        rowHeight = 50;
        indentPerLevel = 20;
        initialIndent = 5;

        selectionStyle = "LINE_LEFT";
        selectionLineThickness = 4;

        assets.put("expand", Comunes.getIcono("expand").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        assets.put("collapse", Comunes.getIcono("collapse").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        assets.put("close", Comunes.getIcono("remove").getScaledInstance(24, 24, Image.SCALE_SMOOTH));

        refresh();
    }

    @Override
    public void refresh() {
        clearSelected();
        refresh(new ArrayList<>());
    }

    private void refresh(ArrayList<TokenModulo> toOpen) {
        children.clear();
        flatList.clear();

        for(Calificaciones calif : Main.registro.calificaciones) {
            if(calif.getEstudiante() == estudiante) {
                this.children.add(new ItemExploradorCalificacion(this, calif));
            }
        }

        this.children.add(new ItemNuevoExploradorCalificacion(this, estudiante));

        repaint();
    }
}
