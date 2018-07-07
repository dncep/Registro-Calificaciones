package ids323.estudiantes;

import ids323.estudiantes.data.*;
import ids323.estudiantes.gui.Ventana;

import java.io.File;
import java.util.Calendar;

public class Main {


    public static Registro registro;

    public static void main(String[] args) {

        registro = new Registro(new File(System.getProperty("user.home") + File.separator + "registro_calificaciones"));

        registro.cargar();

        /*Estudiante est = new Estudiante();
        est.id = 1074824;
        est.nombre = "Daniel";
        est.apellido = "Cepeda";
        est.fechaNacimiento = Calendar.getInstance();
        est.cedula = Cedula.crearCedula("10101010101");
        est.estado = Estado.NORMAL;
        est.carrera = Carrera.IDS;

        Estudiante est2 = new Estudiante();
        est2.id = 2382034;
        est2.nombre = "John";
        est2.apellido = "Doe";
        est2.fechaNacimiento = Calendar.getInstance();
        est2.cedula = Cedula.crearCedula("10101010102");
        est2.estado = Estado.NORMAL;
        est2.carrera = Carrera.LCC;

        registro.estudiantes.add(est);
        registro.estudiantes.add(est2);

        Asignatura asig = new Asignatura();
        asig.area = AreaAcademica.BASICAS;
        asig.nombre = "Cálculo Vectorial";
        asig.creditos = 5;
        asig.codigo = "CBM301";
        asig.profesor = "Nehomar Lazama";

        registro.asignaturas.add(asig);*/

        new Ventana();
    }
}
