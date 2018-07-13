package ids323.estudiantes.componentes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EstiloComponente {
	public Color fondo = InicialesSistema.FONDO;
	public Color texto = InicialesSistema.TEXTO;
	public Font letra = InicialesSistema.LETRA;
	public Border borde = InicialesSistema.BORDE;
	
	public EstiloComponente() {
		fondo = InicialesSistema.FONDO;
		texto = InicialesSistema.TEXTO;
		letra = InicialesSistema.LETRA;
		borde = InicialesSistema.BORDE;
	}
	
	public EstiloComponente(Object ignore) {
		fondo = null;
		texto = null;
		letra = null;
		borde = null;
	}
	
	public static final EstiloComponente INICIAL = new EstiloComponente();
	
	public void aplicarEstilo(JComponent c) {
		if(fondo != null) c.setBackground(fondo);
		if(texto != null) c.setForeground(texto);
		if(letra != null) c.setFont(letra);
		if(borde != null) {c.setBorder(borde);} else {c.setBorder(BorderFactory.createEmptyBorder(0,0,1,0));}
	}
	
	public Color getFondo() {
		return fondo;
	}
	public void setFondo(Color fondo) {
		this.fondo = fondo;
		//return this;
	}
	public Color getTexto() {
		return texto;
	}
	public void setTexto(Color texto) {
		this.texto = texto;
		//return this;
	}
	public Font getFont() {
		return letra;
	}
	public void setFont(Font font) {
		this.letra = font;
		//return this;
	}
	public Border getBorde() {
		return borde;
	}
	public void setBorde(Border borde) {
		this.borde = borde;
		//return this;
	}
	
}
