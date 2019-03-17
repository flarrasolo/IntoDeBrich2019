package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class Monta�a extends Terreno{
	public Monta�a(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/monta�a.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		
		puedoPonerJugador = false;
		energia=1;
	}
	
	@Override
	public boolean recibirAtaque(Jugador j) { return false; }

	@Override
	public void morir() {	}

	@Override
	public String imprimirme() {
		return "M";
	}
	
	
}
