package ids323.estudiantes.data;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Objeto representación de un número de cédula.
 * */
public class Cedula {
    /**
     * La expresión regular para la entrada de una cédula.
     * Acepta valores tanto de formato XXXXXXXXXXX como XXX-XXXXXXX-X donde X es un dígito.
     * */
    private static final Pattern REGEX_CEDULA = Pattern.compile("\\d{11}|\\d{3}-\\d{7}-\\d");

    /**
     * La cadena que representa la cédula.
     * */
    private String cedula;

    /**
     * Crea una cédula sin validación de formato.
     * */
    private Cedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Crea una cédula, dado el valor de entrada. Este valor es comparado con la expresión regular {@link Cedula#REGEX_CEDULA}.
     *
     * @param input La cadena a convertir en un objeto Cedula
     *
     * @return Un objeto Cedula si la cadena es una cédula válida, <code>null</code> si no lo es.
     * */
    public static Cedula crearCedula(String input) {
        if(REGEX_CEDULA.matcher(input).matches()) {
            if(input.length() == 11) input = input.substring(0,3) + "-" + input.substring(3,10) + "-" + input.substring(10,11);
            return new Cedula(input);
        }
        return null;
    }

    @Override
    public String toString() {
        return cedula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cedula cedula1 = (Cedula) o;
        return Objects.equals(cedula, cedula1.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
}
