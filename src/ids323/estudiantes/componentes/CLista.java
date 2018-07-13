package ids323.estudiantes.componentes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CLista<T> extends JPanel {

	private ArrayList<T> options = new ArrayList<>();
	
	protected int selected = 0;

	EstiloComponente normalStyle = new EstiloComponente();
	EstiloComponente rolloverStyle = new EstiloComponente(null);
	EstiloComponente selectedStyle = new EstiloComponente(null);
	
	private ArrayList<ListSelectionListener> listSelectionListeners = new ArrayList<>();

	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	protected CLista() {
	}
	
	public CLista(T[] options) {
		setOptions(options);
	}

	public void setOptions(T[] options) {
		this.options.clear();
		for(T o : options) {
			this.options.add(o);
		}
		updateChildren();
	}
	
	private void updateChildren() {
		this.removeAll();
		
		for(int i = 0; i < options.size(); i++) {
			T o = options.get(i);
			CItemLista option = new CItemLista(o.toString(), i, this);
			
			normalStyle.aplicarEstilo(option);
			
			option.setPreferredSize(null);
			option.setMinimumSize(new Dimension(this.getWidth(),option.getPreferredSize().height));
			option.setMaximumSize(new Dimension(this.getWidth(),option.getPreferredSize().height));
			
			this.add(option);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for(Component c : this.getComponents()) {
			c.setMinimumSize(new Dimension(this.getWidth(),c.getPreferredSize().height));
			c.setMaximumSize(new Dimension(this.getWidth(),c.getPreferredSize().height));
		}
		revalidate();
		super.paintComponent(g);
	}
	
	public void setForeground(Color c) {
		super.setForeground(c);
		setCellForeground(c);
	}
	
	private void updateStyle() {
		for(Component c : this.getComponents()) {
			JComponent jc = (JComponent) c;
			normalStyle.aplicarEstilo(jc);
			if(normalStyle.texto != null) {
				jc.getComponents()[0].setForeground(normalStyle.texto);
			}
		}
		if(selected >= 0) {
			selectedStyle.aplicarEstilo((JComponent) this.getComponents()[selected]);
		}
		repaint();
	}
	
	public Color getCellBackground() {
		return normalStyle.fondo;
	}

	public Color getCellForeground() {
		return normalStyle.texto;
	}

	public Font getCellFont() {
		return normalStyle.letra;
	}

	public Border getCellBorder() {
		return normalStyle.borde;
	}

	public void setCellBackground(Color cellBackground) {
		this.normalStyle.fondo = cellBackground;
		updateStyle();
	}

	public void setCellForeground(Color cellForeground) {
		if(this.normalStyle == null) {
			return;
		}
		this.normalStyle.texto = cellForeground;
		updateStyle();
	}

	public void setCellFont(Font cellFont) {
		this.normalStyle.letra = cellFont;
		updateStyle();
	}

	public void setCellBorder(Border cellBorder) {
		this.normalStyle.borde = cellBorder;
		updateStyle();
	}

	public Color getRolloverCellBackground() {
		return rolloverStyle.fondo;
	}

	public Color getRolloverCellForeground() {
		return rolloverStyle.texto;
	}

	public Font getRolloverCellFont() {
		return rolloverStyle.letra;
	}

	public Border getRolloverCellBorder() {
		return rolloverStyle.borde;
	}

	public void setRolloverCellBackground(Color rolloverCellBackground) {
		this.rolloverStyle.fondo = rolloverCellBackground;
		updateStyle();
	}

	public void setRolloverCellForeground(Color rolloverCellForeground) {
		this.rolloverStyle.texto = rolloverCellForeground;
		updateStyle();
	}

	public void setRolloverCellFont(Font rolloverCellFont) {
		this.rolloverStyle.letra = rolloverCellFont;
		updateStyle();
	}

	public void setRolloverCellBorder(Border rolloverCellBorder) {
		this.rolloverStyle.borde = rolloverCellBorder;
		updateStyle();
	}

	public Color getSelectedCellBackground() {
		return selectedStyle.fondo;
	}

	public Color getSelectedCellForeground() {
		return selectedStyle.texto;
	}

	public Font getSelectedCellFont() {
		return selectedStyle.letra;
	}

	public Border getSelectedCellBorder() {
		return selectedStyle.borde;
	}

	public void setSelectedCellBackground(Color selectedCellBackground) {
		this.selectedStyle.fondo = selectedCellBackground;
		updateStyle();
	}

	public void setSelectedCellForeground(Color selectedCellForeground) {
		this.selectedStyle.texto = selectedCellForeground;
		updateStyle();
	}

	public void setSelectedCellFont(Font selectedCellFont) {
		this.selectedStyle.letra = selectedCellFont;
		updateStyle();
	}

	public void setSelectedCellBorder(Border selectedCellBorder) {
		this.selectedStyle.borde = selectedCellBorder;
		updateStyle();
	}

	public EstiloComponente getNormalStyle() {
		return normalStyle;
	}

	public EstiloComponente getRolloverStyle() {
		return rolloverStyle;
	}

	public EstiloComponente getSelectedStyle() {
		return selectedStyle;
	}

	public void setNormalStyle(EstiloComponente normalStyle) {
		//System.out.println("SETTING NORMAL STYLE");
		//this.normalStyle = normalStyle;
		updateStyle();
	}

	public void setRolloverStyle(EstiloComponente rolloverStyle) {
		this.rolloverStyle = rolloverStyle;
		updateStyle();
	}

	public void setSelectedStyle(EstiloComponente selectedStyle) {
		this.selectedStyle = selectedStyle;
		updateStyle();
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		if(!listSelectionListeners.contains(listener)) listSelectionListeners.add(listener);
	}
	
	public void removeListSelectionListener(ListSelectionListener listener) {
		if(listSelectionListeners.contains(listener)) listSelectionListeners.remove(listener);
	}
	
	protected void registerSelectionChange(int index) {
		if(this.selected != index) {
			for(ListSelectionListener listener : listSelectionListeners) {
				listener.valueChanged(new ListSelectionEvent(options.get(index), index, index, false));
			}
			this.selected = index;
			updateStyle();
			repaint();
		}
	}

	public T getSelected() {
		return options.get(selected);
	}
}

class CItemLista extends JPanel implements MouseListener {
	
	private int index;
	private CLista<?> parent;
	
	CItemLista(String labelText, int index, CLista<?> parent) {
		this.index = index;
		this.parent = parent;
		JLabel label = new JLabel(labelText);
		this.add(label);
		
		this.addMouseListener(this);
	}
	
	private boolean isSelected() {
		return this.index == parent.selected;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		parent.registerSelectionChange(index);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(isSelected()) {
			parent.selectedStyle.aplicarEstilo(this);
		} else {
			parent.rolloverStyle.aplicarEstilo(this);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(isSelected()) {
			parent.selectedStyle.aplicarEstilo(this);
		} else {
			parent.normalStyle.aplicarEstilo(this);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	
}






