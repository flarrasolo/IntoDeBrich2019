package Grafica.Terreno;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class PisoResaltado extends Terreno{
	public PisoResaltado(int x,int y,LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondoResaltado.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		
		puedoPonerJugador = true;
		energia=1;
	}
	
	@Override
	public boolean recibirAtaque(Jugador e) { return false; }

	@Override
	public void morir() {}
	
	@Override
	public String imprimirme() {
		return ".";
	}

}
