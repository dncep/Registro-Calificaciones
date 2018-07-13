package ids323.estudiantes.gui.explorador.base;

/**
 * Created by User on 2/11/2017.
 */
public class MarcaExplorador {
    public static final MarcaExplorador
            DEBUG_WIDTH = new MarcaExplorador("Debug Width"),
            DYNAMIC_ROW_HEIGHT = new MarcaExplorador("Dynamic Row Height");

    private final String displayName;

    public MarcaExplorador(String displayName) {
        this.displayName = displayName;
    }
}
