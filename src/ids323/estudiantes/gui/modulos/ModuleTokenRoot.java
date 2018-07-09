package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Carrera;
import ids323.estudiantes.data.Cedula;
import ids323.estudiantes.data.Estado;
import ids323.estudiantes.data.Estudiante;
import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.util.Commons;
import ids323.estudiantes.xswing.menu.XMenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static ids323.estudiantes.Main.registro;

public class ModuleTokenRoot implements ModuleToken {

    Collection<ModuleToken> subTokens = Arrays.asList(new TokenModuloEstudiantes(), new TokenModuloAsignaturas());

    @Override
    public String getLabel() {
        return "root";
    }

    @Override
    public Image getIcon() {
        return Commons.getIcon("file");
    }

    @Override
    public String getHint() {
        return "blah";
    }

    @Override
    public Collection<ModuleToken> getSubTokens() {
        return subTokens;
    }

    @Override
    public boolean isExpandable() {
        return true;
    }

    @Override
    public DisplayModule createModule() {
        return null;
    }

    @Override
    public void onInteract() {

    }

    @Override
    public JPopupMenu generatePopup(ProjectExplorerItem explorerItem) {
        return null;
    }
}

class TokenModuloEstudiantes implements ModuleToken {
    @Override
    public String getLabel() {
        return "ESTUDIANTES";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<ModuleToken> getSubTokens() {
        return new ArrayList<>(registro.estudiantes);
    }

    @Override
    public boolean isExpandable() {
        return true;
    }

    @Override
    public DisplayModule createModule() {
        return null;
    }

    @Override
    public void onInteract() {

    }

    @Override
    public JPopupMenu generatePopup(ProjectExplorerItem explorerItem) {
        JPopupMenu menu = new JPopupMenu();
        {
            XMenuItem item = new XMenuItem("Nuevo Estudiante");
            item.addActionListener(e -> {

                Calendar fechaNacimiento = Calendar.getInstance();
                fechaNacimiento.set(Calendar.YEAR, fechaNacimiento.get(Calendar.YEAR)-18);
                Random rand = new Random();
                Cedula cedula = Cedula.crearCedula(rand.nextInt(100000-10000)+10000 + "" + rand.nextInt(1000000-100000)+100000);

                Estudiante est = new Estudiante(Main.registro, "Nombre", "Apellido", fechaNacimiento, Estado.EN_PROCESO, Carrera.AGN, cedula, false);
                Main.registro.estudiantes.add(est);
                Ventana.projectExplorer.refresh();
                TabManager.openTab(est);
            });
            menu.add(item);
        }
        return menu;
    }
}

class TokenModuloAsignaturas implements ModuleToken {
    @Override
    public String getLabel() {
        return "ASIGNATURAS";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    @Override
    public String getHint() {
        return null;
    }

    @Override
    public Collection<ModuleToken> getSubTokens() {
        return new ArrayList<>(registro.asignaturas);
    }

    @Override
    public boolean isExpandable() {
        return true;
    }

    @Override
    public DisplayModule createModule() {
        return null;
    }

    @Override
    public void onInteract() {

    }

    @Override
    public JPopupMenu generatePopup(ProjectExplorerItem explorerItem) {
        return null;
    }
}