package ids323.estudiantes.gui;

import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * El área a la derecha del explorador y bajo la lista de pestañas que muestra el contenido de cada pestaña.
 * */
public class AreaModulo extends JPanel {
    /**
     * La ventana a la que pertenece.
     * */
    private final Ventana ventana;

    /**
     * El componente siendo mostrado actualmente.
     * */
    private JComponent content = null;

    /**
     * Crea un área módulo para la ventana dada.
     *
     * @param ventana La ventana padre de este componente.
     * */
    public AreaModulo(Ventana ventana) {
        this.ventana = ventana;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 500));

        JPanel tabListHolder = new JPanel(new BorderLayout());

        tabListHolder.setBackground(new Color(200, 202, 205));
        tabListHolder.setPreferredSize(new Dimension(1, 35));

        this.add(tabListHolder, BorderLayout.NORTH);

        JPanel tabList = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        tabList.setBackground(new Color(200, 202, 205));
        tabList.setPreferredSize(new Dimension(1, 30));
        tabList.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        JScrollPane tabSP = new JScrollPane(ventana.listaPestanas);
        tabSP.setBorder(BorderFactory.createEmptyBorder());
        tabSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabListHolder.add(tabSP, BorderLayout.CENTER);

        this.cambiarModulo(ventana.panelVacio);


        KeyStroke saveKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(saveKeystroke, "saveKeystroke");

        this.getActionMap().put("saveKeystroke", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Tab st = TabManager.getSelectedTab();
                if(st != null) st.guardar();
            }
        });


        KeyStroke closeKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK);

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(closeKeystroke, "closeKeystroke");

        this.getActionMap().put("closeKeystroke", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TabManager.closeSelectedTab();
            }
        });
    }

    /**
     * Cambia el módulo siendo mostrado al módulo dado.
     *
     * @param modulo El módulo a mostrar. Si es <code>null</code>, mostrará la pantalla de inicio.
     * */
    public void cambiarModulo(JComponent modulo) {
        if(this.content != null) {
            if(this.content == ventana.panelVacio) ventana.panelVacio.pausar();
            this.remove(this.content);
        }
        if(modulo == null) {
            modulo = ventana.panelVacio;
            ventana.panelVacio.activar();
        }

        this.add(modulo, BorderLayout.CENTER);
        this.content = modulo;

        this.revalidate();
        this.repaint();
    }
}
