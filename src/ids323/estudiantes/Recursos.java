package ids323.estudiantes;

import ids323.estudiantes.util.GestorImagenes;
import ids323.estudiantes.util.LectorLineas;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase de utilidad que se encarga de cargar todos los recursos externos que el código necesita.
 * */
public class Recursos {

    /**
     * La lista de "tips" que se muestran en la ventana inicial del programa.
     * */
    public static ArrayList<String> tips = new ArrayList<>();

    /**
     * Lee los recursos que necesita el programa del "classpath".
     * */
    public static void cargar() {
        tips.clear();
        try {
            ArrayList<String> lines = LectorLineas.leer("/resources/tips.txt");
            tips.addAll(lines);
        } catch(IOException x) {
            Main.ventana.mostrarError(x.getMessage());
        }

    }

    private static String getRutaIcono(String nombre) {
        return "/assets/icons/" + nombre + ".png";
    }

    public static BufferedImage getIcono(String nombre) {
        return GestorImagenes.load(getRutaIcono(nombre));
    }
}
