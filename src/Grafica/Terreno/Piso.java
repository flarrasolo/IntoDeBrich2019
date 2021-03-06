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
		
		//this.setDibujo("/Imagenes/fondo.png");
		
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
