package ids323.estudiantes.data;

/**
 * Representa un mes en el cual un trimestre puede empezar.
 * */
public enum MesTrimestre {
    FEBRERO("Febrero-Abril %d"),
    MAYO("Mayo-Julio %d"),
    AGOSTO("Agosto-Octubre %d"),
    NOVIEMBRE("Noviembre %d-Enero %d");

    /**
     * Contiene la cadena que describe cómo el mes se va a ver en pantalla.<br>
     * %d se va a reemplazar por el año en el que se encuentra el trimestre.<br>
     * Si hay dos %d, el segundo será reemplazado por el año siguiente.
     * */
    String formato;

    /**
     * Crea un mes trimestral con el formato dado.
     * */
    MesTrimestre(String formato) {
        this.formato = formato;
    }

    /**
     * Obtiene el formato del trimestre.
     *
     * @return El formato del trimestre.
     * */
    public String getFormato() {
        return formato;
    }

    @Override
    public String toString() {
        return formato.replace("%d","").replace(" -","-").trim();
    }
}
