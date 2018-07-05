package ids323.estudiantes.gui.modulos;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
        return new ArrayList<>(Main.registro.estudiantes);
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
        return new ArrayList<>(Main.registro.asignaturas);
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