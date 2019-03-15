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

	public boolean movimientoPosibleDisparo() {
		return true;
	}

	public boolean mejorar() {
		return false;
	}

	@Override
	public boolean recibirAtaque(Jugador j) { return false; }
		
	public boolean destruido() {
		boolean resultado = false;
		if(energia==0) {
			ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondo.png"));
			resultado = true;
		}
		return resultado;
	}

	@Override
	public void morir() {}
	
	@Override
	public String imprimirme() {
		return "-";
	}
	
}
