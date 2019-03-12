package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Tanque extends Jugador{

	public Tanque(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,movimiento,ataque);
		profundidad=2;

		setImagen("/Imagenes/tanque.png");
		puedoPonerJugador = false;
		daño = 1;
		dañoEdificios = 0;
	}

	@Override
	public void atacar(ComponenteGrafico celda) {
		celda.recibirAtaque(this);
		
	}

	@Override
	public void morir() {
		logica.murioUsuario(this);		
	}
	
	@Override
	public void setImagenResaltada() {
		this.setImagen("/Imagenes/tanqueResaltado.png");
	}

	@Override
	public void setImagenNormal() {
		this.setImagen("/Imagenes/tanque.png");
	}
}
