package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.TokenModulo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Interfaz que permite la comunicación entre partes del programa y la lista de pestañas.
 */
public class TabManager {
    public static ArrayList<Tab> openTabs = new ArrayList<>();

    private static Tab selectedTab = null;

    public static void openTab(TokenModulo path, int index) {
        openTab(path);
        selectLocation(selectedTab, index, 0);
    }

    public static void openTab(TokenModulo path, int index, int length) {
        openTab(path);
        selectLocation(selectedTab, index, length);
    }

    public static void openTab(TokenModulo token) {
        for (int i = 0; i < openTabs.size(); i++) {
            if (openTabs.get(i).token.equals(token)) {
                setSelectedTab(openTabs.get(i));
                return;
            }
        }
        Tab nt = new Tab(token);
        openTabs.add(nt);
        Main.ventana.listaPestanas.addTab(nt);
        setSelectedTab(nt);
    }

    private static void selectLocation(Tab tab, int index, int length) {
    }

    public static void closeSelectedTab() {
        closeSelectedTab(false);
    }

    public static void closeSelectedTab(boolean force) {
        closeTab(getSelectedTab(), force);
    }

    public static void closeTab(Tab tab) {
        closeTab(tab, false);
    }

    public static void closeTab(Tab tab, boolean force) {
        if(tab == null) return;
        if(!force) {
            if(!tab.isGuardado()) {
                int result = JOptionPane.showOptionDialog(Main.ventana.jframe, "Hay cambios sin guardar. ¿Desea guardarlos?", "Cambios sin guardar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"Guardar", "No guardar", "Cancelar"}, "Guardar");
                if(result == JOptionPane.CANCEL_OPTION) return;
                if(result == JOptionPane.YES_OPTION) tab.guardar();
            }
        }
        for (int i = 0; i < openTabs.size(); i++) {
            if (openTabs.get(i) == tab) {
                if (selectedTab == openTabs.get(i)) setSelectedTab(Main.ventana.listaPestanas.getFallbackTab(tab));

                Main.ventana.listaPestanas.removeTab(tab);
                openTabs.remove(i);

                return;
            }
        }
    }

    public static void setSelectedTab(Tab tab) {
        Main.ventana.listaPestanas.selectTab(tab);
        if (selectedTab != null) {
            selectedTab = null;
        }
        if (tab != null) {
            selectedTab = tab;
            Main.ventana.areaModulos.cambiarModulo(tab.getComponenteModulo());
            tab.onSelect();
        } else {
            Main.ventana.areaModulos.cambiarModulo(null);
        }
    }

    public static Tab getSelectedTab() {
        return selectedTab;
    }

    public static Tab getTabForToken(TokenModulo token) {
        for(Tab tab : openTabs) {
            if(tab.token == token) return tab;
        }
        return null;
    }
}
