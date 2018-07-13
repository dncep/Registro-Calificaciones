package ids323.estudiantes.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Loads images and caches them.
 */
public class GestorImagenes {
    private static HashMap<String, BufferedImage> loadedImages = new HashMap<>();

    static {
        BufferedImage nullTexture = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = nullTexture.createGraphics();
        gr.setColor(new Color(0, 120, 255));
        gr.fillRect(0, 0, 2, 2);
        gr.setColor(Color.BLACK);
        gr.fillRect(1, 0, 1, 1);
        gr.fillRect(0, 1, 1, 1);
        loadedImages.put("null", nullTexture);
    }

    public static void clear() {
        loadedImages.clear();
    }

    public static BufferedImage load(String path) {
        if (!loadedImages.containsKey(path)) {
            try(InputStream is = GestorImagenes.class.getResourceAsStream(path)) {
                if (is != null) {
                    loadedImages.put(path, ImageIO.read(is));
                    is.close();
                } else {
                    System.out.println("[WARN]  Image \"" + path + "\" not found.");
                }
            } catch (IOException e) {
                System.out.println("[WARN]  Image \"" + path + "\" not found.");
            }
        }
        if (loadedImages.get(path) != null) {
            return loadedImages.get(path);
        } else {
            return loadedImages.get("null");
        }
    }

    private GestorImagenes() {}
}
