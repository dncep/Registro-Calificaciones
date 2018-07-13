package ids323.estudiantes.gui.modulos.edicion;

import ids323.estudiantes.componentes.CBoton;
import ids323.estudiantes.componentes.Relleno;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.logica.EntradaValor;
import ids323.estudiantes.gui.modulos.edicion.logica.ValorEdicion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class ModuloEdicion extends JPanel implements ModuloPantalla {
    protected final Tab associatedTab;

    protected final String title;
    protected ArrayList<ValorEdicion<?>> valores = new ArrayList<>();

    protected ArrayList<EntradaValor> entradas = new ArrayList<>();

    protected JPanel content;

    private JPanel buttonPanel;

    public ModuloEdicion(Tab tab, String title) {
        super(new BorderLayout());
        this.associatedTab = tab;
        this.title = title;
    }

    void actualizarEntradas() {
        content.removeAll();

        ArrayList<EntradaValor> nuevasEntradas = new ArrayList<>();

        for(ValorEdicion<?> v : valores) {

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);

            JLabel fieldLabel = new JLabel(v.getTitulo());
            fieldLabel.setFont(fieldLabel.getFont().deriveFont(20f));

            row.add(fieldLabel, BorderLayout.NORTH);

            EntradaValor entrada = null;
            for(EntradaValor entradaAnterior : entradas) {
                if(entradaAnterior.getValorEdicion() == v) {
                    entrada = entradaAnterior;
                    break;
                }
            }
            if(entrada == null) {
                entrada = v.crearEntrada(this);
            }
            if(entrada == null) {
                System.err.println("Failed to create value input for " + v + "; handler for that class not found?");
                continue;
            }

            nuevasEntradas.add(entrada);
            JPanel fieldWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fieldWrapper.setOpaque(false);
            fieldWrapper.add(entrada.getComponente());
            row.add(fieldWrapper);

            row.setMaximumSize(new Dimension(10000,50));
            content.add(row);
        }

        entradas.clear();
        entradas.addAll(nuevasEntradas);

        content.add(buttonPanel);
    }

    void construir() {
        JPanel wrapper = new JPanel(new BorderLayout());
        JScrollPane sp = new JScrollPane(wrapper);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        sp.setBorder(BorderFactory.createEmptyBorder());
        this.add(sp);
        wrapper.setBackground(Colores.FONDO);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Colores.PRIMARIO_CLARO);
        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setForeground(Colores.TEXTO);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36f).deriveFont(Font.BOLD));
        header.add(new Relleno(15), BorderLayout.NORTH);
        header.add(new Relleno(15), BorderLayout.SOUTH);
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        header.add(titlePanel);
        wrapper.add(header, BorderLayout.NORTH);
        JPanel contentWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contentWrapper.setOpaque(false);

        content = new JPanel();
        contentWrapper.add(content);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(50,50,0,0),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(0,3,0,0, Colores.TEXTO_OSCURO_MENOR),
                                BorderFactory.createEmptyBorder(0,20,0,100)
                        )
                )
        );

        CBoton guardar = new CBoton("Guardar");

        guardar.addActionListener(e -> {
            associatedTab.guardar();
            TabManager.closeSelectedTab();
            finalizarEdicion();
        });

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        CBoton cancelar = new CBoton("Cancelar");
        cancelar.addActionListener(e -> {
            TabManager.closeSelectedTab();
            finalizarEdicion();
        });

        buttonPanel.add(guardar);
        buttonPanel.add(cancelar);

        content.add(buttonPanel);

        wrapper.add(contentWrapper, BorderLayout.CENTER);

        actualizarEntradas();
    }

    protected abstract void finalizarEdicion();

    public void enEdicion() {
        associatedTab.enEdicion();
    }

    @Override
    public Object getValor() {
        ArrayList<Integer> valueHashes = new ArrayList<>();
        for(EntradaValor entrada : entradas) {
            valueHashes.add(entrada.getCodigoValor());
        }
        return Objects.hash(valueHashes.toArray());
    }

    @Override
    public boolean puedeGuardar() {
        return true;
    }

    @Override
    public Object guardar() {
        boolean valid = true;
        for(EntradaValor valor : entradas) {
            String validation = valor.validarEntrada();
            if(validation != null) {
                valid = false;
            }
        }
        if(valid) {
            entradas.forEach(EntradaValor::guardarEntrada);
            Ventana.projectExplorer.refresh();
        }
        return getValor();
    }

    @Override
    public void enfocar() {

    }
}
