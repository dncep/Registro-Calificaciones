package ids323.estudiantes.gui.modulos.edicion.logica;

import ids323.estudiantes.componentes.CMenuOpciones;
import ids323.estudiantes.componentes.CampoNumerico;
import ids323.estudiantes.componentes.CampoTexto;
import ids323.estudiantes.data.MesTrimestre;
import ids323.estudiantes.data.Trimestre;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.util.DocumentAdapter;
import ids323.estudiantes.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class AdaptadoresEntradaEstandar {

    public static final HashMap<Class, AdaptadorEntrada> ADAPTADORES_ENTRADA = new HashMap<>();
    public static final AdaptadorEntrada ADAPTADOR_ENTRADA_OPCION_MULTIPLE;

    static {
        Dimension defTamanoCampo = new Dimension(300, 45);
        float defTamanoLetraCampo = 20f;

        ADAPTADORES_ENTRADA.put(String.class, (valor, modulo) -> new EntradaValor() {
            CampoTexto campo = new CampoTexto((String) valor.getValorDefecto());
            JPanel panel = new JPanel();
            JLabel error = new JLabel();

            {
                panel.setOpaque(false);
                panel.add(campo);
                panel.add(error);

                campo.setForeground(Colores.TEXTO_ENTRADA);
                campo.setPreferredSize(defTamanoCampo);
                campo.setFont(campo.getFont().deriveFont(defTamanoLetraCampo));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);

                campo.getDocument().addDocumentListener((DocumentAdapter) e -> validarEntrada());
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                String result = valor.getValidacion().validar(campo.getText());
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                valor.getSetter().set(campo.getText());
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return campo.getText().hashCode();
            }
        });
        ADAPTADORES_ENTRADA.put(Integer.class, (valor, modulo) -> new EntradaValor() {
            CampoNumerico campo = new CampoNumerico((int) valor.getValorDefecto());
            JPanel panel = new JPanel();
            JLabel error = new JLabel();

            {
                panel.setOpaque(false);
                panel.add(campo);
                panel.add(error);

                campo.setForeground(Colores.TEXTO_ENTRADA);
                campo.setPreferredSize(defTamanoCampo);
                campo.setFont(campo.getFont().deriveFont(defTamanoLetraCampo));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);

                campo.getDocument().addDocumentListener((DocumentAdapter) e -> validarEntrada());
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                String result = campo.getText() == null ? "Formato inválido" : valor.getValidacion().validar(campo.getValue());
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                valor.getSetter().set(campo.getValue());
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return campo.getValue();
            }
        });
        ADAPTADORES_ENTRADA.put(Enum.class, (valor, modulo) -> new EntradaValor() {
            CMenuOpciones<Object> campo = new CMenuOpciones<>(((Enum) valor.getValorDefecto()).getDeclaringClass().getEnumConstants());
            JPanel panel = new JPanel();
            JLabel error = new JLabel();

            {
                panel.setOpaque(false);
                panel.add(campo);
                panel.add(error);

                campo.setValor(valor.getValorDefecto());
                campo.setForeground(Colores.TEXTO_ENTRADA);

                campo.setMinimumSize(defTamanoCampo);
                campo.setFont(campo.getFont().deriveFont(defTamanoLetraCampo*2/3));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);

                campo.agregarOpcionListener(c -> validarEntrada());
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                String result = valor.getValidacion().validar(campo.getValor());
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                valor.getSetter().set(campo.getValor());
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return campo.getValor().hashCode();
            }
        });
        ADAPTADORES_ENTRADA.put(Calendar.class, (valor, modulo) -> new EntradaValor() {
            JPanel panel;
            CampoTexto campo;
            JLabel error;

            {
                panel = new JPanel();
                panel.setOpaque(false);

                Calendar def = (Calendar) valor.getValorDefecto();

                String dia = "" + def.get(Calendar.DAY_OF_MONTH);
                String mes = "" + (def.get(Calendar.MONTH)+1);
                String anio = "" + def.get(Calendar.YEAR);

                String formatted = StringUtil.repeat("0",2-dia.length()) + dia + "-" + StringUtil.repeat("0",2-mes.length()) + mes + "-" + StringUtil.repeat("0",4-anio.length()) + anio;

                campo = new CampoTexto("", Pattern.compile("\\d{1,2}[-/]\\d{1,2}[-/]\\d+"));
                campo.setToolTipText("Formato: dd-mm-aaaa");
                panel.add(campo);
                error = new JLabel();
                panel.add(error);

                campo.getDocument().addDocumentListener((DocumentAdapter) e -> validarEntrada());

                campo.setText(formatted);

                campo.setForeground(Colores.TEXTO_ENTRADA);
                campo.setPreferredSize(defTamanoCampo);
                campo.setFont(campo.getFont().deriveFont(defTamanoLetraCampo));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            private Calendar leerFecha() {
                String str = campo.getText();
                if(str == null) return null;
                String[] segments = str.split("[-/]");

                int dia = Integer.parseInt(segments[0]);
                int mes = Integer.parseInt(segments[1]);
                int anio = Integer.parseInt(segments[2]);

                Calendar date = Calendar.getInstance();
                date.set(anio, mes-1, dia);
                return date;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                Calendar date = leerFecha();
                String result = date == null ? "Formato inválido para fecha" : valor.getValidacion().validar(date);
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                Calendar date = leerFecha();
                if(date != null) valor.getSetter().set(date);
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return (campo.getText() != null) ? campo.getText().hashCode() : 0;
            }
        });

        ADAPTADOR_ENTRADA_OPCION_MULTIPLE = (valor, modulo) -> new EntradaValor() {
            CMenuOpciones<Object> field = new CMenuOpciones<>(valor.getValoresPosibles().toArray());
            JPanel panel = new JPanel();
            JLabel error = new JLabel();

            {
                panel.setOpaque(false);
                panel.add(field);
                panel.add(error);

                field.setValor(valor.getValorDefecto());
                field.setForeground(Colores.TEXTO_ENTRADA);

                field.setMinimumSize(defTamanoCampo);
                field.setFont(field.getFont().deriveFont(defTamanoLetraCampo*2/3));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                String result = valor.getValidacion().validar(field.getValor());
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                valor.getSetter().set(field.getValor());
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return field.getValor().hashCode();
            }
        };

        ADAPTADORES_ENTRADA.put(Trimestre.class, (valor, modulo) -> new EntradaValor() {

            private JPanel panel;
            private CMenuOpciones<MesTrimestre> campoMes;
            private CampoNumerico campoAnio;
            private JLabel error;

            {
                panel = new JPanel();
                panel.setOpaque(false);

                campoMes = new CMenuOpciones<>(MesTrimestre.values());
                panel.add(campoMes);
                campoAnio = new CampoNumerico();
                panel.add(campoAnio);
                error = new JLabel();
                panel.add(error);

                campoMes.agregarOpcionListener(c -> validarEntrada());
                campoAnio.getDocument().addDocumentListener((DocumentAdapter) e -> validarEntrada());

                campoMes.setValor(((Trimestre) valor.getValorDefecto()).getMes());
                campoAnio.setValue(((Trimestre) valor.getValorDefecto()).getAnio());

                campoMes.setForeground(Colores.TEXTO_ENTRADA);
                campoMes.setPreferredSize(defTamanoCampo);
                campoMes.setFont(campoMes.getFont().deriveFont(defTamanoLetraCampo).deriveFont(Font.PLAIN));

                campoAnio.setForeground(Colores.TEXTO_ENTRADA);
                campoAnio.setPreferredSize(defTamanoCampo);
                campoAnio.setFont(campoAnio.getFont().deriveFont(defTamanoLetraCampo));

                error.setFont(error.getFont().deriveFont(16f));
                error.setForeground(Colores.TEXTO_ERROR);
            }

            @Override
            public JComponent getComponente() {
                return panel;
            }

            @Override
            public String validarEntrada() {
                modulo.enEdicion();
                String result =
                        campoAnio.getText() == null ?
                                "Formato inválido" :
                                campoAnio.getValue() > 0 ?
                                        (valor.getValidacion().validar(new Trimestre(campoMes.getValor(), campoAnio.getValue()))) :
                                        "Año inválido";
                error.setText(result != null ? "*" + result : null);
                return result;
            }

            @Override
            public void guardarEntrada() {
                valor.getSetter().set(new Trimestre(campoMes.getValor(), campoAnio.getValue()));
            }

            @Override
            public ValorEdicion<?> getValorEdicion() {
                return valor;
            }

            @Override
            public int getCodigoValor() {
                return new Trimestre(campoMes.getValor(), campoAnio.getValue()).hashCode();
            }
        });
    }

    public static AdaptadorEntrada getAdaptadorParaClase(Class cls) {
        for(Map.Entry<Class, AdaptadorEntrada> entry : ADAPTADORES_ENTRADA.entrySet()) {
            if(entry.getKey().isAssignableFrom(cls)) return entry.getValue();
        }
        return null;
    }
}
