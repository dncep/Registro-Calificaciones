package ids323.estudiantes.gui.explorador.base;

import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.explorador.base.elementos.ElementoExplorador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Representa un explorador que contiene ítemes que pueden o no expandirse y mostrar subnodos.
 */
public class MasterExplorador extends JComponent implements MouseListener, MouseMotionListener {
    protected HashMap<MarcaExplorador, Boolean> explorerFlags = new HashMap<>();

    protected ArrayList<ElementoExplorador> children = new ArrayList<>();
    protected ArrayList<ElementoExplorador> selectedItems = new ArrayList<>();

    protected ElementoExplorador rolloverItem = null;

    private ArrayList<TokenModulo> expandedElements = new ArrayList<>();

    protected ArrayList<ElementoExplorador> flatList = new ArrayList<>();
    private int contentWidth = 0;
    private int offsetY = 0;

    protected HashMap<String, Color> colors = new HashMap<>();
    protected HashMap<String, Image> assets = new HashMap<>();

    protected int rowHeight = 20;
    protected int indentPerLevel = 20;
    protected int initialIndent = 0;
    protected String selectionStyle = "FULL";
    protected int selectionLineThickness = 2;

    public MasterExplorador() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        explorerFlags.put(MarcaExplorador.DYNAMIC_ROW_HEIGHT, false);
        explorerFlags.put(MarcaExplorador.DEBUG_WIDTH, false);
    }

    public void refresh() {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        contentWidth = 0;
        offsetY = 0;
        flatList.clear();
        g.setColor(colors.get("fondo"));
        g.fillRect(0,0,this.getWidth(), this.getHeight());

        ArrayList<ElementoExplorador> toRender = new ArrayList<>(children);

        for(ElementoExplorador i : toRender) {
            i.render(g);
        }

        Dimension newSize = new Dimension(contentWidth, offsetY + rowHeight);
        if(!newSize.equals(this.getPreferredSize())) {
            this.setPreferredSize(newSize);
            this.getParent().revalidate();
        }
    }

    @Override
    public void repaint() {
        if(this.getParent() instanceof JViewport && this.getParent().getParent() instanceof JScrollPane) {
            this.getParent().getParent().repaint();
        } else super.repaint();
    }

    private ElementoExplorador getElementAtMousePos(MouseEvent e) {
        if(getFlag(MarcaExplorador.DYNAMIC_ROW_HEIGHT)) {
            int y = 0;
            for(ElementoExplorador element : flatList) {
                if(e.getY() > y && e.getY() < y + element.getHeight()) return element;
                y += element.getHeight();
            }
            return null;
        } else {
            int index = e.getY() / rowHeight;
            if(index >= 0 && index < flatList.size()) {
                return flatList.get(index);
            }
            return null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ElementoExplorador element = getElementAtMousePos(e);
        if(element != null) element.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ElementoExplorador element = getElementAtMousePos(e);
        if(element != null) element.mousePressed(e);
        else if(e.getButton() == MouseEvent.BUTTON1) {
            clearSelected();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ElementoExplorador element = getElementAtMousePos(e);
        if(element != null) element.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(rolloverItem != null) {
            rolloverItem.setRollover(false);
            rolloverItem.mouseExited(e);
            rolloverItem = null;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ElementoExplorador element = getElementAtMousePos(e);
        if(element != null) {
            element.mouseMoved(e);
            if(rolloverItem != null) rolloverItem.setRollover(false);
            if(rolloverItem != element) {
                if(rolloverItem != null) rolloverItem.mouseExited(e);
                element.mouseEntered(e);
            }
            rolloverItem = element;
            rolloverItem.setRollover(true);
        } else {
            if(rolloverItem != null) {
                rolloverItem.setRollover(false);
                rolloverItem.mouseExited(e);
            }
            rolloverItem = null;
        }
        repaint();
    }

    public void clearSelected() {
        for(ElementoExplorador item : selectedItems) {
            item.setSelected(false);
        }
        selectedItems.clear();
        selectionUpdated();
    }

    protected void selectionUpdated() {}

    public void addSelected(ElementoExplorador item) {
        this.addSelected(item, true);
    }

    public void addSelected(ElementoExplorador item, boolean invert) {
        if(!selectedItems.contains(item)) {
            item.setSelected(true);
            selectedItems.add(item);
        } else if(invert) {
            item.setSelected(false);
            selectedItems.remove(item);
        }
        selectionUpdated();
    }

    public void setSelected(ElementoExplorador item, MouseEvent e) {
        ElementoExplorador lastItem = null;
        if(this.selectedItems.size() > 0) lastItem = this.selectedItems.get(this.selectedItems.size()-1);
        if(!e.isControlDown()) {
            clearSelected();
        }
        if(e.isShiftDown() && lastItem != null) {
            int startIndex = flatList.indexOf(lastItem);
            int endIndex = flatList.indexOf(item);

            int start = Math.min(startIndex, endIndex);
            int end = Math.max(startIndex, endIndex);
            for(int i = start; i <= end; i++) {
                addSelected(flatList.get(i), false);
            }
        } else {
            addSelected(item);
        }
        repaint();
        selectionUpdated();
    }

    public List<TokenModulo> getSelectedFiles() {
        List<TokenModulo> list = new ArrayList<>();
        selectedItems.forEach(item -> {
            TokenModulo path = item.getIdentifier();
            if(path != null) list.add(path);
        });
        return list;
    }

    public boolean getFlag(MarcaExplorador flag) {
        return this.explorerFlags.get(flag);
    }

    public void toggleFlag(MarcaExplorador flag) {
        this.explorerFlags.put(flag, !getFlag(flag));
    }

    public ArrayList<ElementoExplorador> getFlatList() {
        return flatList;
    }

    public HashMap<String, Color> getColorMap() {
        return colors;
    }

    public HashMap<String, Image> getAssetMap() {
        return assets;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public int getIndentPerLevel() {
        return indentPerLevel;
    }

    public void setIndentPerLevel(int indentPerLevel) {
        this.indentPerLevel = indentPerLevel;
    }

    public int getInitialIndent() {
        return initialIndent;
    }

    public void setInitialIndent(int initialIndent) {
        this.initialIndent = initialIndent;
    }

    public String getSelectionStyle() {
        return selectionStyle;
    }

    public void setSelectionStyle(String selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    public int getSelectionLineThickness() {
        return selectionLineThickness;
    }

    public void setSelectionLineThickness(int selectionLineThickness) {
        this.selectionLineThickness = selectionLineThickness;
    }

    public int getContentWidth() {
        return contentWidth;
    }

    public void setContentWidth(int contentWidth) {
        this.contentWidth = contentWidth;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public ArrayList<TokenModulo> getExpandedElements() {
        return expandedElements;
    }

    public void setExpandedElements(ArrayList<TokenModulo> expandedElements) {
        this.expandedElements = expandedElements;
    }

    public ArrayList<ElementoExplorador> getChildren() {
        return children;
    }
}