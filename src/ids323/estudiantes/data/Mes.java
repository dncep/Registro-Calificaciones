package ids323.estudiantes.data;

public enum Mes {
    ENERO,
    FEBRERO,
    MARZO,
    ABRIL,
    MAYO,
    JUNIO,
    JULIO,
    AGOSTO,
    SEPTIEMBRE,
    OCTUBRE,
    NOVIEMBRE,
    DICIEMBRE;

    @Override
    public String toString() {
        return name().charAt(0) + name().toLowerCase().substring(1);
    }
}
