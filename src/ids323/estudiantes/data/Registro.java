package ids323.estudiantes.data;

import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.modulos.ModuleTokenRoot;
import ids323.estudiantes.saveio.SaveReader;
import ids323.estudiantes.saveio.SaveWriter;

import java.io.*;
import java.util.ArrayList;

/**
 * Objeto destinado a almacenar los datos del sistema, incluyendo los estudiantes y las asignaturas.
 * */
public class Registro {

    public static final byte VERSION_DATA = 1;

    /**
     * Lista para los estudiantes.
     * */
    public final ArrayList<Estudiante> estudiantes = new ArrayList<>();
    /**
     * Lista para las asignaturas.
     * */
    public final ArrayList<Asignatura> asignaturas = new ArrayList<>();
    /**
     * El fichero en el cual almacenar los datos persistentes.
     * */
    private final File file;

    /**
     * El ID del siguiente estudiante a crear.
     * */
    public int ID_ESTUDIANTE = 1100000;
    /**
     * El ID de la siguiente asignatura a crear.
     * */
    public int ID_ASIGNATURA = 0;

    public ModuleToken rootToken;

    /**
     * Construye un registro que se guarda en el fichero dado.
     *
     * @param file El fichero en el cual almacenar los datos persistentes.
     * */
    public Registro(File file) {
        this.file = file;

        rootToken = new ModuleTokenRoot();
    }

    /**
     * Borra los datos en este objeto y los llena con los datos presentes en el fichero correspondiente a este registro.
     * */
    public void cargar() {
        ID_ASIGNATURA = 0;
        ID_ESTUDIANTE = 1100000;
        estudiantes.clear();
        asignaturas.clear();

        if(!file.exists()) return;

        try(FileInputStream fis = new FileInputStream(file)) {
            SaveReader sr = new SaveReader(fis);

            int versionArchivo = sr.readByte();

            ID_ESTUDIANTE = sr.readInt();
            int cantEstudiantes = sr.readInt();
            for(int i = 0; i < cantEstudiantes; i++) {
                Estudiante est = new Estudiante();
                est.nombre = sr.readString();
                est.apellido = sr.readString();
                est.fechaNacimiento = sr.readDate();
                est.estado = Estado.values()[sr.readByte()];
                est.id = sr.readInt();
                est.carrera = Carrera.values()[sr.readByte()];
                est.cedula = Cedula.crearCedula(sr.readString());
                est.esExtranjero = sr.readBoolean();
                estudiantes.add(est);
            }

            ID_ASIGNATURA = sr.readInt();
            int cantAsignaturas = sr.readInt();
            for(int i = 0; i < cantAsignaturas; i++) {
                Asignatura asig = new Asignatura();
                asig.id = sr.readInt();
                asig.codigo = sr.readString();
                asig.nombre = sr.readString();
                asig.area = AreaAcademica.values()[sr.readByte()];
                asig.profesor = sr.readString();
                asignaturas.add(asig);
            }
        } catch(EOFException x) {
            System.out.println("Corrupted save file");
        } catch(IOException x) {
            x.printStackTrace();
        }
    }

    /**
     * Escribe los datos de este registro en el fichero correspondiente.
     * */
    public void guardar() {

        if(!file.exists()) {
            try {
                if(!file.createNewFile()) return;
            } catch(IOException x) {
                x.printStackTrace();
                return;
            }
        }

        try(FileOutputStream fos = new FileOutputStream(file)) {
            SaveWriter sw = new SaveWriter(fos);

            sw.writeByte(VERSION_DATA);

            //Estudiantes
            sw.writeInt(ID_ESTUDIANTE);
            sw.writeInt(estudiantes.size());

            for(Estudiante est : estudiantes) {
                sw.writeString(est.nombre);
                sw.writeString(est.apellido);
                sw.writeDate(est.fechaNacimiento);
                sw.writeByte(est.estado.ordinal());
                sw.writeInt(est.id);
                sw.writeByte(est.carrera.ordinal());
                sw.writeString(est.cedula.toString());
                sw.writeBoolean(est.esExtranjero);
            }

            sw.writeInt(ID_ASIGNATURA);
            sw.writeInt(asignaturas.size());

            for(Asignatura asig : asignaturas) {
                sw.writeInt(asig.id);
                sw.writeString(asig.codigo);
                sw.writeString(asig.nombre);
                sw.writeByte(asig.area.ordinal());
                sw.writeString(asig.profesor);
            }
        } catch(IOException x) {
            x.printStackTrace();
        }
    }
}
