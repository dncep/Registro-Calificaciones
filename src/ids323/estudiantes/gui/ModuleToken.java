package ids323.estudiantes.gui;

import ids323.estudiantes.gui.explorer.ProjectExplorerItem;
import ids323.estudiantes.gui.modulos.DisplayModule;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public interface ModuleToken {
    String getLabel();
    Image getIcon();
    String getHint();
    Collection<ModuleToken> getSubTokens();
    boolean isExpandable();
    DisplayModule createModule();

    void onInteract();

    JPopupMenu generatePopup(ProjectExplorerItem explorerItem);
}
