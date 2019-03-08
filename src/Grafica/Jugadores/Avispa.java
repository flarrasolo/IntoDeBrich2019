package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Avispa extends Jugador{
	public Avispa(int x, int y, LogicaJuego l) {
		super(x,y);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/avispa.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
	}
	@Override
	public void colicion(ComponenteGrafico e) {
		// TODO Auto-generated method stub
		
	}

}
