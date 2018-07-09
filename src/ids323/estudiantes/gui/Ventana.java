package ids323.estudiantes.gui;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.explorer.ProjectExplorerMaster;
import ids323.estudiantes.gui.tablist.TabListMaster;
import ids323.estudiantes.util.Padding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ventana {

    public static JFrame jframe;
    public static final TabListMaster tabList = new TabListMaster();
    public static JPanel welcomePane = new JPanel();
    public static ProjectExplorerMaster projectExplorer = new ProjectExplorerMaster();
    public static AreaModulo editArea = new AreaModulo();

    public Ventana() {
        jframe = new JFrame("Registro de Calificaciones");
        jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(700,500));
        jframe.setContentPane(contentPane);

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(projectExplorer);
        sidebar.setPreferredSize(new Dimension(300,1));
        contentPane.add(sidebar, BorderLayout.WEST);

        JPanel sidebarHeader = new JPanel(new BorderLayout());
        sidebarHeader.add(new Padding(15), BorderLayout.WEST);
        JLabel sidebarLabel = new JLabel("REGISTRO");
        sidebarHeader.add(sidebarLabel);
        sidebarLabel.setForeground(Colors.TEXT);
        sidebarHeader.setPreferredSize(new Dimension(1, 35));
        sidebarHeader.setBackground(Colors.ACCENT_DARKER);
        sidebar.add(sidebarHeader, BorderLayout.NORTH);

        contentPane.add(editArea, BorderLayout.CENTER);

        welcomePane.setBackground(Colors.BACKGROUND);

        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //TabManager.openTab("C:\\Users\\Usuario\\Craftr\\natives\\src\\craftr\\lang\\Enum.craftr");
        //TabManager.openTab("C:\\Users\\Usuario\\Craftr\\natives\\src\\craftr\\lang\\Object.craftr");

        jframe.pack();
        jframe.setVisible(true);

        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                terminar();
            }
        });
    }

    public static boolean isVisible() {
        return jframe.isVisible();
    }

    private void terminar() {
        Main.registro.guardar();
        System.exit(0);
    }
}
