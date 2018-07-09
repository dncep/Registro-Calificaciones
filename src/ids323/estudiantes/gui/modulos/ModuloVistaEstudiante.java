package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.gui.InfoPanel;
import ids323.estudiantes.util.Padding;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;

public class ModuloVistaEstudiante extends ModuloVista {

    private Estudiante estudiante;

    public ModuloVistaEstudiante(Estudiante estudiante) {
        super(estudiante.getNombre() + " " + estudiante.getApellido(), estudiante.id + "");
        this.estudiante = estudiante;

        infoPanel.put("CARRERA", estudiante.getCarrera().getNombre());
        infoPanel.put("CONDICIÓN", estudiante.getEstado().toString());

        infoPanel.put("FECHA DE NACIMIENTO",
        estudiante.getFechaNacimiento().get(Calendar.DAY_OF_MONTH) +
                " de " +
                "enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre,octubre,noviembre,diciembre".split(",")[estudiante.getFechaNacimiento().get(Calendar.MONTH)] +
                " " +
                estudiante.getFechaNacimiento().get(Calendar.YEAR));

        infoPanel.put("NACIONALIDAD", estudiante.isEsExtranjero() ? "Extranjero/a" : "Dominicano/a");

        construir();
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean canSave() {
        return false;
    }

    @Override
    public Object save() {
        return null;
    }

    @Override
    public void focus() {

    }
}
