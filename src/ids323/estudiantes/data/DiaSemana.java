package ids323.estudiantes.data;

public enum DiaSemana {
    LUNES("Lunes", "Lun"),
    MARTES("Martes", "Mar"),
    MIERCOLES("Miércoles", "Mie"),
    JUEVES("Jueves", "Jue"),
    VIERNES("Viernes", "Vie"),
    SABADO("Sábado", "Sab");

    public final String nombre;
    public final String corto;

    DiaSemana(String nombre, String corto) {
        this.nombre = nombre;
        this.corto = corto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorto() {
        return corto;
    }
}
