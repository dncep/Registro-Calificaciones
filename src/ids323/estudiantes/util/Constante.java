package ids323.estudiantes.util;

public class Constante {
    private final String nombre;
    private final Object grupo;

    public Constante(String nombre) {
        this(nombre, null);
    }

    public Constante(String nombre, Object grupo) {
        this.nombre = nombre;
        this.grupo = grupo;
    }

    public boolean esDeGrupo(Object grupo) {
        return this.grupo == grupo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
