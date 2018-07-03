package ids323.estudiantes.gui;

import ids323.estudiantes.gui.explorer.ProjectExplorerMaster;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.tablist.TabListMaster;
import ids323.estudiantes.util.Padding;

import javax.swing.*;
import java.awt.*;

public class Ventana {

    private static JFrame jframe;
    public static final TabListMaster tabList = new TabListMaster();
    public static JPanel welcomePane = new JPanel();
    public static ProjectExplorerMaster projectExplorer = new ProjectExplorerMaster();
    public static AreaModulo editArea = new AreaModulo();

    public Ventana() {
        jframe = new JFrame("Registro de Calificaciones");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(700,500));
        jframe.setContentPane(contentPane);

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(projectExplorer);
        sidebar.setPreferredSize(new Dimension(300,1));
        contentPane.add(sidebar, BorderLayout.WEST);

        JPanel sidebarHeader = new JPanel(new BorderLayout());
        sidebarHeader.add(new Padding(15), BorderLayout.WEST);
        sidebarHeader.add(new JLabel("REGISTRO"));
        sidebarHeader.setPreferredSize(new Dimension(1, 35));
        sidebarHeader.setBackground(new Color(215, 215, 215));
        sidebar.add(sidebarHeader, BorderLayout.NORTH);

        contentPane.add(editArea, BorderLayout.CENTER);

        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);

        TabManager.openTab("C:\\Users\\Usuario\\Craftr\\natives\\src\\craftr\\lang\\Enum.craftr");
        TabManager.openTab("C:\\Users\\Usuario\\Craftr\\natives\\src\\craftr\\lang\\Object.craftr");

        jframe.pack();
        jframe.setVisible(true);
    }

    public static boolean isVisible() {
        return jframe.isVisible();
    }
}
