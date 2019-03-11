package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class Piso extends Terreno{
	public Piso(int x,int y,LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondo.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		movimientoPosible=false;
		puedoPonerJugador = true;
		energia=1;
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

	@Override
	public void recibirAtaque(Jugador e) {
		// TODO Auto-generated method stub
		
	}

}
