package ids323.estudiantes.util;

import java.awt.image.BufferedImage;

public class Comunes {

    public static boolean esCaracterEspecial(char ch) {
        return "\b\r\n\t\f\u007F\u001B".contains("" + ch);
    }

    private static String getRutaIcono(String nombre) {
        return "/assets/icons/" + nombre + ".png";
    }

    public static BufferedImage getIcono(String nombre) {
        return GestorImagenes.load(getRutaIcono(nombre));
    }
}
