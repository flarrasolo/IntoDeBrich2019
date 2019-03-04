package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Monta�a extends Terreno{
	public Monta�a(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/montania.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		vida=1;
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
	
	public boolean destruido() {
		boolean resultado = false;
		if(vida==0) {
			ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondo.png"));
			resultado = true;
		}
		return resultado;
	}
}
