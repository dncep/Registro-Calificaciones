package ids323.estudiantes.data;

import ids323.estudiantes.saveio.SaveReader;
import ids323.estudiantes.saveio.SaveWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * Lista para las secciones.
     * */
    public final ArrayList<Seccion> secciones = new ArrayList<>();
    /**
     * El fichero en el cual almacenar los datos persistentes.
     * */
    private final File file;

    /**
     * El ID de la siguiente asignatura a crear.
     * */
    public int ID_MATERIA = 0;

    /**
     * El ID de la siguiente seccion a crear.
     * */
    public int ID_SECCION = 0;

    /**
     * Construye un registro que se guarda en el fichero dado.
     *
     * @param file El fichero en el cual almacenar los datos persistentes.
     * */
    public Registro(File file) {
        this.file = file;

        if(!file.exists()) {
            try {
                boolean success = file.createNewFile();
            } catch(IOException x) {
                x.printStackTrace();
            }
        }
    }

    /**
     * Borra los datos en este objeto y los llena con los datos presentes en el fichero correspondiente a este registro.
     * */
    public void cargar() {
        ID_MATERIA = 0;
        ID_SECCION = 0;
        estudiantes.clear();
        asignaturas.clear();

        try(FileInputStream fis = new FileInputStream(file)) {
            SaveReader sr = new SaveReader(fis);

            int versionArchivo = sr.readByte();

            int cantEstudiantes = sr.readByte();
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

            ID_MATERIA = (versionArchivo >= 2) ? sr.readInt() : sr.readByte();
            int cantMaterias = sr.readByte();
            for(int i = 0; i < cantMaterias; i++) {
                Asignatura mat = new Asignatura();
                mat.codigo = sr.readString();
                mat.nombre = sr.readString();
                mat.area = AreaAcademica.values()[sr.readByte()];
                mat.id = sr.readByte();
                asignaturas.add(mat);
            }

            if(versionArchivo >= 2) ID_SECCION = sr.readInt();
            if(versionArchivo >= 3) {
                int cantSecciones = sr.readInt();
                for(int i = 0; i < cantSecciones; i++) {
                    Seccion sec = new Seccion();

                    sec.id = sr.readInt();
                    sec.profesor = sr.readString();
                    sec.aula = sr.readString();

                    int cantEstudiantesEnSeccion = sr.readInt();
                    for(int j = 0; j < cantEstudiantesEnSeccion; j++) {
                        int id = sr.readInt();
                        for(Estudiante est : estudiantes) {
                            if(est.id == id) {
                                sec.estudiantes.add(est);
                                break;
                            }
                        }
                    }

                    int idMateria = sr.readInt();
                    for(Asignatura mat : asignaturas) {
                        if(mat.id == idMateria) {
                            sec.asignatura = mat;
                            break;
                        }
                    }

                    MesTrimestre mes = MesTrimestre.values()[sr.readByte()];
                    int anio = sr.readInt();
                    sec.trimestre = new Trimestre(mes, anio);
                    sec.horario = sr.readHorario();
                    sec.codigo = sr.readString();
                    secciones.add(sec);
                }
            }
        } catch(IOException x) {
            x.printStackTrace();
        }
    }

    /**
     * Escribe los datos de este registro en el fichero correspondiente.
     * */
    public void guardar() {

        try(FileOutputStream fos = new FileOutputStream(file)) {
            SaveWriter sw = new SaveWriter(fos);

            sw.writeByte(VERSION_DATA);

            //Estudiantes
            sw.writeByte(estudiantes.size());

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

            sw.writeInt(ID_MATERIA);
            sw.writeByte(asignaturas.size());

            for(Asignatura mat : asignaturas) {
                sw.writeString(mat.codigo);
                sw.writeString(mat.nombre);
                sw.writeByte(mat.area.ordinal());
                sw.writeByte(mat.id);
            }

            sw.writeInt(ID_SECCION);
            sw.writeInt(secciones.size());
            for(Seccion sec : secciones) {
                sw.writeInt(sec.id);
                sw.writeString(sec.profesor);
                sw.writeString(sec.aula);

                sw.writeInt(sec.estudiantes.size());
                for(Estudiante est : sec.estudiantes) {
                    sw.writeInt(est.id);
                }

                sw.writeInt(sec.asignatura.id);
                sw.writeByte(sec.trimestre.mes.ordinal());
                sw.writeInt(sec.trimestre.anio);
                sw.writeHorario(sec.horario);
                sw.writeString(sec.codigo);
            }
        } catch(IOException x) {
            x.printStackTrace();
        }
    }
}
