package ids323.estudiantes.gui.explorer;

import ids323.estudiantes.gui.explorer.base.ExplorerFlag;
import ids323.estudiantes.gui.explorer.base.ExplorerMaster;
import ids323.estudiantes.gui.explorer.base.elements.ExplorerElement;
import ids323.estudiantes.gui.modulos.TabManager;
import ids323.estudiantes.util.Commons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 2/7/2017.
 */
public class ProjectExplorerItem extends ExplorerElement {
    private ProjectExplorerItem parent = null;

    private String path = null;
    private boolean isDirectory = false;
    private String filename = null;
    private int indentation = 0;

    private boolean expanded = false;

    private Image icon = null;

    private int x = 0;

    ProjectExplorerItem(ExplorerMaster master, File file, ArrayList<String> toOpen) {
        super(master);
        this.path = file.getPath();

        this.isDirectory = file.isDirectory();

        this.filename = file.getName();
        if(!this.isDirectory) {
            if(filename.endsWith(".craftr") || filename.endsWith(".png")) filename = filename.substring(0, filename.lastIndexOf('.'));
        }

        this.addThemeListener();

        if(toOpen.contains(this.path)) {
            expand(toOpen);
        }
    }

    private ProjectExplorerItem(ProjectExplorerItem parent, File file, ArrayList<String> toOpen) {
        super(parent.getMaster());
        this.parent = parent;

        this.path = file.getPath();
        this.indentation = parent.indentation + 1;
        this.x = indentation * master.getIndentPerLevel() + master.getInitialIndent();

        this.filename = file.getName();
        this.isDirectory = file.isDirectory();

        this.addThemeListener();

        if(toOpen.contains(this.path)) {
            expand(toOpen);
        }
    }

    private void addThemeListener() {
        boolean useFileIcon = false;
        if(path.endsWith(".png")) {
            try {
                useFileIcon = true;
                BufferedImage img = ImageIO.read(new File(path));
                Dimension size = new Dimension(img.getWidth(), img.getHeight());
                if(img.getWidth() < img.getHeight()) {
                    size.height = 16;
                    size.width = Math.max(1,(int) Math.round(16 * (double) img.getWidth() / img.getHeight()));
                } else {
                    size.width = 16;
                    size.height = Math.max(1,(int) Math.round(16 * (double) img.getHeight() / img.getWidth()));
                }
                this.icon = img.getScaledInstance(size.width,size.height, Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                useFileIcon = false;
            }
        }
        if(!useFileIcon) {

            String iconName = "estudiante";

            this.icon = Commons.getIcon(iconName).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        }
    }

    private void expand(ArrayList<String> toOpen) {
        File[] subfiles = new File(path).listFiles();
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

        expanded = true;
        master.getExpandedElements().add(this.path);
        master.repaint();
    }

    private void collapse() {
        this.propagateCollapse();
        this.children.clear();
        expanded = false;
        master.repaint();
    }

    private void propagateCollapse() {
        master.getExpandedElements().remove(this.path);
        for(ExplorerElement element : children) {
            if(element instanceof ProjectExplorerItem) ((ProjectExplorerItem) element).propagateCollapse();
        }
    }

    public void render(Graphics g) {
        g.setFont(master.getFont());
        int y = master.getOffsetY();
        master.getFlatList().add(this);

        int x = (indentation * master.getIndentPerLevel()) + master.getInitialIndent();

        g.setColor((this.rollover || this.selected) ? master.getColorMap().get("item.rollover.background") : master.getColorMap().get("item.background"));
        g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getRowHeight());
        if(this.selected) {
            g.setColor(master.getColorMap().get("item.selected.background"));

            switch(master.getSelectionStyle()) {
                case "FULL": {
                    g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getRowHeight());
                    break;
                }
                case "LINE_LEFT": {
                    g.fillRect(0, master.getOffsetY(), master.getSelectionLineThickness(), master.getRowHeight());
                    break;
                }
                case "LINE_RIGHT": {
                    g.fillRect(master.getWidth() - master.getSelectionLineThickness(), master.getOffsetY(), master.getSelectionLineThickness(), master.getRowHeight());
                    break;
                }
                case "LINE_TOP": {
                    g.fillRect(0, master.getOffsetY(), master.getWidth(), master.getSelectionLineThickness());
                    break;
                }
                case "LINE_BOTTOM": {
                    g.fillRect(0, master.getOffsetY() + master.getRowHeight() - master.getSelectionLineThickness(), master.getWidth(), master.getSelectionLineThickness());
                    break;
                }
            }
        }

        //Expand/Collapse button
        File[] listFiles = new File(path).listFiles();
        if(isDirectory && listFiles != null && listFiles.length > 0){
            int margin = ((master.getRowHeight() - 16) / 2);
            if(expanded) {
                g.drawImage(master.getAssetMap().get("collapse"),x,y + margin,16, 16,new Color(0,0,0,0),null);
            } else {
                g.drawImage(master.getAssetMap().get("expand"),x,y + margin,16, 16,new Color(0,0,0,0),null);
            }
        }
        x += 23;

