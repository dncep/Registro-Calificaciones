package ids323.estudiantes.data;

import java.util.HashMap;
import java.util.Map;

public class Horario {
    private HashMap<DiaSemana, HoraDia> horas = new HashMap<>();

    public Horario() {
    }

    public boolean choca(Horario horario) {
        for(DiaSemana dia : DiaSemana.values()) {
            HoraDia este = this.horas.get(dia);
            HoraDia otro = horario.horas.get(dia);
            if(este != null && otro != null && este.choca(otro)) return true;
        }
        return false;
    }

    public void colocar(DiaSemana dia, HoraDia hora) {
        horas.put(dia, hora);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<DiaSemana, HoraDia> entry : horas.entrySet()) {
            sb.append(entry.getKey().corto);
            sb.append(' ');
            sb.append(entry.getValue());
            sb.append(", ");
        }

        if(sb.length() > 1) sb.setLength(sb.length()-2);
        return sb.toString();
    }

    public HashMap<DiaSemana, HoraDia> getMapa() {
        return horas;
    }

    public Horario duplicate() {
        Horario nuevo = new Horario();
        for(Map.Entry<DiaSemana, HoraDia> entry : this.horas.entrySet()) {
            nuevo.horas.put(entry.getKey(), entry.getValue().duplicate());
        }
        return nuevo;
    }
}
