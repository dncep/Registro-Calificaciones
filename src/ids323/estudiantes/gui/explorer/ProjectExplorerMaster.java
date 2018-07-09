package ids323.estudiantes.gui.explorer;

import ids323.estudiantes.Main;
import ids323.estudiantes.gui.Colors;
import ids323.estudiantes.gui.ModuleToken;
import ids323.estudiantes.gui.explorer.base.ExplorerMaster;
import ids323.estudiantes.gui.explorer.base.elements.ExplorerSeparator;
import ids323.estudiantes.util.Commons;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by User on 5/16/2017.
 */
public class ProjectExplorerMaster extends ExplorerMaster {
    private ModuleToken root;

    public ProjectExplorerMaster() {
        root = Main.registro.rootToken;

        colors.put("background",Colors.ACCENT_DARKEST);
        colors.put("item.background",Colors.ACCENT_DARKEST);
        colors.put("item.foreground",Colors.TEXT);
        colors.put("item.selected.background", Colors.ACCENT);
        colors.put("item.selected.foreground",Colors.TEXT);
        colors.put("item.rollover.background",Colors.ACCENT_DARKER);
        colors.put("item.rollover.foreground",Colors.TEXT);

        rowHeight = 25;
        indentPerLevel = 20;
        initialIndent = 5;

        selectionStyle = "LINE_LEFT";
        selectionLineThickness = 2;

        assets.put("expand", Commons.getIcon("expand").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        assets.put("collapse",Commons.getIcon("collapse").getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        refresh();
    }

    @Override
    public void refresh() {
        clearSelected();
        refresh(new ArrayList<>(this.getExpandedElements()));
    }

    private void refresh(ArrayList<ModuleToken> toOpen) {
        children.clear();
        flatList.clear();
        this.getExpandedElements().clear();

        Collection<ModuleToken> subTokens = root.getSubTokens();
        for(ModuleToken token : subTokens) {
            this.children.add(new ProjectExplorerItem(this, token, toOpen));
        }

        repaint();
    }

    @Override
    protected void selectionUpdated() {
        super.selectionUpdated();
    }
}
