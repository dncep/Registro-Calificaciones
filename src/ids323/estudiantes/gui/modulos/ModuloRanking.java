package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.util.StringUtil;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;

public class ModuloRanking extends JPanel implements ModuloPantalla {

    public ModuloRanking() {
        super(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textArea.setForeground(Colores.TEXTO_OSCURO);
        this.setBackground(Colores.FONDO);
        textArea.setBackground(Colores.FONDO);

        JScrollPane sp = new JScrollPane(textArea);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        this.add(sp);

        ArrayList<Estudiante> ranking = new ArrayList<>(Main.registro.estudiantes);
        ranking.sort((a,b) -> (int) (100*(b.getIndiceGeneral()-a.getIndiceGeneral())));

        Document document = textArea.getDocument();

        for(int i = 0; i < ranking.size(); i++) {
            Estudiante est = ranking.get(i);

            String parte1 = (i+1) + ". " + est.getNombre() + " " + est.getApellido() + " (" + est.getCarrera().getCodigo() + ")";

            double indice = est.getIndiceGeneral();
            String indiceStr = (indice >= 0) ? StringUtil.stripDecimals(indice, 2) : "-";
            String line = parte1 + StringUtil.repeat(" ", 20-parte1.length()) + "\t" + "Índice General: " + indiceStr + "\tHonores: " + est.getHonores() + "\n";
            try {
                document.insertString(document.getLength(), line, null);
            } catch(BadLocationException x) {
                x.printStackTrace();
            }
        }
    }

    @Override
    public Object getValor() {
        return null;
    }

    @Override
    public boolean puedeGuardar() {
        return false;
    }

    @Override
    public Object guardar() {
        return null;
    }

    @Override
    public void enfocar() {

    }
}
