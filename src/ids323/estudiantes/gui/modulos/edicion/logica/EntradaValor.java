package ids323.estudiantes.gui.modulos.edicion.logica;

import javax.swing.*;

public interface EntradaValor {
    JComponent getComponente();
    String validarEntrada();
    void guardarEntrada();
    ValorEdicion<?> getValorEdicion();

    int getCodigoValor();
}
