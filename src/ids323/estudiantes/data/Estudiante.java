package ids323.estudiantes.data;

import java.util.Calendar;

/**
 * Representación de un estudiante como objeto.
 * */
public class Estudiante {
    public String nombre;
    public String apellido;
    public Calendar fechaNacimiento;
    public Estado estado;
    public int id;
    public Carrera carrera;
    public Cedula cedula;
    public boolean esExtranjero = false;

    public Estudiante() {
    }
}
