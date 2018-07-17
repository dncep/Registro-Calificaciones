package ids323.estudiantes.gui.modulos.edicion.logica;

import ids323.estudiantes.gui.modulos.edicion.ModuloEdicion;

import java.util.Collection;

/**
 * Representa un valor a editar en un {@link ModuloEdicion}. Describe cómo se debe leer y guardar el valor.
 * */
public class ValorEdicion<T> {
    private final String titulo;
    private final T valorDefecto;
    private final ValidacionValor<T> validacion;
    private final SetterValor<T> setter;
    private Collection<T> valoresPosibles;

    private AdaptadorEntrada adaptador;

    public ValorEdicion(String titulo, T valorDefecto, ValidacionValor<T> validacion, SetterValor<T> setter) {
        this(titulo, valorDefecto, validacion, setter, AdaptadoresEntradaEstandar.getAdaptadorParaClase(valorDefecto.getClass()));
    }

    public ValorEdicion(String titulo, T valorDefecto, ValidacionValor<T> validacion, SetterValor<T> setter, AdaptadorEntrada adaptador) {
        this.titulo = titulo;
        this.valorDefecto = valorDefecto;
        this.validacion = validacion;
        this.setter = setter;

        this.adaptador = adaptador;
    }

    public ValorEdicion(String titulo, T valorDefecto, ValidacionValor<T> validacion, SetterValor<T> setter, Collection<T> valoresPosibles) {
        this(titulo, valorDefecto, validacion, setter, AdaptadoresEntradaEstandar.ADAPTADOR_ENTRADA_OPCION_MULTIPLE);
        this.valoresPosibles = valoresPosibles;
    }

    public String getTitulo() {
        return titulo;
    }

    public T getValorDefecto() {
        return valorDefecto;
    }

    public ValidacionValor<T> getValidacion() {
        return validacion;
    }

    public SetterValor<T> getSetter() {
        return setter;
    }

    public Collection<T> getValoresPosibles() {
        return valoresPosibles;
    }

    public EntradaValor crearEntrada(ModuloEdicion modulo) {
        return adaptador.crearEntrada(this, modulo);
    }
}
