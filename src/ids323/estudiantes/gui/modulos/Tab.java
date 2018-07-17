package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.tablist.TabItem;

import javax.swing.*;
import java.util.Date;

/**
 * Concept of an open tab in the interface. Contains a component that represents
 * the clickable tab element.
 */
public class Tab {
    public TokenModulo token;
    public ModuloPantalla modulo;
    private Object valorGuardado;
    public boolean visible = true;

    public long tiempoAbierto;
    private boolean guardado = true;
    private TabItem tabItem;

    @Override
    public String toString() {
        return "Tab [titulo=" + getTitulo() + ", token=" + token + ", visible=" + visible + "]";
    }

    public Tab(TokenModulo token) {
        this.token = token;
        modulo = token.crearModulo(this);
        valorGuardado = modulo.getValor();

        tiempoAbierto = new Date().getTime();
    }

    public void onSelect() {
        tiempoAbierto = new Date().getTime();
        modulo.enfocar();
        //modulo.displayCaretInfo();
    }

    public void enEdicion() {
        this.setGuardado(valorGuardado == null || valorGuardado.equals(modulo.getValor()));
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return this.tabItem != null && this.tabItem.isSelected();
    }

    public void guardar() {
        if(!modulo.puedeGuardar()) return;

        Object val = modulo.guardar();
        if(val != null) {
            valorGuardado = val;
            setGuardado(true);
        }
    }

    public JComponent getComponenteModulo() {
        return (JComponent) modulo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        if(this.guardado != guardado) {
            this.guardado = guardado;
            actualizarLista();
        }
    }

    private void actualizarLista() {
        Main.ventana.listaPestanas.repaint();
    }

    public String getTitulo() {
        return token.getTitulo();
    }

    public TabItem getLinkedTabItem() {
        return tabItem;
    }

    public void linkTabItem(TabItem tabItem) {
        this.tabItem = tabItem;
    }
}
