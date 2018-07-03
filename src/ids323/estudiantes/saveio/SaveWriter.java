package ids323.estudiantes.saveio;

import ids323.estudiantes.data.DiaSemana;
import ids323.estudiantes.data.HoraDia;
import ids323.estudiantes.data.Horario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SaveWriter {

    private final FileOutputStream fos;

    public SaveWriter(FileOutputStream fos) {
        this.fos = fos;
    }

    public void writeInt(int n) throws IOException {
        byte[] bytes = new byte[4];
        for(int i = 0; i < 4; i++) {
            bytes[i] = (byte) ((n & 0xFF000000) >> (8*3));
            n = n << 8;
        }
        fos.write(bytes);
    }

    public void writeByte(int n) throws IOException {
        fos.write((byte) n);
    }

    public void writeString(String str) throws IOException {
        writeInt(str.length());
        for(char c : str.toCharArray()) {
            writeInt((int) c);
        }
    }

    public void writeDate(Calendar date) throws IOException {
        writeInt(date.get(Calendar.YEAR));
        writeByte(date.get(Calendar.MONTH));
        writeByte(date.get(Calendar.DAY_OF_MONTH));
    }

    public void writeHoraDia(HoraDia hora) throws IOException {
        if(hora != null) {
            writeByte(hora.getInicio());
            writeByte(hora.getFin());
        } else {
            writeByte(0);
            writeByte(0);
        }
    }

    public void writeHorario(Horario horario) throws IOException {
        for(DiaSemana dia : DiaSemana.values()) {
            writeHoraDia(horario.getMapa().get(dia));
        }
    }

    public void writeBoolean(boolean bool) throws IOException {
        writeByte(bool ? 1 : 0);
    }

    public void close() throws IOException {
        fos.close();
    }
}
