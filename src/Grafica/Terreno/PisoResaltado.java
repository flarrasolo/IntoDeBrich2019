package Grafica.Terreno;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class PisoResaltado extends Terreno{
	public PisoResaltado(int x,int y,LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondoResaltado.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		movimientoPosible=false;
		puedoPonerJugador = true;
		vida=1;
	}
	
	public boolean movimientoPosible() {
		return movimientoPosible;
	}
	
	public boolean movimientoPosibleEnemigo(){
		return movimientoPosible;
	}
	
	public boolean movimientoPosibleDisparo() {
		return true;
	}

	public void colicion(ComponenteGrafico x) {}
}
