package ids323.estudiantes.data;

/**
 * Representación de una asignatura como objeto.
 * */
public class Asignatura {
    public int id;
    public AreaAcademica area;
    public String codigo;
    public String nombre;
    public int creditos;

    public Asignatura() {
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (ID: " + id + ")";
    }
}
