package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.componentes.menu.CItemMenu;
import ids323.estudiantes.data.Asignatura;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.data.Profesor;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.ItemExploradorRegistro;
import ids323.estudiantes.util.Comunes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static ids323.estudiantes.Main.registro;

public class TokenOrigen implements TokenModulo {

    private Collection<TokenModulo> subTokens = Arrays.asList(new TokenModuloEstudiantes(), new TokenModuloAsignaturas(), new TokenModuloProfesores());

    @Override
    public String getTitulo() {
        return "root";
    }

    @Override
    public Image getIcono() {
        return Comunes.getIcono("file");
    }

    @Override
    public String getHint() {
        return "blah";
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return subTokens;
    }

    @Override
    public boolean isExpandible() {
        return true;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return null;
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu(ItemExploradorRegistro explorerItem) {
        return null;
    }
}

class TokenModuloEstudiantes implements TokenModulo {
    @Override
    public String getTitulo() {
        return "ESTUDIANTES";
    }

    @Override
    public Image getIcono() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return new ArrayList<>(registro.estudiantes);
    }

    @Override
    public boolean isExpandible() {
        return true;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return null;
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu(ItemExploradorRegistro explorerItem) {
        JPopupMenu menu = new JPopupMenu();
        {
            CItemMenu item = new CItemMenu("Nuevo Estudiante");
            item.addActionListener(e -> Estudiante.crearNuevo());
            menu.add(item);
        }
        return menu;
    }
}

class TokenModuloAsignaturas implements TokenModulo {
    @Override
    public String getTitulo() {
        return "ASIGNATURAS";
    }

    @Override
    public Image getIcono() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return new ArrayList<>(registro.asignaturas);
    }

    @Override
    public boolean isExpandible() {
        return true;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return null;
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu(ItemExploradorRegistro explorerItem) {
        JPopupMenu menu = new JPopupMenu();
        {
            CItemMenu item = new CItemMenu("Nueva Asignatura");
            item.addActionListener(e -> Asignatura.crearNueva());
            menu.add(item);
        }
        return menu;
    }
}

class TokenModuloProfesores implements TokenModulo {
    @Override
    public String getTitulo() {
        return "PROFESORES";
    }

    @Override
    public Image getIcono() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<TokenModulo> getSubTokens() {
        return new ArrayList<>(registro.profesores);
    }

    @Override
    public boolean isExpandible() {
        return true;
    }

    @Override
    public ModuloPantalla crearModulo(Tab tab) {
        return null;
    }

    @Override
    public void enInteraccion() {

    }

    @Override
    public JPopupMenu generarMenu(ItemExploradorRegistro explorerItem) {
        JPopupMenu menu = new JPopupMenu();
        {
            CItemMenu item = new CItemMenu("Nuevo Profesor");
            item.addActionListener(e -> Profesor.crearNuevo());
            menu.add(item);
        }
        return menu;
    }
}