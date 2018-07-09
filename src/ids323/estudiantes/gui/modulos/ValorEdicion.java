package ids323.estudiantes.gui.modulos;

public class ValorEdicion<T> {
    private final String label;
    private final T defaultValue;
    private final ValidacionValor<T> validacion;
    private final SetterValor<T> setter;
    private final Class tipo;

    public ValorEdicion(String label, T defaultValue, ValidacionValor<T> validacion, SetterValor<T> setter) {
        this.label = label;
        this.defaultValue = defaultValue;
        this.validacion = validacion;
        this.setter = setter;
        this.tipo = defaultValue.getClass();
    }

    public String getLabel() {
        return label;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public ValidacionValor<T> getValidacion() {
        return validacion;
    }

    public SetterValor<T> getSetter() {
        return setter;
    }

    public Class getTipo() {
        return tipo;
    }
}
