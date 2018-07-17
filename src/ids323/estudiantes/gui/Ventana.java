package ids323.estudiantes.gui;

import ids323.estudiantes.Main;
import ids323.estudiantes.componentes.CBoton;
import ids323.estudiantes.componentes.Relleno;
import ids323.estudiantes.data.Asignatura;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.data.Profesor;
import ids323.estudiantes.gui.explorador.MasterExploradorRegistro;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.tablist.TabListMaster;
import ids323.estudiantes.util.Comunes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ventana {

    public JFrame jframe;
    public final TabListMaster listaPestanas;
    public PantallaTips panelVacio;
    public MasterExploradorRegistro exploradorRegistro;
    public AreaModulo areaModulos;

    public Ventana() {
        jframe = new JFrame("Registro de Calificaciones");
        jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        listaPestanas = new TabListMaster();
        panelVacio = new PantallaTips();
        exploradorRegistro = new MasterExploradorRegistro(this);
        areaModulos = new AreaModulo(this);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(700,500));
        jframe.setContentPane(contentPane);

        JPanel sidebar = new JPanel(new BorderLayout());

        JScrollPane sp = new JScrollPane(exploradorRegistro);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().setUnitIncrement(20);
        sidebar.add(sp);

        sidebar.setPreferredSize(new Dimension(300,1));
        contentPane.add(sidebar, BorderLayout.WEST);

        JPanel sidebarHeader = new JPanel(new BorderLayout());
        sidebarHeader.add(new Relleno(15), BorderLayout.WEST);
        JLabel sidebarLabel = new JLabel("REGISTRO");
        sidebarHeader.add(sidebarLabel);
        sidebarLabel.setForeground(Colores.TEXTO);
        sidebarHeader.setPreferredSize(new Dimension(1, 35));
        sidebarHeader.setBackground(Colores.PRIMARIO_OSCURO);
        sidebar.add(sidebarHeader, BorderLayout.NORTH);

        contentPane.add(areaModulos, BorderLayout.CENTER);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(Colores.PRIMARIO_OSCURO.brighter());
        contentPane.add(toolbar, BorderLayout.NORTH);

        {
            CBoton button = new CBoton("", new ImageIcon(Estudiante.ICON_NUEVO.getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(Colores.PRIMARIO_OSCURO);
            button.setRolloverColor(Colores.PRIMARIO_MAS_OSCURO);
            button.setPressedColor(Colores.PRIMARIO_CLARO);
            button.setBorder(Colores.PRIMARIO_MAS_OSCURO, 1);
            button.addActionListener(e -> Estudiante.crearNuevo());
            button.setToolTipText("Nuevo Estudiante");
            toolbar.add(button);
        }
        {
            CBoton button = new CBoton("", new ImageIcon(Asignatura.ICON_NUEVO.getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(Colores.PRIMARIO_OSCURO);
            button.setRolloverColor(Colores.PRIMARIO_MAS_OSCURO);
            button.setPressedColor(Colores.PRIMARIO_CLARO);
            button.setBorder(Colores.PRIMARIO_MAS_OSCURO, 1);
            button.addActionListener(e -> Asignatura.crearNueva());
            button.setToolTipText("Nueva Asignatura");
            toolbar.add(button);
        }
        {
            CBoton button = new CBoton("", new ImageIcon(Profesor.ICON_NUEVO.getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(Colores.PRIMARIO_OSCURO);
            button.setRolloverColor(Colores.PRIMARIO_MAS_OSCURO);
            button.setPressedColor(Colores.PRIMARIO_CLARO);
            button.setBorder(Colores.PRIMARIO_MAS_OSCURO, 1);
            button.addActionListener(e -> Profesor.crearNuevo());
            button.setToolTipText("Nuevo Profesor");
            toolbar.add(button);
        }
        {
            CBoton button = new CBoton("", new ImageIcon(Comunes.getIcono("buscar").getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(Colores.PRIMARIO_OSCURO);
            button.setRolloverColor(Colores.PRIMARIO_MAS_OSCURO);
            button.setPressedColor(Colores.PRIMARIO_CLARO);
            button.setBorder(Colores.PRIMARIO_MAS_OSCURO, 1);
            button.addActionListener(e -> exploradorRegistro.triggerSearch());
            button.setToolTipText("Buscar");
            toolbar.add(button);
        }

        {
            CBoton button = new CBoton("", new ImageIcon(Comunes.getIcono("calificaciones").getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(Colores.PRIMARIO_OSCURO);
            button.setRolloverColor(Colores.PRIMARIO_MAS_OSCURO);
            button.setPressedColor(Colores.PRIMARIO_CLARO);
            button.setBorder(Colores.PRIMARIO_MAS_OSCURO, 1);
            button.addActionListener(e -> TabManager.openTab(Main.registro.rankingToken));
            button.setToolTipText("Generar Ranking");
            toolbar.add(button);
        }

        panelVacio.setBackground(Colores.FONDO);
        panelVacio.setForeground(Colores.TEXTO_OSCURO);
        panelVacio.setFont(panelVacio.getFont().deriveFont(24f).deriveFont(Font.BOLD));
        areaModulos.setContent(null);

        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jframe.pack();
        jframe.setVisible(true);

        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                terminar();
            }
        });
    }

    public boolean isVisible() {
        return jframe.isVisible();
    }

    private void terminar() {
        Main.registro.guardar();
        System.exit(0);
    }

    public void mostrarError(String error) {
        JOptionPane.showMessageDialog(jframe, error, "Error Interno", JOptionPane.ERROR_MESSAGE);
    }
}
