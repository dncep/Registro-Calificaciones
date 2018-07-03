package ids323.estudiantes.data;

public enum MesTrimestre {
    FEBRERO("Febrero-Mayo %d", "Febrero %d"),
    MAYO("Mayo-Agosto %d", "Mayo %d"),
    AGOSTO("Agosto-Noviembre %d", "Agosto %d"),
    NOVIEMBRE("Noviembre %d-Febrero %d", "Noviembre %d");

    String formato;
    String corto;

    MesTrimestre(String formato, String corto) {
        this.formato = formato;
        this.corto = corto;
    }

    public String getFormato() {
        return formato;
    }

    public String getCorto() {
        return corto;
    }

    public String getNombre() {
        return corto.substring(0, corto.indexOf(' '));
    }
}
