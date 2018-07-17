package ids323.estudiantes.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by User on 1/8/2017.
 */
public class LectorLineas {
    public static ArrayList<String> leer(String ruta) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        LectorLineas.
                                class.
                                getResourceAsStream(ruta)))) {
            String line;
            for (; (line = br.readLine()) != null; ) {
                if(line.length() > 0 && !line.startsWith("#")) lines.add(line.trim());
            }
            return lines;
        } catch(NullPointerException npe) {
            throw new FileNotFoundException("Archivo no encontrado: " + ruta);
        }
    }

    public static ArrayList<String> leer(File archivo) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        try(FileInputStream inputStream = new FileInputStream(archivo.getPath())){
            Scanner sc = new Scanner(inputStream, "UTF-8");
            while(sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            return lines;
        } catch(FileNotFoundException x) {
            throw new FileNotFoundException("Archivo no encontrado: " + archivo);
        }
    }

    private LectorLineas() {}
}