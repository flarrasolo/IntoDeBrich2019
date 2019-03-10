package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class Montaña extends Terreno{
	public Montaña(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/montaña.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		puedoPonerJugador = false;
		vida=1;
	}

	public boolean movimientoPosibleDisparo() {
		return true;
	}

	@Override
	public void recibirAtaque(Jugador j) {
		// TODO Auto-generated method stub
		
	}


	
	
}
