package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class Tierra extends Terreno{
	
	public Tierra(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/tierra.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		
		puedoPonerJugador = false;
		energia=1;
	}

	@Override
	public boolean recibirAtaque(Jugador j) { return false; }
	
	@Override
	public void morir() {}
	
	@Override
	public String imprimirme() {
		return "-";
	}
	
}
