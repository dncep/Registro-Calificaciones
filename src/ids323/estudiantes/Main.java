package ids323.estudiantes;

import ids323.estudiantes.data.Registro;
import ids323.estudiantes.gui.Ventana;

import java.io.File;

/**
 * Clase principal del programa.
 * */
public class Main {

    /**
     * Representa el registro que está siendo manipulado por el programa.
     * */
    public static Registro registro;
    /**
     * Representa la ventana que se está mostrando al usuario.
     * */
    public static Ventana ventana;

    public static void main(String[] args) {
        registro = new Registro(new File(System.getProperty("user.home") + File.separator + "registro_calificaciones"));
        registro.cargar();

        ventana = new Ventana();
        Recursos.cargar();
        ventana.panelVacio.setTips(Recursos.tips);
        ventana.panelVacio.activar();
    }
}
