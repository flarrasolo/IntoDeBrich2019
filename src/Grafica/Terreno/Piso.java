package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Piso extends Terreno{
	public Piso(int x,int y,LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		movimientoPosible=true;
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

	public void colicion(ComponenteGrafico x) {
		
	}

	public boolean mejorar() {
		return false;
	}

}
