package ids323.estudiantes.data;

import java.util.Calendar;
import java.util.Objects;

/**
 * Representa un trimestre en el ciclo de INTEC
 * */
public class Trimestre {
    /**
     * El mes en el cual empieza el trimestre.
     * */
    private MesTrimestre mes;
    /**
     * El año en el cual empieza el trimestre.
     * */
    private int anio;

    /**
     * Crea un trimestre con las propiedades dadas.
     *
     * @param mes El mes en el cual empieza el trimestre.
     * @parem anio El año en el cual empieza el trimestre.
     * */
    public Trimestre(MesTrimestre mes, int anio) {
        this.mes = mes;
        this.anio = anio;
    }

    /**
     * Retorna un objeto Trimestre que representa el trimestre más cercano a la fecha en la que se llama el método.<br>
     *
     * Por ejemplo, si se llama a este método el 16 de julio del 2018, el método retorna el trimestre Mayo-Julio 2018.
     *
     * @return El trimestre más cercano.
     * */
    public static Trimestre getTrimestreCercano() {
        Calendar now = Calendar.getInstance();

        int anio = now.get(Calendar.YEAR);
        int mes = now.get(Calendar.MONTH);

        while((mes-1) % 3 != 0) {
            mes = Util.mod(mes-1,12);
        }
        if(mes > now.get(Calendar.MONTH)) anio--;
        Trimestre trimestre = new Trimestre(MesTrimestre.values()[(mes-1)/3], anio);
        return trimestre;
    }

    /**
     * Retorna un objeto trimestre que representa el trimestre siguiente a este.<br>
     *
     * Por ejemplo, este objeto trimestre representa Mayo-Julio 2018, este método retorna el trimestre Agosto-Noviembre 2018.
     *
     * @return El trimestre próximo a este.
     * */
    public Trimestre getProximo() {
        int index = this.mes.ordinal()+1;
        int anio = this.anio;
        if(index >= MesTrimestre.values().length) {
            anio++;
            index = 0;
        }
        return new Trimestre(MesTrimestre.values()[index], anio);
    }

    /**
     * Obtiene el mes de inicio de este trimestre.
     *
     * @return El mes de inicio de este trimestre.
     * */
    public MesTrimestre getMes() {
        return mes;
    }

    /**
     * Obtiene el año de inicio de este trimestre.
     *
     * @return El año de inicio de este trimestre.
     * */
    public int getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return mes.formato.replaceFirst("%d", "" + anio).replaceFirst("%d", "" + (anio+1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trimestre trimestre = (Trimestre) o;
        return anio == trimestre.anio &&
                mes == trimestre.mes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mes, anio);
    }
}
