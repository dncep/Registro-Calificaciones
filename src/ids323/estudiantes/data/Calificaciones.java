package ids323.estudiantes.data;

import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.ModuloCalificaciones;
import ids323.estudiantes.util.Comunes;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Calificaciones implements TokenModulo {

    public static final Image ICON = Comunes.getIcono("calificaciones");

    private Trimestre trimestre;
    private Estudiante estudiante;
    private HashMap<Asignatura, Integer> calificaciones = new HashMap<>();

    public Calificaciones() {
    }

    public Calificaciones(Trimestre trimestre, Estudiante estudiante) {
        this.trimestre = trimestre;
        this.estudiante = estudiante;
    }

    private String getLetra(int val) {
        if(val == -1) return "R";
        if(val == -2) return "I";
        if(val < 60) return "F";
        if(val < 70) return "D";
        if(val < 80) return "C" + ((val >= 75) ? "+" : "");
        if(val < 90) return "B" + ((val >= 85) ? "+" : "");
        return "A";
    }

    private double valorLetra(String letra) {
        return "FDCBA".indexOf(letra.charAt(0)) + (letra.endsWith("+") ? 0.5 : 0);
    }

    public double getIndiceTrimestral() {
        double puntos = getPuntosDeHonor();
        double creditos = getCreditos();
        return creditos != 0 ? puntos / creditos : -1;
    }

    public Trimestre getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public HashMap<Asignatura, Integer> getCalificaciones() {
        return calificaciones;
    }

    public int getPuntosDeHonor() {
        int puntos = 0;

        for(Map.Entry<Asignatura, Integer> nota : calificaciones.entrySet()) {
            String letra = getLetra(nota.getValue());
            double valorLetra = valorLetra(letra);
            if(valorLetra >= 0) {
                int creditos = nota.getKey().getCreditos();
                puntos += valorLetra*creditos;
            }
        }

        return puntos;
    }

    public int getCreditos() {
        int creditos = 0;
        for(Asignatura asig : calificaciones.keySet()) {
            creditos += asig.getCreditos();
        }
        return creditos;
    }

    @Override
    public String getTitulo() {
        return estudiante.getId() + " | " + trimestre;
    }

    @Override
    public Image getIcono() {
        return ICON;
    }

    @Override
    public String getHint() {
        return "Calificaciones de estudiante " + estudiante.getNombre() + " " + estudiante.getApellido() + " para trimestre " + trimestre;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return Collections.emptyList();
    }

    @Override
    public boolean isExpandible() {
        return false;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return new ModuloCalificaciones(tab, this);
    }

    @Override
    public void enInteraccion() {
        TabManager.openTab(this);
    }

    @Override
    public JPopupMenu generarMenu() {
        return null;
    }
}
