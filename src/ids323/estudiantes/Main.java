package ids323.estudiantes;

import ids323.estudiantes.data.Registro;
import ids323.estudiantes.gui.Ventana;

import java.io.File;

public class Main {


    public static void main(String[] args) {

        new Ventana();

        Registro registro = new Registro(new File(System.getProperty("user.home") + File.separator + "registro_calificaciones"));
        //registro.cargar();
        //registro.guardar();
    }
}