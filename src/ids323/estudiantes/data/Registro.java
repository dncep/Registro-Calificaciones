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
                Estudiante est = new Estudiante(sr.readInt());
                est.setNombre(sr.readString());
                est.setApellido(sr.readString());
                est.setFechaNacimiento(sr.readDate());
                est.setEstado(Estado.values()[sr.readByte()]);
                est.setCarrera(Carrera.values()[sr.readByte()]);
                est.setCedula(Cedula.crearCedula(sr.readString()));
                est.setExtranjero(sr.readBoolean());
                estudiantes.add(est);
            }

            ID_ASIGNATURA = sr.readInt();
            int cantAsignaturas = sr.readInt();
            for(int i = 0; i < cantAsignaturas; i++) {
                Asignatura asig = new Asignatura(sr.readInt());
                asig.setCodigo(sr.readString());
                asig.setNombre(sr.readString());
                asig.setArea(AreaAcademica.values()[sr.readByte()]);
                asig.setProfesor(sr.readString());
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
                sw.writeInt(est.getId());
                sw.writeString(est.getNombre());
                sw.writeString(est.getApellido());
                sw.writeDate(est.getFechaNacimiento());
                sw.writeByte(est.getEstado().ordinal());
                sw.writeByte(est.getCarrera().ordinal());
                sw.writeString(est.getCedula().toString());
                sw.writeBoolean(est.isEsExtranjero());
            }

            sw.writeInt(ID_ASIGNATURA);
            sw.writeInt(asignaturas.size());

            for(Asignatura asig : asignaturas) {
                sw.writeInt(asig.getId());
                sw.writeString(asig.getCodigo());
                sw.writeString(asig.getNombre());
                sw.writeByte(asig.getArea().ordinal());
                sw.writeString(asig.getProfesor());
            }
        } catch(IOException x) {
            x.printStackTrace();
        }
    }
}
