package ids323.estudiantes.componentes;

import ids323.estudiantes.util.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
public class CMenuOpciones<T> extends CBoton {

    private ArrayList<T> opciones = new ArrayList<>();
    private HashMap<T, ImageIcon> iconos = new HashMap<>();

    protected int seleccionado = -1;

    private Fabrica<JPopupMenu> fabricaMenu = JPopupMenu::new;
    private Fabrica<JMenuItem> fabricaItems = JMenuItem::new;

    private ArrayList<OpcionListener<T>> opcionListeners = new ArrayList<>();

    public CMenuOpciones(T[] opciones) {
        super(" ");
        setOpciones(opciones);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CMenuOpciones.this.mouseClicked(e);
            }
        });
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    public ArrayList<T> getOpciones() {
        return opciones;
    }

    public void setOpciones(T[] opciones) {
        this.opciones.clear();
        seleccionado = -1;
        agregarOpciones(opciones);
    }

    public void agregarOpcion(T option) {
        this.opciones.add(option);
        actualizarOpciones();
    }

    public void agregarOpciones(T[] options) {
        for(T o : options) {
            agregarOpcion(o);
        }
    }

    private void actualizarOpciones() {
        if(seleccionado == -1 && opciones.size() > 0) {
            seleccionado = 0;
            this.setText(opciones.get(0).toString());
            this.setIcon(iconos.get(opciones.get(0)));
        } else {
            this.setText(opciones.get(seleccionado).toString());
            this.setIcon(iconos.get(opciones.get(seleccionado)));
        }
    }

    public void setFabricaMenu(Fabrica<JPopupMenu> f) {
        this.fabricaMenu = f;
    }

    public void setFabricaItems(Fabrica<JMenuItem> f) {
        this.fabricaItems = f;
    }

    public void agregarOpcionListener(OpcionListener<T> l) {
        opcionListeners.add(l);}

    private void registrarEleccion(int index) {
        seleccionado = index;
        actualizarOpciones();
        T selected = opciones.get(index);
        for(OpcionListener<T> listener : opcionListeners) listener.onChoice(selected);
    }

    public T getValor() {
        if(seleccionado < 0) return null;
        if(seleccionado >= opciones.size()) return null;
        return opciones.get(seleccionado);
    }

    public int getIndiceValor() {
        return seleccionado;
    }

    public void setValor(T value) {
        int index = opciones.indexOf(value);
        if(index >= 0) {
            registrarEleccion(index);
        }
    }

    public void setIndiceValor(int index) {
        if(index >= 0 && index < opciones.size()) registrarEleccion(index);
    }

    public void setIcono(T option, Image img) {
        this.iconos.put(option, new ImageIcon(img.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        actualizarOpciones();
    }

    public void vaciar() {
        seleccionado = -1;
        opciones.clear();
        iconos.clear();
    }

    public void mouseClicked(MouseEvent e) {
        JPopupMenu pm = fabricaMenu.createInstance();

        int height = 2;

        for(int i = 0; i < opciones.size(); i++) {
            T option = opciones.get(i);
            JMenuItem item = fabricaItems.createInstance();
            item.setText(option.toString());
            item.setIcon(iconos.get(option));
            int choice = i;
            item.addActionListener(arg0 -> registrarEleccion(choice));
            pm.add(item);
            height += item.getPreferredSize().getHeight();
        }

        pm.show(this,0 ,this.getHeight()-1);
    }
}
