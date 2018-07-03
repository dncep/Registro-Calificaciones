package ids323.estudiantes.data;

import java.util.Objects;

public class Trimestre {
    public MesTrimestre mes;
    public int anio;

    public Trimestre(MesTrimestre mes, int anio) {
        this.mes = mes;
        this.anio = anio;
    }

    @Override
    public String toString() {
        return mes.formato.replace("%d", "" + anio).replace("%d", "" + (anio+1));
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
