package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.util.StringUtil;
import ids323.estudiantes.xswing.XButton;
import ids323.estudiantes.xswing.XDropdownMenu;
import ids323.estudiantes.xswing.XNumberField;
import ids323.estudiantes.xswing.XTextField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ModuloEdicion extends JPanel implements DisplayModule {

    protected static final HashMap<Class, InputAdapter> inputAdapters = new HashMap<>();

    protected final String title;
    protected ArrayList<ValorEdicion<?>> valores = new ArrayList<>();
    protected ArrayList<EntradaValor> entrada = new ArrayList<>();

    static {
        inputAdapters.put(String.class, valor -> new EntradaValor() {
            XTextField field = new XTextField((String) valor.getDefaultValue());

            @Override
            public JComponent getComponent() {
                return field;
            }

            @Override
            public String validateInput() {
                return valor.getValidacion().validate(field.getText());
            }

            @Override
            public void setInput() {
                valor.getSetter().set(field.getText());
            }
        });
        inputAdapters.put(Integer.class, valor -> new EntradaValor() {
            XNumberField field = new XNumberField((int) valor.getDefaultValue());

            @Override
            public JComponent getComponent() {
                return field;
            }

            @Override
            public String validateInput() {
                return valor.getValidacion().validate(field.getValue());
            }

            @Override
            public void setInput() {
                valor.getSetter().set(field.getValue());
            }
        });
        inputAdapters.put(Enum.class, valor -> new EntradaValor() {
                    XDropdownMenu<Object> field = new XDropdownMenu<>(((Enum) valor.getDefaultValue()).getDeclaringClass().getEnumConstants());

                    {
                        field.setValue(valor.getDefaultValue());
                    }

                    @Override
                    public JComponent getComponent() {
                        return field;
                    }

                    @Override
                    public String validateInput() {
                        return valor.getValidacion().validate(field.getValue());
                    }

                    @Override
                    public void setInput() {
                        valor.getSetter().set(field.getValue());
                    }
                });
        inputAdapters.put(Calendar.class, valor -> new EntradaValor() {
            JPanel panel;
            XTextField field;

            {
                panel = new JPanel();
                panel.setOpaque(false);

                Calendar def = (Calendar) valor.getDefaultValue();

                String dom = "" + def.get(Calendar.DAY_OF_MONTH);
                String mon = "" + def.get(Calendar.MONTH);
                String year = "" + def.get(Calendar.YEAR);

                String format = StringUtil.repeat("0",2-dom.length()) + dom + "-" + StringUtil.repeat("0",2-mon.length()) + mon + "-" + StringUtil.repeat("0",4-year.length()) + year;

                field = new XTextField(format, Pattern.compile("\\d{1,2}[-/]\\d{1,2}[-/]\\d{4}"));
                field.setToolTipText("Formato: dd-mm-aaaa");
            }

            @Override
            public JComponent getComponent() {
                return panel;
            }

            private Calendar parseDate() {
                String str = field.getText();
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
            public String validateInput() {
                Calendar date = parseDate();
                return date == null ? "Formato inválido para fecha" : valor.getValidacion().validate(date);
            }

            @Override
            public void setInput() {
                Calendar date = parseDate();
                if(date != null) valor.getSetter().set(date);
            }
        });
    }

    public ModuloEdicion(String title) {
        super(new FlowLayout(FlowLayout.CENTER));
        this.title = title;
    }

    void construir() {
        this.add(new JLabel(this.title));
        for(ValorEdicion<?> v : valores) {
            this.add(new JLabel(v.getLabel()));
            for(Map.Entry<Class, InputAdapter> adapter : inputAdapters.entrySet()) {
                if(adapter.getKey().isInstance(v.getDefaultValue())) {
                    EntradaValor entrada = adapter.getValue().crearEntrada(v);
                    this.entrada.add(entrada);
                    this.add(entrada.getComponent());
                }
            }
        }

        XButton guardar = new XButton("Guardar");

        guardar.addActionListener(e -> {
            boolean valid = true;
            for(EntradaValor valor : entrada) {
                String validation = valor.validateInput();
                if(validation != null) {
                    valid = false;
                    System.out.println(validation);
                }
            }
            if(valid) {
                entrada.forEach(EntradaValor::setInput);
                TabManager.closeSelectedTab();
                Ventana.projectExplorer.refresh();
            }
        });
        XButton cancelar = new XButton("Cancelar");
        cancelar.addActionListener(e -> TabManager.closeSelectedTab());

        this.add(guardar);
        this.add(cancelar);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean canSave() {
        return false;
    }

    @Override
    public Object save() {
        return null;
    }

    @Override
    public void focus() {

    }
}
