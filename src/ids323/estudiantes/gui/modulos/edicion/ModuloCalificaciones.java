package ids323.estudiantes.gui.modulos.edicion;

import ids323.estudiantes.Main;
import ids323.estudiantes.componentes.CBoton;
import ids323.estudiantes.componentes.CMenuOpciones;
import ids323.estudiantes.componentes.CampoNumerico;
import ids323.estudiantes.data.Asignatura;
import ids323.estudiantes.data.Calificaciones;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.gui.modulos.Tab;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.gui.modulos.edicion.logica.AdaptadorEntrada;
import ids323.estudiantes.gui.modulos.edicion.logica.EntradaValor;
import ids323.estudiantes.gui.modulos.edicion.logica.ValorEdicion;
import ids323.estudiantes.util.Comunes;
import ids323.estudiantes.util.DocumentAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModuloCalificaciones extends ModuloEdicion {

    private Calificaciones calificaciones;

    @SuppressWarnings("unchecked")
    private AdaptadorEntrada adaptadorEntradaCalificaciones = (valor, modulo) -> new EntradaValor() {

        private JPanel panel;
        private CMenuOpciones<Object> campoClave;
        private CampoNumerico campoValor;
        private JLabel error;
        private CBoton botonBorrar;

        private CMenuOpciones<Object> campoAgregar;

        private HashMap<Object, Integer> map = new HashMap<>();

        {
            Dimension defFieldSize = new Dimension(400, 45);
            float defFieldFontSize = 20f;

            map.putAll(((HashMap<Asignatura, Integer>) valor.getValorDefecto()));

            panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);

            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fieldPanel.setOpaque(false);
            panel.add(fieldPanel, BorderLayout.CENTER);

            campoClave = new CMenuOpciones<>(getOpcionesSeleccionNuevas());
            campoClave.setIcono(campoClave.getOpciones().get(0), Comunes.getIcono("dropdown"));

            campoValor = new CampoNumerico();

            fieldPanel.add(campoClave);
            fieldPanel.add(campoValor);

            error = new JLabel();

            botonBorrar = new CBoton("X");
            botonBorrar.setPreferredSize(new Dimension(defFieldSize.height, defFieldSize.height));
            botonBorrar.setToolTipText("Remover asignatura de registro de calificaciones");
            botonBorrar.setFont(botonBorrar.getFont().deriveFont(16f));
            botonBorrar.setBackground(Colores.PRIMARIO_CLARO);
            botonBorrar.setRolloverColor(Colores.PRIMARIO_OSCURO);
            botonBorrar.setPressedColor(Colores.PRIMARIO_MAS_OSCURO);
            botonBorrar.setForeground(Colores.TEXTO);
            fieldPanel.add(botonBorrar);

            botonBorrar.addActionListener(e -> {
                int result = JOptionPane.showOptionDialog(Ventana.jframe, "¿Está seguro de que quiere remover la asignatura " + campoClave.getValor() + " de este reporte de calificaciones?", "Confirmación de acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(Calificaciones.ICON), new String[] {"Si", "No"}, "Si");
                if(result != JOptionPane.YES_OPTION) return;

                map.remove(campoClave.getValor());
                refrescar();
            });

            fieldPanel.add(error);

            campoClave.setForeground(Colores.TEXTO_ENTRADA);
            campoClave.setPreferredSize(defFieldSize);
            campoClave.setFont(campoClave.getFont().deriveFont(defFieldFontSize).deriveFont(Font.PLAIN));

            campoValor.setForeground(Colores.TEXTO_ENTRADA);
            campoValor.setPreferredSize(defFieldSize);
            campoValor.setFont(campoValor.getFont().deriveFont(defFieldFontSize));

            error.setFont(error.getFont().deriveFont(16f));
            error.setForeground(Colores.TEXTO_ERROR);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setOpaque(false);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            campoAgregar = new CMenuOpciones<>(getOpcionesAdicionNuevas());
            campoAgregar.setPreferredSize(defFieldSize);
            campoAgregar.setFont(campoAgregar.getFont().deriveFont(defFieldFontSize*0.9f).deriveFont(Font.PLAIN));
            campoAgregar.agregarOpcionListener(c -> {
                if(c instanceof Asignatura) {
                    campoAgregar.setIndiceValor(0);
                    map.put(c, 100);
                    refrescar();
                    campoClave.setValor(c);
                }
            });

            campoAgregar.setIndiceValor(0);

            buttonPanel.add(campoAgregar);

            campoValor.getDocument().addDocumentListener((DocumentAdapter) e -> {
                if(validarEntrada() == null && campoClave.getValor() instanceof Asignatura) {
                    map.put(campoClave.getValor(), campoValor.getValue());
                }
            });

            campoClave.agregarOpcionListener(c -> actualizar());

            campoClave.setIndiceValor(0);
        }

        private void refrescar() {
            campoClave.setOpciones(getOpcionesSeleccionNuevas());
            campoClave.setIcono(campoClave.getOpciones().get(0), Comunes.getIcono("dropdown"));
            campoAgregar.setOpciones(getOpcionesAdicionNuevas());
            actualizar();
        }

        private void actualizar() {
            campoValor.setVisible(campoClave.getValor() instanceof Asignatura);
            botonBorrar.setVisible(campoClave.getValor() instanceof Asignatura);
            if(!(campoClave.getValor() instanceof Asignatura)) campoValor.setValue(0);
            else campoValor.setValue(map.get(campoClave.getValor()));
            validarEntrada();
        }

        private Object[] getOpcionesSeleccionNuevas() {
            ArrayList<Object> options = new ArrayList<>();
            if(map.isEmpty()) {
                options.add("No hay asignaturas seleccionadas");
            } else {
                options.add("Seleccione asignatura a calificar");
                options.addAll(map.keySet());
            }
            return options.toArray();
        }

        private Object[] getOpcionesAdicionNuevas() {
            ArrayList<Object> options = new ArrayList<>();
            if(map.size() == Main.registro.asignaturas.size()) {
                options.add("No existen más asignaturas");
            } else {
                options.add("+ Agregar asignatura a la selección (" + map.size() + "/" + Main.registro.asignaturas.size() + ")");
                for(Asignatura asig : Main.registro.asignaturas) {
                    if(!map.containsKey(asig)) options.add(asig);
                }
            }
            return options.toArray();
        }

        @Override
        public JComponent getComponente() {
            return panel;
        }

        @Override
        public String validarEntrada() {
            modulo.enEdicion();
            String result = campoValor.getText() == null ? "Formato inválido" : campoValor.getValue() >= 0 && campoValor.getValue() <= 100 ? null : "Calificación debe estar entre 0 y 100";
            error.setText(result != null ? "*" + result : null);
            return result;
        }

        @Override
        public void guardarEntrada() {
            valor.getSetter().set(map);
        }

        @Override
        public ValorEdicion<?> getValorEdicion() {
            return valor;
        }

        @Override
        public int getCodigoValor() {
            return map.hashCode();
        }
    };

    public ModuloCalificaciones(Tab tab, Calificaciones calificaciones) {
        super(tab, "Editando calificaciones de " + calificaciones.getEstudiante() + " para " + calificaciones.getTrimestre());
        this.calificaciones = calificaciones;

        valores.add(new ValorEdicion<>("Ciclo trimestral", calificaciones.getTrimestre(), v -> {
            for(Calificaciones calif : Main.registro.calificaciones) {
                if(calif.getEstudiante() == this.calificaciones.getEstudiante() && calif != this.calificaciones && calif.getTrimestre().equals(v)) {
                    return "Ya existe un reporte de calificaciones de este estudiante para el trimestre " + v;
                }
            }
            return null;
        }, calificaciones::setTrimestre));

        valores.add(new ValorEdicion<>("Calificaciones", calificaciones.getCalificaciones(), v -> null, v -> {calificaciones.getCalificaciones().clear(); calificaciones.getCalificaciones().putAll(v);}, adaptadorEntradaCalificaciones));

        construir();
    }

    @Override
    protected void finalizarEdicion() {
        TabManager.closeTab(TabManager.getTabForToken(calificaciones.getEstudiante()));
        TabManager.openTab(calificaciones.getEstudiante());
    }
}
