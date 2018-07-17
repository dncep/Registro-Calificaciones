package ids323.estudiantes;

import ids323.estudiantes.util.LectorLineas;

import java.io.IOException;
import java.util.ArrayList;

public class Resources {

    public static ArrayList<String> tips = new ArrayList<>();

    public static void load() {
        tips.clear();
        try {
            ArrayList<String> lines = LectorLineas.leer("/resources/tips.txt");
            tips.addAll(lines);
        } catch(IOException x) {
            Main.ventana.mostrarError(x.getMessage());
        }

    }

}
