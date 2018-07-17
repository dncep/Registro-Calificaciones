package ids323.estudiantes;

import ids323.estudiantes.data.Registro;
import ids323.estudiantes.gui.Ventana;

import java.io.File;

public class Main {


    public static Registro registro;
    public static Ventana ventana;

    public static void main(String[] args) {
        registro = new Registro(new File(System.getProperty("user.home") + File.separator + "registro_calificaciones"));
        registro.cargar();

        ventana = new Ventana();
        Resources.load();
        ventana.panelVacio.setTips(Resources.tips);
        ventana.panelVacio.start();
    }
}
