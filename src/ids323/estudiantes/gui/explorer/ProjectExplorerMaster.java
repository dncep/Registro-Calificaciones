package ids323.estudiantes.gui.explorer;

import ids323.estudiantes.gui.explorer.base.ExplorerMaster;
import ids323.estudiantes.gui.explorer.base.elements.ExplorerSeparator;
import ids323.estudiantes.util.Commons;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 5/16/2017.
 */
public class ProjectExplorerMaster extends ExplorerMaster {
    private File root;

    public ProjectExplorerMaster() {
        root = new File("C:\\Users\\Usuario\\Craftr");

        colors.put("background",new Color(235, 235, 235));
        colors.put("item.background",new Color(235, 235, 235));
        colors.put("item.foreground",new Color(45, 45, 45));
        colors.put("item.selected.background",new Color(30,147,222));
        colors.put("item.selected.foreground",new Color(45, 45, 45));
        colors.put("item.rollover.background",new Color(215, 215, 215));
        colors.put("item.rollover.foreground",new Color(45, 45, 45));

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

    private void refresh(ArrayList<String> toOpen) {
        children.clear();
        flatList.clear();
        this.getExpandedElements().clear();

        File[] subfiles = root.listFiles();
        if(subfiles == null) return;

        ArrayList<File> subfiles1 = new ArrayList<>();

        for(File f : subfiles) {
            if(f.isDirectory()) {
                this.children.add(new ProjectExplorerItem(this, f, toOpen));
            } else {
                subfiles1.add(f);
            }
        }
        for(File f : subfiles1) {
            this.children.add(new ProjectExplorerItem(this, f, toOpen));
        }

        this.children.add(new ExplorerSeparator(this));

        File[] resourceFiles = new File(System.getProperty("user.home") + File.separator + "Craftr").listFiles();
        if(resourceFiles != null) {
            for(File f : resourceFiles) {
                this.children.add(new ProjectExplorerItem(this, f, toOpen));
            }
        }

        repaint();
    }

    @Override
    protected void selectionUpdated() {
        super.selectionUpdated();
    }
}
