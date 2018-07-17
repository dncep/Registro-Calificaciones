package ids323.estudiantes.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase estática de utilidad que lee un archivo y lo divide en líneas no vacías.
 */
public class LectorLineas {
    /**
     * Lee un archivo recurso dentro del classpath y lo divide en líneas no vacías.
     *
     * @throws IOException
     *
     * @param ruta La ruta dentro del classpath que debe leer.
     * @return Un <code>ArrayList&lt;String&gt;</code> con todas las líneas del archivo, excluyendo las vacías y las que empiezan con el símbolo '#'.
     * */
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

    /**
     * Lee un archivo dentro del filesystem del dispositivo y lo divide en líneas no vacías.
     *
     * @throws IOException
     *
     * @param archivo El archivo dentro del filesystem del dispositivo.
     * @return Un <code>ArrayList&lt;String&gt;</code> con todas las líneas del archivo, excluyendo las vacías y las que empiezan con el símbolo '#'.
     * */
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

    /**
     * LectorLineas no debe ser instanciado.
     * */
    private LectorLineas() {}
}