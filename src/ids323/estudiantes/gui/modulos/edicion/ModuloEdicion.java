package ids323.estudiantes.gui.modulos.edicion;

import ids323.estudiantes.Main;
import ids323.estudiantes.componentes.CBoton;
import ids323.estudiantes.componentes.Relleno;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.gui.modulos.ModuloPantalla;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.logica.EntradaValor;
import ids323.estudiantes.gui.modulos.edicion.logica.ValorEdicion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa una pantalla para editar un conjunto de información.
 * */
public abstract class ModuloEdicion extends JPanel implements ModuloPantalla {
    /**
     * La pestaña a la que pertenece.
     * */
    protected final Tab tab;

    /**
     * El título de la pantalla.
     * */
    protected final String title;
    /**
     * La lista de valores que se editan.
     * */
    protected ArrayList<ValorEdicion<?>> valores = new ArrayList<>();

    /**
     * La lista de componentes de entrada para cada valor de edición.
     * */
    protected ArrayList<EntradaValor> entradas = new ArrayList<>();

    /**
     * El contenido interno de este panel.
     * */
    protected JPanel content;

    /**
     * El panel de botones para las acciones Guardar y Cancelar.
     * */
    private JPanel buttonPanel;

    /**
     * Crea un módulo de edición para las propiedades dadas.
     *
     * @param tab La pestaña asociada al módulo.
     * @param title El título que se muestra en el encabezado del módulo.
     * */
    public ModuloEdicion(Tab tab, String title) {
        super(new BorderLayout());
        this.tab = tab;
        this.title = title;
    }

    /**
     * Oculta los campos actualmente visibles y los vuelve a mostrar a según los datos en {@link ModuloEdicion#valores}.
     * */
    private void actualizarEntradas() {
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

    /**
     * Construye los componentes internos de este módulo.
     * */
    protected void construir() {
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
            tab.guardar();
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

    /**
     * Se llama cuando se presiona el botón Guardar o el botón Cancelar. No se llama cuando se cierra la pestaña.
     * */
    protected abstract void finalizarEdicion();

    /**
     * Se llama cuando uno de los campos en el módulo cambia su valor.
     * */
    public void enEdicion() {
        tab.enEdicion();
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
            Main.ventana.exploradorRegistro.refresh();
        }
        return getValor();
    }

    @Override
    public void enfocar() {

    }
}
