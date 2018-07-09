package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.gui.Colors;
import ids323.estudiantes.gui.InfoPanel;
import ids323.estudiantes.util.Padding;

import javax.swing.*;
import java.awt.*;

public abstract class ModuloVista extends JPanel implements DisplayModule {

    protected final String title;
    protected final String subtitle;
    protected final InfoPanel infoPanel = new InfoPanel();

    public ModuloVista(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    protected void construir() {
        this.setLayout(new BorderLayout());

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(true);
        inner.setBackground(Colors.ACCENT_LIGHT);
        JScrollPane sp = new JScrollPane(inner);
        sp.setBorder(BorderFactory.createEmptyBorder());
        this.add(sp);

        inner.add(new Padding(15), BorderLayout.NORTH);
        inner.add(new Padding(30, Colors.BACKGROUND), BorderLayout.SOUTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        inner.add(body, BorderLayout.CENTER);


        body.add(new Padding(250, Colors.BACKGROUND), BorderLayout.WEST);
        body.add(new Padding(250, Colors.BACKGROUND), BorderLayout.EAST);

        {
            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);

            {
                //Title
                JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
                titlePanel.setOpaque(false);
                JLabel title = new JLabel(this.title);
                title.setFont(title.getFont().deriveFont(36f).deriveFont(Font.BOLD));
                title.setForeground(Colors.TEXT);
                titlePanel.add(title);
                header.add(titlePanel, BorderLayout.NORTH);
            }
            {
                //Subtitle
                JPanel subtitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
                subtitlePanel.setOpaque(false);
                JLabel subtitle = new JLabel(this.subtitle);
                subtitle.setFont(subtitle.getFont().deriveFont(24f).deriveFont(Font.PLAIN));
                subtitle.setForeground(Colors.TEXT_MINOR);
                subtitlePanel.add(subtitle);
                header.add(subtitlePanel, BorderLayout.CENTER);
            }
            header.add(new Padding(15), BorderLayout.SOUTH);

            body.add(header, BorderLayout.NORTH);
        }

        {
            JPanel content = new JPanel(new BorderLayout());
            body.add(content, BorderLayout.CENTER);
            content.setBackground(Colors.BACKGROUND);

            content.add(infoPanel);
        }
    }
}
