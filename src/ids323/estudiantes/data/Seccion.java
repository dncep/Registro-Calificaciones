package ids323.estudiantes.data;

import java.util.ArrayList;

public class Seccion {
    public int id;
    public String profesor;
    public String aula;
    public ArrayList<Estudiante> estudiantes = new ArrayList<>();
    public Asignatura asignatura;
    public Trimestre trimestre;
    public Horario horario;
    public String codigo;

    @Override
    public String toString() {
        return codigo + " - " + trimestre + " (ID: " + id + ")";
    }
}
