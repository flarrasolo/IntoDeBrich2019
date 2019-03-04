package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Edificio extends Terreno{
	public Edificio(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/edificio.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		vida=10;
	}

	public boolean movimientoPosibleDisparo() {
		return true;
	}

	public boolean mejorar() {
		return false;
	}

	@Override
	public void colicion(ComponenteGrafico e) {
		vida--;
	}
}
