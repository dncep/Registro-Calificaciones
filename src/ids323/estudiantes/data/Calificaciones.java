package ids323.estudiantes.data;

import ids323.estudiantes.Recursos;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.ModuloCalificaciones;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa las calificaciones de un estudiante a lo largo de un trimestre.
 * */
public class Calificaciones implements TokenModulo {

    /**
     * Constante que especifica si las calificaciones entre 75 y 80, y entre 85 y 90 reciben calificaciones C+ y B+
     * respectivamente, y si estas diferencias de letra cuentan para el cálculo del índice trimestral.
     * */
    private static final boolean USAR_LETRAS_MAS = false;

    /**
     * El ícono que representa la entidad Calificaciones en el tamaño original.
     * */
    public static final Image ICON = Recursos.getIcono("calificaciones");

    /**
     * El trimestre al que pertenece este reporte de calificaciones.
     * */
    private Trimestre trimestre;
    /**
     * El estudiante al que pertenece este reporte de calificaciones.
     * */
    private Estudiante estudiante;
    /**
     * El mapa detallando relaciones entre asignaturas y su respectiva calificación numérica.
     * */
    private HashMap<Asignatura, Integer> calificaciones = new HashMap<>();

    /**
     * Crea un objeto calificaciones vacío. Sera usado solo para crear reportes de calificaciones de un archivo.
     * */
    public Calificaciones() {
    }

    /**
     * Crea un objeto calificaciones vacío pero con trimestre y estudiante dados.
     *
     * @param trimestre El trimestre al que pertenece el reporte de calificaciones.
     * @param estudiante El estudiante al que pertenece el reporte de calificaciones.
     * */
    public Calificaciones(Trimestre trimestre, Estudiante estudiante) {
        this.trimestre = trimestre;
        this.estudiante = estudiante;
    }

    /**
     * Retorna la letra (o secuencia de letras en caso que {@link Calificaciones#USAR_LETRAS_MAS} sea <code>true</code>))
     * que representa una calificación dada.
     *
     * @param val La calificación numérica a convertir en letra.
     *
     * @return
     * <ul>
     *     <li>"I" para val = -2</li>
     *     <li>"R" para val = -1</li>
     *     <li>"F" para 0 <= val < 60</li>
     *     <li>"D" para 60 <= val < 70</li>
     *     <li>"C" para 70 <= val < 80</li>
     *     <li>"B" para 80 <= val < 90</li>
     *     <li>"A" para val >= 90</li>
     * </ul>
     * En caso que {@link Calificaciones#USAR_LETRAS_MAS} sea <code>true</code>, se agregan dos casos más:
     * <ul>
     *     <li>"C+" para 75 <= val < 80</li>
     *     <li>"B+" para 85 <= val < 90</li>
     * </ul>
     * */
    private String getLetra(int val) {
        if(val == -1) return "R";
        if(val == -2) return "I";
        if(val < 60) return "F";
        if(val < 70) return "D";
        if(val < 80) return "C" + ((USAR_LETRAS_MAS && val >= 75) ? "+" : "");
        if(val < 90) return "B" + ((USAR_LETRAS_MAS && val >= 85) ? "+" : "");
        return "A";
    }

    /**
     * Retorna el valor de la letra dada.
     *
     * @param letra La letra a convertir en valor numérico para puntos de honor.
     *
     * @return
     * <ul>
     *     <li>0 para "F"</li>
     *     <li>1 para "D"</li>
     *     <li>2 para "C"</li>
     *     <li>3 para "B"</li>
     *     <li>4 para "A"</li>
     *     <li>-1 para casos inválidos</li>
     * </ul>
     * En caso que {@link Calificaciones#USAR_LETRAS_MAS} sea <code>true</code>, se agregan dos casos más:
     * <ul>
     *     <li>2.5 para "C+"</li>
     *     <li>3.5 para "B+"</li>
     * </ul>
     * */
    private double valorLetra(String letra) {
        return "FDCBA".indexOf(letra.charAt(0)) + (USAR_LETRAS_MAS && letra.endsWith("+") ? 0.5 : 0);
    }

    /**
     * Calcula los puntos de honor para el reporte de calificaciones.
     * Se define como suma del producto entre el valor de la letra de cada asignatura y el número de créditos.
     *
     * @return La cantidad de puntos de honor en este reporte de calificaciones.
     * */
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

    /**
     * Calcula la suma de los créditos de todas las asignaturas en este reporte de calificaciones.
     * @return La cantidad total de créditos del trimestre.
     * */
    public int getCreditos() {
        int creditos = 0;
        for(Asignatura asig : calificaciones.keySet()) {
            creditos += asig.getCreditos();
        }
        return creditos;
    }

    /**
     * Calcula el índice trimestral, como el cociente entre los puntos de honor y la cantidad total de créditos.
     * @return El índice trimestral. Si el número de créditos es cero y/o no existen asignaturas en este reporte, devuelve -1.
     * */
    public double getIndiceTrimestral() {
        double puntos = getPuntosDeHonor();
        double creditos = getCreditos();
        return creditos != 0 ? puntos / creditos : -1;
    }

    /**
     * Obtiene el trimestre al que pertenece este reporte de calificaciones.
     * @return El trimestre del reporte de calificaciones.
     * */
    public Trimestre getTrimestre() {
        return trimestre;
    }

    /**
     * Cambia el trimestre al que pertenece este reporte de calificaciones.
     * @param trimestre El nuevo trimestre del reporte de calificaciones.
     * */
    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }

    /**
     * Obtiene el estudiante al que pertenece este reporte de calificaciones.
     * @return El estudiante del reporte de calificaciones.
     * */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Cambia el estudiante al que pertenece este reporte de calificaciones.
     * @param estudiante El nuevo estudiante del reporte de calificaciones.
     * */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * Obtiene el mapa detallando relaciones entre asignaturas y su respectiva calificación numérica.
     * Cambios al objeto devuelto se verán reflejos en las calificaciones de este reporte.
     *
     * @return El mapa de relación asignatura-calificación.
     * */
    public HashMap<Asignatura, Integer> getCalificaciones() {
        return calificaciones;
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
