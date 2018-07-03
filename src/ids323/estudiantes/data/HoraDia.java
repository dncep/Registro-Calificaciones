package ids323.estudiantes.data;

import java.util.Objects;

public class HoraDia {
    private int inicio;
    private int fin;

    public HoraDia(int inicio, int fin) {
        if(inicio > 0 && fin > inicio && inicio < 24 && fin <= 24) {
            this.inicio = inicio;
            this.fin = fin;
        } else throw new IllegalArgumentException("Horas introducidas inválidas (" + inicio + "/" + fin + ")");
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    public boolean choca(HoraDia hora) {
        return Math.max(this.inicio, hora.inicio) < Math.min(this.fin, hora.fin);
    }

    public HoraDia duplicate() {
        return new HoraDia(inicio, fin);
    }

    @Override
    public String toString() {
        return inicio + "/" + fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoraDia horaDia = (HoraDia) o;
        return inicio == horaDia.inicio &&
                fin == horaDia.fin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inicio, fin);
    }
}
