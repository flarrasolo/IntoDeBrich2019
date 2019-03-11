package Grafica.Terreno;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class Edificio extends Terreno{
	public Edificio(int x, int y, LogicaJuego l) {
		super(x,y,l);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/edificio.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		puedoPonerJugador = false;
		energia=10;
	}

	public boolean movimientoPosibleDisparo() {
		return true;
	}

	public boolean mejorar() {
		return false;
	}

	@Override
	public boolean recibirAtaque(Jugador j) {
		energia -=j.getDañoEdificios();
		if(energia == 0)
			morir();
		return true;
	}

	@Override
	public void morir() { logica.edificioDestruido(this); }
}
