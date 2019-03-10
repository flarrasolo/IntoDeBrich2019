package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Tanque extends Jugador{
	private int movimientosRestantes;
	public Tanque(int x, int y, LogicaJuego l) {
		super(x,y);
		profundidad=2;
		movimientosRestantes = 5;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/tanque.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
	}
	@Override
	public void colicion(ComponenteGrafico e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getMovimientosRestantes() {
		return movimientosRestantes;
	}
	
	public void setMovimientosRestantes(int mov) {
		movimientosRestantes = mov;
	}

}