        //File Icon
        {
            int margin = ((master.getRowHeight() - 16) / 2);
            g.drawImage(this.icon,x + 8 - icon.getWidth(null)/2,y + margin + 8 - icon.getHeight(null)/2, null);
        }
        x += 25;

        //File Name

        if(this.selected) {
            g.setColor(master.getColorMap().get("item.selected.foreground"));
        } else if(this.rollover) {
            g.setColor(master.getColorMap().get("item.rollover.foreground"));
        } else {
            g.setColor(master.getColorMap().get("item.foreground"));
        }
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        Graphics2D g2d = (Graphics2D) g;
        Composite oldComposite = g2d.getComposite();

        g.drawString(filename, x, master.getOffsetY() + metrics.getAscent() + ((master.getRowHeight() - metrics.getHeight())/2));
        x += metrics.stringWidth(filename);

        g2d.setComposite(oldComposite);

        if(master.getFlag(ExplorerFlag.DEBUG_WIDTH)) {
            g.setColor(Color.YELLOW);
            g.fillRect(master.getContentWidth()-2, master.getOffsetY(), 2, master.getRowHeight());
            g.setColor(Color.GREEN);
            g.fillRect(x-2, master.getOffsetY(), 2, master.getRowHeight());
        }

        master.setOffsetY(master.getOffsetY() + master.getRowHeight());
        master.setContentWidth(Math.max(master.getContentWidth(), x));
        for(ExplorerElement i : children) {
            i.render(g);
        }
    }

    private void open() {
        File file = new File(this.path);
        if (file.isDirectory()) {
            if(expanded) collapse();
            else expand(new ArrayList<>());
        } else {
            TabManager.openTab(this.path);
        }
    }

    @Override
    public int getHeight() {
        return master.getRowHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && !e.isControlDown() && e.getClickCount() % 2 == 0 && (e.getX() < x || e.getX() > x + master.getRowHeight())) {
            this.open();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            //x = indentation * master.getIndentPerLevel() + master.getInitialIndent();
            if(this.isDirectory && e.getX() >= x && e.getX() <= x + 20) {
                if(expanded) collapse();
                else expand(new ArrayList<>());
            } else {
                master.setSelected(this, e);
            }
        } else if(e.getButton() == MouseEvent.BUTTON3) {
            if(!this.selected) master.setSelected(this, new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), 0, e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger(), MouseEvent.BUTTON1));
            //StyledPopupMenu menu = this.generatePopup();
            //menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public String getIdentifier() {
        return path;
    }

    /*private StyledPopupMenu generatePopup() {
        StyledPopupMenu menu = new StyledPopupMenu();

        String newPath;
        if(this.isDirectory) newPath = this.path;
        else if(this.parent != null) newPath = this.parent.path;
        else newPath = new File(this.path).getParent();

        List<String> selectedFiles = master.getSelectedFiles();

        {
            StyledMenu newMenu = new StyledMenu("New");

            menu.add(newMenu);

            // --------------------------------------------------

            Project project = ProjectManager.getAssociatedProject(new File(path));

            String projectDir = (project != null) ? project.getDirectory().getPath() + File.separator : null;

            int lastGroup = 0;

            for(FileType type : FileType.values()) {
                if(type.canCreate(projectDir, path + File.separator)) {
                    if(type.group != lastGroup) {
                        newMenu.addSeparator();
                        lastGroup = type.group;
                    }
                    newMenu.add(type.createMenuItem(newPath));
                }
            }


        }
        menu.addSeparator();


        menu.add(MenuItems.fileItem(MenuItems.FileMenuItem.COPY));
        menu.add(MenuItems.fileItem(MenuItems.FileMenuItem.PASTE));

        StyledMenuItem deleteItem = MenuItems.fileItem(MenuItems.FileMenuItem.DELETE);
        deleteItem.setEnabled(selectedFiles.size() >= 1);
        deleteItem.addActionListener(e -> FileManager.delete(selectedFiles));
        menu.add(deleteItem);

        menu.addSeparator();
        StyledMenu refactorMenu = new StyledMenu("Refactor");
        menu.add(refactorMenu);

        StyledMenuItem renameItem = MenuItems.fileItem(MenuItems.FileMenuItem.RENAME);

        refactorMenu.add(renameItem);
        refactorMenu.add(MenuItems.fileItem(MenuItems.FileMenuItem.MOVE));

        menu.addSeparator();

        StyledMenuItem openInSystemItem = new StyledMenuItem("Show in System Explorer", "explorer");
        openInSystemItem.addActionListener(e -> Commons.showInExplorer(this.path));

        menu.add(openInSystemItem);

        return menu;
    }*/
}
